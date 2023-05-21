package com.dam.springboot.backend.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	 private JwtTokenUtil jwtTokenUtil;

	    public JwtAuthenticationProvider() {
	        super();
	    }


	    public JwtAuthenticationProvider(JwtTokenUtil jwtTokenUtil) {
	        this.jwtTokenUtil = jwtTokenUtil;
	    }

	    @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        String token = (String) authentication.getCredentials();
	        String username = jwtTokenUtil.getUsernameFromToken(token);

	        if (username != null && jwtTokenUtil.validateToken(token)) {
	            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
	        }

	        return null;
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	    }
}
