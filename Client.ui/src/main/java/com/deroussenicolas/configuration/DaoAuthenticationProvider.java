package com.deroussenicolas.configuration;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class DaoAuthenticationProvider implements AuthenticationProvider {
	
	final static Logger logger = LogManager.getLogger(DaoAuthenticationProvider.class);
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {	
		logger.info("DaoAuthenticationProvider, methode = authenticate" );
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("DaoAuthenticationProvider, methode = supports" );
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
