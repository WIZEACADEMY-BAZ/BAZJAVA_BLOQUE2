package com.wizeline.gradle.practicajava.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wizeline.gradle.practicajava.config.JwtTokenConfig;
import com.wizeline.gradle.practicajava.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication",
description = "Genera token de autenticacion.")

@RestController
public class AuthenticationController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenConfig jwtTokenCongif;

	@PostMapping("/authenticate")
	public ResponseEntity<?> getAuthenticationToken(@RequestBody UserDTO userDTO){
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(userDTO.getUser());
		}catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado");
		}
		Claims claims = Jwts.claims().setSubject(userDTO.getUser());
		claims.put("username", userDTO.getUser());
		String authorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		claims.put("authorities", authorities);
		claims.put("date", new Date());
		
		String token = jwtTokenCongif.generateToken(userDTO, claims);
		return ResponseEntity.ok(token);
	}
	
}
