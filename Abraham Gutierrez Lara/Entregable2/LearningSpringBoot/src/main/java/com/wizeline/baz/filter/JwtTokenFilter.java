package com.wizeline.baz.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wizeline.baz.utils.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	@Autowired
	private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(jwtExists(request)) {
            String token = getAccessToken(request);

            if (jwtTokenService.validateAccessToken(token)) {
                Claims claims = validateToken(token);
                setUpSpringAuthentication(claims, request);
            }
        }
        filterChain.doFilter(request, response);
    }
    
    

    /**
     * Obtiene el token de acceso desde el header del request.
     * @param request Petición del usuario, debe incluir header Authorization.
     * @return Regresa únicamente el token, sin la palabra Bearer
     */
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        String token = header.split(" ")[1].trim();
        return token;
    }

    /**
     * Válida el token ingresado en el request.
     * @param token token ingresado en el header del request.
     * @return Regresa si el token es válido o no.
     */
    private Claims validateToken(String token) {
        return Jwts.parser().setSigningKey(jwtTokenService.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * Válida si se ha ingresado un token en el header del request.
     * @param request Petición por parte del usuario.
     * @return Regresa si hay un token.
     */
    private boolean jwtExists(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null)
            return false;
        return true;
    }

    /**
     * Genera la autenticación del usuario y agrega sus roles.
     * @param claims Información adicional del usuario (roles).
     */
    private void setUpSpringAuthentication(Claims claims, HttpServletRequest request) {
    	String authorities = claims.get("authorities").toString();
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    	for(String authority : StringUtils.tokenizeToStringArray(authorities, ",")) {
    		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority));
    	}
    	
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), "",
                grantedAuthorities);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

}
