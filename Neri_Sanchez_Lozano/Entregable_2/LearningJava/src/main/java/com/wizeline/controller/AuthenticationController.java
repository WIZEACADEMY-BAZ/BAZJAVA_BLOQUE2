package com.wizeline.controller;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wizeline.gradle.learningjava.config.JwtTokenConfig;
import com.wizeline.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api")
@RestController
@Tag(name="Authentication",description="Genera token de autenticacion")
public class AuthenticationController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenConfig jwtTokenConfig;
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<?> getAuthenticationToken(@RequestBody UserDTO userDTO){
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(userDTO.getUser());
		}catch(UsernameNotFoundException unf){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}
		
		Claims claims = Jwts.claims().setSubject(userDTO.getUser());
        claims.put("username", userDTO.getUser());
        String authorities = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("date", new Date());

        String token = jwtTokenConfig.generateToken(userDTO, claims);
        return ResponseEntity.ok(token);
        
	}
	

}
