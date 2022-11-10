package com.cursojava.proyecto.controllers;

import java.util.Date;
import java.util.stream.Collectors;

import com.cursojava.proyecto.config.JwtTokenConfig;
import com.cursojava.proyecto.model.EntrenadorDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("login")
@Tag(name = "Authentication", description = "Genera token de autenticación.")
public class AuthenticationController {

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> getAuthenticationToken(@RequestBody EntrenadorDTO entrenadorDTO) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(entrenadorDTO.getNombre());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        Claims claims = Jwts.claims().setSubject(entrenadorDTO.getNombre());
        claims.put("username", entrenadorDTO.getNombre());
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("date", new Date());

        String token = jwtTokenConfig.generateToken(entrenadorDTO, claims);
        return ResponseEntity.ok(token);
    }
}