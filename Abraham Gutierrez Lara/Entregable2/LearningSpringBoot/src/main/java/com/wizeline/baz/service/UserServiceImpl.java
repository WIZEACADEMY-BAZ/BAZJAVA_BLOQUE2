package com.wizeline.baz.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wizeline.baz.cipher.DESCipher;
import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.OperationData;
import com.wizeline.baz.model.UserDTO;
import com.wizeline.baz.model.request.CreateUserRequest;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.request.UpdatePasswordRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateUserResponse;
import com.wizeline.baz.model.response.LoginResponse;
import com.wizeline.baz.repository.UserRepository;
import com.wizeline.baz.utils.BuildOperationData;
import com.wizeline.baz.utils.JwtTokenService;
import com.wizeline.baz.utils.RegisterOperationThread;
import com.wizeline.baz.utils.StatusCodes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private DESCipher desCipher;
	
	@Override
	public ResponseEntity<BaseResponseDTO> createUserBanker(CreateUserRequest request) {
		List<UserRole> roles = Arrays.asList(UserRole.BANKER);
		return createUser(request, roles);
	}

	@Override
	public ResponseEntity<BaseResponseDTO> createUser(CreateUserRequest request) {
		List<UserRole> roles = Arrays.asList(UserRole.USER);
		return createUser(request, roles);
	}

	@Override
	public ResponseEntity<BaseResponseDTO> updateUserPassword(UpdatePasswordRequest request) {
		Optional<UserDTO> userOpt = userRepository.findUserByEmail(request.getEmail());
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException(request.getEmail());
		}
		UserDTO user = userOpt.get();
		boolean passwordUpdated = userRepository.updateUserPassword(user.getId(), request.getNewPassword());
		if(!passwordUpdated) {
			throw new UserNotFoundException(user.getId());
		}
		BaseResponseDTO response = new BaseResponseDTO();
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<BaseResponseDTO> login(LoginRequest request) {
		Optional<UserDTO> userOpt = userRepository.findUserByEmail(request.getEmail());
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException(request.getEmail());
		}
		
		UserDTO user = userOpt.get();
		String savedPassword = desCipher.decrypt(user.getPassword());
		String password = desCipher.decrypt(request.getPassword());
		
		if(savedPassword != null && password != null && !savedPassword.equals(password)) {
			FailedLoginDetails failedLoginDetails = new FailedLoginDetails(user.getId(), user.getEmail(),request.getPassword());
			RegisterOperationThread<FailedLoginDetails> registerOperationThread = new RegisterOperationThread<>(failedLoginDetails);
			registerOperationThread.start();
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		Claims claims = Jwts.claims();
		claims.put("userId", user.getId());
		claims.put("authorities", user.getRoles().stream().map(role -> role.toString()).collect(Collectors.toList()));
		String jwtToken = jwtTokenService.generateToken(user.getId(), claims);	
		LoginResponse response = new LoginResponse();
		response.setJwtToken(jwtToken);
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<BaseResponseDTO> createUser(CreateUserRequest request, List<UserRole> roles) {
		boolean existsByEmail = userRepository.existsByEmail(request.getEmail());
		if(existsByEmail) {
			ErrorDTO error = new ErrorDTO(StatusCodes.EMAIL_EXIST, "Email -> " + request.getEmail());
			BaseResponseDTO response = new BaseResponseDTO(ResponseStatus.FAILED, StatusCodes.FAILED, error);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		String userId = UUID.randomUUID().toString().replace("-", "");
		String encryptedPassword = desCipher.encrypt(request.getPassword());
		UserDTO user = userRepository.createUser(userId, request.getName(), request.getEmail(), encryptedPassword, roles);
		CreateUserResponse response = new CreateUserResponse();
		response.setUser(user);
		response.makeSuccess();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	private class FailedLoginDetails implements BuildOperationData  {
		private final String userId;
		private final String email;
		private final String failedPassword;
		
		public FailedLoginDetails(String userId, String email, String failedPassword) {
			this.userId = userId;
			this.email = email;
			this.failedPassword = failedPassword;
		}

		@Override
		public OperationData operationData() {
			Map<String, Object> operationData = new HashMap<>();
			operationData.put("userId", this.userId);
			operationData.put("email", this.email);
			operationData.put("failedPassword", this.failedPassword);
			operationData.put("time", LocalDate.now());
			return new OperationData("FAILED_LOGIN",  operationData);
		}
		
		
	}
	
}
