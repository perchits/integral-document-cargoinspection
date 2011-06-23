package com.docum.service;

import java.io.Serializable;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service("authenticationManager")
public class DocumAuthenticationManager implements AuthenticationManager, Serializable {

	private static final long serialVersionUID = -1905470359652988106L;
	private Authentication authentication;
	
	@Override
	public Authentication authenticate(Authentication arg0) throws AuthenticationException {
		this.authentication = arg0;
		return this.authentication;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
