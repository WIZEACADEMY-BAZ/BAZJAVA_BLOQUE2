package com.wizeline.gradle.learningjava.config;

import java.time.ZonedDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.wizeline.model.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Configuration
public class JwtTokenConfig {
	
	
	private static final Logger log = LoggerFactory.getLogger(JwtTokenConfig.class);
	
	@Value("${secret}")
	private String secret;
	
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
	    } catch (ExpiredJwtException ex) {
	        log.error("JWT Token expirado", ex.getMessage());
	    } catch (IllegalArgumentException ex) {
	        log.error("Token es null o vacío", ex.getMessage());
	    } catch (MalformedJwtException ex) {
	        log.error("JWT no valido", ex);
	    } catch (UnsupportedJwtException ex) {
	        log.error("JWT no soportado", ex);
	    } catch (SignatureException ex) {
	        log.error("Falló la validación de la firma");
	    }
	        return false;
	    }

}
