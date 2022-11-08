package com.wizeline.gradle.learningjavagradle.config;

import java.time.ZonedDateTime;
import java.util.Date;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wizeline.gradle.learningjavagradle.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenConfig.class);


	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(UserDTO userDTO, Claims claims) {
		return Jwts.builder()
				.setSubject(userDTO.getUser())
				.setIssuedAt(new Date())
				.setClaims(claims)
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(360).toInstant()))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT Token expirado", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token es null o vacío", ex.getMessage());
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT no valido", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT no soportado", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Falló la validación de la firma");
		}
		return false;
	}

}
