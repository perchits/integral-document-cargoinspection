package com.docum.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.docum.domain.po.common.SecurityRole;

public interface LoginService extends UserDetailsService {
	
	public static final String SERVICE_NAME = "loginService";
	
	public List<SecurityRole> getUserRoles();
	public boolean getAdministrationPermited();
	public boolean getDevelopmentPermited();
	public boolean getOperatorModePermited();
}
