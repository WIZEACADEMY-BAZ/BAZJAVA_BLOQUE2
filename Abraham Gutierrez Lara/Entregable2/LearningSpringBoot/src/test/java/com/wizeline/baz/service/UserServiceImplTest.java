package com.wizeline.baz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.baz.cipher.DESCipher;
import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.exceptions.FailedLoginException;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.UserDTO;
import com.wizeline.baz.model.request.CreateUserRequest;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.request.UpdatePasswordRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateUserResponse;
import com.wizeline.baz.model.response.GetUsersResponse;
import com.wizeline.baz.model.response.LoginResponse;
import com.wizeline.baz.repository.UserRepository;
import com.wizeline.baz.utils.JwtTokenService;
import com.wizeline.baz.utils.StatusCodes;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@Mock
	private UserRepository userRepo;
	@Mock
	private JwtTokenService jwtTokenService;
	@Mock
	private DESCipher cipher;
	@InjectMocks
	private UserServiceImpl service;
	@Captor
	private ArgumentCaptor<List<UserRole>> rolesCaptor;
	
	@Test
	void createUserBanker() {
		String encryptedPassword = UUID.randomUUID().toString();
		CreateUserRequest request = setUpCreateUser("myemail@gmail.com", "Abraham", encryptedPassword);
		
		ResponseEntity<BaseResponseDTO> responseEntity = service.createUserBanker(request);
		assertCreateUserResponseEntity(responseEntity, request.getName(), request.getEmail(),
										encryptedPassword, UserRole.BANKER);
	}
	
	@Test
	void createUser() {
		String encryptedPassword = UUID.randomUUID().toString();
		CreateUserRequest request = setUpCreateUser("myemail@gmail.com", "Abraham", encryptedPassword);
		
		ResponseEntity<BaseResponseDTO> responseEntity = service.createUser(request);
		assertCreateUserResponseEntity(responseEntity, request.getName(), request.getEmail(),
										encryptedPassword, UserRole.USER);
	}
	
	@Test
	void createUserWithExistingEmail() {
		when(userRepo.existsByEmail(anyString())).thenReturn(true);
		CreateUserRequest request = new CreateUserRequest();
		request.setEmail("email@gmail.com");
		ResponseEntity<BaseResponseDTO> responseEntity = service.createUser(request);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertInstanceOf(BaseResponseDTO.class, responseEntity.getBody());
		BaseResponseDTO response = (BaseResponseDTO) responseEntity.getBody();
		assertNotNull(response.getErrors());
		ErrorDTO error = response.getErrors();
		assertNotNull(error);
		assertEquals(StatusCodes.EMAIL_EXIST, error.getErrorCode());
		assertNotNull(error.getMessage());
		assertTrue(error.getMessage().contains(request.getEmail()));
	}
	
	@Test
	void getAllUsers() {
		List<UserDTO> users = Arrays.asList(new UserDTO());
	
		when(userRepo.getAllUsers()).thenReturn(users);
		ResponseEntity<BaseResponseDTO> responseEntity = service.getUsers();
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(GetUsersResponse.class, responseEntity.getBody());
		GetUsersResponse response = (GetUsersResponse)  responseEntity.getBody();
		assertNotNull(response.getUsers());
		assertEquals(response.getUsers().size(), users.size());
	}
	
	@Test
	void updatePasswordNoFoundUser() {
		when(userRepo.findUserByEmail(anyString())).thenReturn(Optional.empty());
		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setEmail("email@gmail.com");
		UserNotFoundException exception = assertThrows(UserNotFoundException.class,() -> service.updateUserPassword(request));
		assertNotNull(exception);
		assertNotNull(exception.getUser());
		assertEquals(request.getEmail(), exception.getUser());
	}
	
	@Test
	void updatePassword() {
		UserDTO user = new UserDTO();
		user.setId(UUID.randomUUID().toString());
		when(userRepo.findUserByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepo.updateUserPassword(anyString(), anyString())).thenReturn(true);
		when(cipher.encrypt(anyString())).thenReturn(UUID.randomUUID().toString());
		
		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setEmail("email@gmail.com");
		request.setNewPassword(UUID.randomUUID().toString());
		
		ResponseEntity<BaseResponseDTO> responseEntity = service.updateUserPassword(request);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertInstanceOf(BaseResponseDTO.class, responseEntity.getBody());
	}
	
	
	@Test
	void loginNoFoundUser() {
		when(userRepo.findUserByEmail(anyString())).thenReturn(Optional.empty());
		LoginRequest request = new LoginRequest();
		request.setEmail("email@gmail.com");
		UserNotFoundException exception = assertThrows(UserNotFoundException.class,() -> service.login(request));
		assertNotNull(exception);
		assertNotNull(exception.getUser());
		assertEquals(request.getEmail(), exception.getUser());
	}
	
	@Test
	void loginFailedPassword() {
		UserDTO savedUser = getUser("Abraham", "abraham@gmail.com", "password123", Arrays.asList(UserRole.USER));
		when(userRepo.findUserByEmail(anyString())).thenReturn(Optional.of(savedUser));
		when(cipher.decrypt(savedUser.getPassword())).thenReturn(savedUser.getPassword());
		
		LoginRequest request = new LoginRequest();
		request.setEmail("email@gmail.com");
		request.setPassword("wrongpassword");
		when(cipher.decrypt(request.getPassword())).thenReturn(request.getPassword());
		
		FailedLoginException exception = assertThrows(FailedLoginException.class,() -> service.login(request));
		assertNotNull(exception);
		assertNotNull(exception.operationData());
	}
	
	@Test
	void successfulLogin() {
		String password = "password123";
		UserDTO user = getUser("Abraham", "abraham@gmail.com", password, Arrays.asList(UserRole.USER));
		when(userRepo.findUserByEmail(anyString())).thenReturn(Optional.of(user));
		when(cipher.decrypt(user.getPassword())).thenReturn(user.getPassword());
		when(jwtTokenService.generateToken(anyString(), any())).thenReturn(UUID.randomUUID().toString());
		
		LoginRequest request = new LoginRequest();
		request.setEmail("email@gmail.com");
		request.setPassword(password);
		when(cipher.decrypt(request.getPassword())).thenReturn(request.getPassword());
		
		ResponseEntity<BaseResponseDTO> responseEntity = service.login(request);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertInstanceOf(LoginResponse.class, responseEntity.getBody());
		LoginResponse response = (LoginResponse) responseEntity.getBody();
		assertNotNull(response.getJwtToken());
	}
	
	private void assertCreateUserResponseEntity(ResponseEntity<BaseResponseDTO> responseEntity, String expectedName, 
												String expectedEmail, String expectedPassword, UserRole expectedRole) {
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertInstanceOf(CreateUserResponse.class, responseEntity.getBody());
		CreateUserResponse response = (CreateUserResponse) responseEntity.getBody();
		UserDTO user = response.getUser();
		assertNotNull(user);
		assertEquals(expectedName, user.getName());
		assertEquals(expectedEmail, user.getEmail());
		assertEquals(expectedPassword, user.getPassword());
		assertNotNull(user.getRoles());
		assertEquals(1, user.getRoles().size());
		assertTrue(user.getRoles().contains(expectedRole));
	}
	
	private CreateUserRequest setUpCreateUser(String email, String name, String encryptedPassword) {
		CreateUserRequest request = new CreateUserRequest();
		request.setEmail(email);
		request.setName(name);
		request.setPassword("12345");
		
		when(userRepo.existsByEmail(anyString())).thenReturn(false);
		when(cipher.encrypt(anyString())).thenReturn(encryptedPassword);
		when(userRepo.createUser(anyString(), eq(request.getName()), 
								eq(request.getEmail()), eq(encryptedPassword), rolesCaptor.capture() ))
			.thenAnswer(new Answer<UserDTO>() {				
				@Override
				public UserDTO answer(InvocationOnMock invocation) throws Throwable {
					List<UserRole> roles = rolesCaptor.getValue();
					UserDTO user = getUser(request.getName(), request.getEmail(), encryptedPassword, roles);
					return user;
				}
			});
		return request;
	}
	
	private UserDTO getUser(String name, String email, String password, List<UserRole> roles) {
		UserDTO user = new UserDTO();
		user.setCreationTime(LocalDateTime.now());
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		user.setRoles(roles);
		user.setId(UUID.randomUUID().toString());
		return user;
	}
}
