package com.wizeline.baz.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	public static void setAuth(String ...roles) {
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    	for(String authority : roles) {
    		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority));
    	}
    	
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("test", "",
                grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
}
