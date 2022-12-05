package com.superapp.springboot.learningjava.controller;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superapp.springboot.learningjava.LearningjavaApplication;
import com.superapp.springboot.learningjava.dto.ResponseDTO;
import com.superapp.springboot.learningjava.dto.UserDTO;
import com.superapp.springboot.learningjava.service.UserService;
import com.superapp.springboot.learningjava.utils.CommonServices;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "User",
description = "Contiene operaciones comunes realizadas para usuarios.")
@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
    UserService userService;

    @Autowired
    CommonServices commonServices;

    private static final Logger LOGGER = Logger.getLogger(LearningjavaApplication.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    private final Bucket bucket;

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    
	@PostMapping("/user")
	public UserDTO login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		
		String token = getJWTToken(username);
		UserDTO user = new UserDTO();
		user.setUser(username);
		user.setToken(token);		
		return user;
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<String> getUsers() {
		if (bucket.tryConsume(1)) {
			    return ResponseEntity.ok("It's ok");
		}
	    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}

	private String getJWTToken(String username) {
		String secretKey = "1234abcd!";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	@GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
        LOGGER.info(msgProcPeticion);
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

    @PostMapping("/createUser")
    public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = new ResponseDTO();

        response = createUser(userDTO.getUser(), userDTO.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info(msgProcPeticion);
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
