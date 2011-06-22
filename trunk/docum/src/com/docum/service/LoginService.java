package com.docum.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService{
	
	public static final String SERVICE_NAME = "loginService"; 

}
