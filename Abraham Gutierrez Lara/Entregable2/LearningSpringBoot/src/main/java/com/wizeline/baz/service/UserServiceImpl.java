package com.wizeline.baz.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wizeline.baz.annotations.ReportFailedOperation;
import com.wizeline.baz.cipher.DESCipher;
import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.exceptions.FailedLoginException;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.UserDTO;
import com.wizeline.baz.model.batch.FailedLoginInfo;
import com.wizeline.baz.model.request.CreateUserRequest;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.request.UpdatePasswordRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateUserResponse;
import com.wizeline.baz.model.response.GetUsersResponse;
import com.wizeline.baz.model.response.LoginResponse;
import com.wizeline.baz.repository.UserRepository;
import com.wizeline.baz.utils.Constants;
import com.wizeline.baz.utils.JwtTokenService;
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
		String encryptedPassword = desCipher.encrypt(request.getNewPassword());
		userRepository.updateUserPassword(user.getId(), encryptedPassword);
		BaseResponseDTO response = BaseResponseDTO.builder().build();
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}

	
	@Override
	@ReportFailedOperation(exception = FailedLoginException.class, topic = Constants.FAILED_LOGINS_TOPIC)
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
			throw new FailedLoginException(failedLoginDetails.toMap());
		}
		
		Claims claims = Jwts.claims();
		claims.setSubject(user.getId());
		claims.put("authorities", user.getRoles().stream().map(role -> role.toString()).collect(Collectors.joining(",")));
		String jwtToken = jwtTokenService.generateToken(user.getId(), claims);	
		LoginResponse response = new LoginResponse();
		response.setJwtToken(jwtToken);
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BaseResponseDTO> getUsers() {
		GetUsersResponse response = new GetUsersResponse();
		response.setUsers(userRepository.getAllUsers());
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<BaseResponseDTO> createUser(CreateUserRequest request, List<UserRole> roles) {
		boolean existsByEmail = userRepository.existsByEmail(request.getEmail());
		if(existsByEmail) {
			ErrorDTO error = new ErrorDTO(StatusCodes.EMAIL_EXIST, "Email -> " + request.getEmail());
			return new ResponseEntity<>(BaseResponseDTO.builder()
											.status(ResponseStatus.FAILED)
											.code(StatusCodes.FAILED)
											.errors(error).build(),
										HttpStatus.BAD_REQUEST);
		}
		String userId = UUID.randomUUID().toString().replace("-", "");
		String encryptedPassword = desCipher.encrypt(request.getPassword());
		UserDTO user = userRepository.createUser(userId, request.getName(), request.getEmail(), encryptedPassword, roles);
		CreateUserResponse response = new CreateUserResponse();
		response.setUser(user);
		response.makeSuccess();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	private class FailedLoginDetails  {
		
		private final FailedLoginInfo info;
		
		public FailedLoginDetails(String userId, String email, String failedPassword) {
			info = new FailedLoginInfo();
			info.setEmail(email);
			info.setFailedPassword(failedPassword);
			info.setUserId(userId);
			info.setTime(LocalDateTime.now());
		}

		public Map<String, Object> toMap() {
			return info.toMap();
		}
		
		
	}	
}
