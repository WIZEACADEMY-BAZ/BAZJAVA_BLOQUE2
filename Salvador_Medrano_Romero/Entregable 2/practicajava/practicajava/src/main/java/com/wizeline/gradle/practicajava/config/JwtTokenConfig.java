package com.wizeline.gradle.practicajava.config;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wizeline.gradle.practicajava.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenConfig {
	
	@Value("${jwt.secret}")
    private String secret;
	
	private static final Logger LOGGER = Logger.getLogger(JwtTokenConfig.class.getName());

	public String generateToken(UserDTO userDTO, Claims claims) {
		return Jwts.builder()
				.setSubject(userDTO.getUser())
				.setIssuedAt(new Date())
				.setClaims(claims)
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch (ExpiredJwtException ex) {
			LOGGER.info("JWT Token expirado: " + ex.getMessage());
		}catch (IllegalArgumentException ex) {
			LOGGER.info("Token es null o vacio: " + ex.getMessage());
		}catch (MalformedJwtException ex) {
			LOGGER.info("JWT no valido: " + ex.getMessage());
		}catch (SignatureException ex) {
			LOGGER.info("Fallo la validacion de la firma");
		}
		return false;
	}
	
}
