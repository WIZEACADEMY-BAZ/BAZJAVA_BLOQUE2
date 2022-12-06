package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.configuration.JwtTokenConfig;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.Date;
import java.util.stream.Collectors;

@Tag(name = "Authentication",
        description = "Genera token de autenticación.")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Autowired
    private UserDetailsService userDetailsService;
    // Inyectar dependencias

    @PostMapping("/authenticate")
    public ResponseEntity<?> getAuthenticationToken(@RequestBody UserDTO userDTO) {
        UserDetails userDetails;
        try {

            //InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(); //Lo cree

            userDetails = userDetailsService.loadUserByUsername(userDTO.getUser());
        } catch (UsernameNotFoundException e) {
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
