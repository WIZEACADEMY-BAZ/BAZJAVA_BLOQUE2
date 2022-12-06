package com.wizeline.maven.learninjavamaven.config;

import com.wizeline.maven.learninjavamaven.model.UserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZonedDateTime;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {

    @Value("${jwt.secret}")
    private String secret;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenConfig.class);

    /**
     * Este metodo genera el token de autenticacion.
     * @param userDTO Informacion del usuario autenticado.
     * @param claims Informacion adicional del usuario que se agrega al token.
     * @return Regresa el token de autenticacion.
     */
    public String generateToken(UserDTO userDTO, Claims claims){
        return Jwts.builder()
                .setSubject(userDTO.getUser())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(5).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Validacion del token utilizado durante la autenticacion.
     * @param token Token de autenticacion.
     * @return Regresa verdadero o falso dependiendo si es un token valido.
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJwt(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT Token expirado", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token es nulo o vacio", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("Token no valido", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Token no soportado", ex.getMessage());
        } catch (SignatureException ex) {
            LOGGER.error("Fallo la validacion de la firma");
        }
        return false;

    }
}
