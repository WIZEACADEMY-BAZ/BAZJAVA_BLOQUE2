package com.wizeline.gradle.learningjavagradle.controller;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.gradle.learningjavagradle.configuration.SecurityConfig;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;


@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  UserService userService;
  
  @Autowired
  CommonServices commonServices;
  
  @Autowired
  SecurityConfig securityConfig;
  
  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
  
  @GetMapping(value ="/login")
  public ResponseEntity<ResponseDTO> login(@RequestParam String user, @RequestParam String password){
      ResponseDTO response = new ResponseDTO();

      LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
      UserDTO userName = new UserDTO();
      StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
      builder.append("?user=" + user);
      builder.append("&password=" + password);
      URI uri = URI.create(builder.toString());
      userName = userName.getParameters(splitQuery(uri));
      response = commonServices.login(userName.getUser(), userName.getPassword());

      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
      return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
  }
	  
	  @PostMapping(value = "/createUser", produces = "application/json")
	  public ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO request){
		  LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
		  ResponseDTO response = new ResponseDTO();
		  response = userService.createUser(request.getUser(), request.getPassword());
		  LOGGER.info("Create user - Completed");
		  return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	  }
	  
	  @PostMapping("/createUsers")
	    public  ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
	        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
	        List<ResponseDTO> responseList = new ArrayList<>();

	        userDTOList.stream().forEach( userDTO -> {
	                    String user = userDTO.getUser();
	                    String password = userDTO.getPassword();
	                    response.set(createUser(user, password));
	                    responseList.add(response.get());
	                }
	        );

	        HttpHeaders responseHeaders = new HttpHeaders();
	        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

	        return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
	    } 
	  
	  public static Map<String, String> splitQuery(URI uri) {
	        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
	        String query = uri.getQuery();
	        String[] pairs = query.split("&");
	        for (String pair : pairs) {
	            int idx = pair.indexOf("=");
	            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
	                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
	        }
	        return queryPairs;
	    }
	  
	  private ResponseDTO createUser(String user, String password) {
	        return userService.createUser(user, password);
	    }
}
