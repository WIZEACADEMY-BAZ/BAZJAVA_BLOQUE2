package com.wizeline.entregabledavid.config;

import java.time.ZonedDateTime;
import java.util.Date;

import com.wizeline.entregabledavid.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Class to generate and configure JWT token.
 */

@Component
public class JwtTokenConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenConfig.class);


    @Value("${jwt.secret}")
    private String secret;

    /**
     * Este método genera el token de autenticación.
     * @param userDTO Información del usuario autenticado.
     * @param claims Información adicional del usuario que se agrega al token.
     * @return Regresa el token de autenticación.
     */
    public String generateToken(UserDTO userDTO, Claims claims) {
        return Jwts.builder()
                .setSubject(userDTO.getUser())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Validación del token utilizado durante la autenticación.
     * @param token Token de autenticación.
     * @return Regresa verdadero o falso dependiendo si es un token válido.
     */
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