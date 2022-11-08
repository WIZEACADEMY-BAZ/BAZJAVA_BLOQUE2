package com.wizeline.baz.utils;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenService {

	private static final Logger LOGGER = Logger.getLogger(JwtTokenService.class.getName());

	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(String subject, Claims claims) {
		return Jwts.builder().setSubject(subject).setIssuer("BankWizelineApi").setIssuedAt(new Date()).setClaims(claims)
				.setExpiration(Date.from(ZonedDateTime.now().plusHours(12).toInstant()))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | UnsupportedJwtException
				| SignatureException ex) {
			LOGGER.info("No se pudo validar el jwt token. Excepcion -> " + ex.getClass().getName());
		}
		return false;
	}

	public String getSecret() {
		return secret;
	}
}
