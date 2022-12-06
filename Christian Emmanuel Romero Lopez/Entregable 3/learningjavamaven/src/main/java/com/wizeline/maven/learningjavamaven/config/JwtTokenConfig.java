package com.wizeline.maven.learningjavamaven.config;

import com.wizeline.maven.learningjavamaven.model.UserModel;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.Date;

@Configuration
public class JwtTokenConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenConfig.class);


    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(UserModel userModel, Claims claims) {
        return Jwts.builder()
                .setSubject(userModel.getUser())
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