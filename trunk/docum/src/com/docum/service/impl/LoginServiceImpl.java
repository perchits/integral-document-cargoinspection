package com.docum.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.po.SecurityRoleEnum;
import com.docum.domain.po.common.SecurityRole;
import com.docum.domain.po.common.SecurityUser;
import com.docum.service.CryptoService;
import com.docum.service.DocumAuthenticationManager;
import com.docum.service.LoginService;
import com.docum.service.SecurityUserService;

@Service(LoginService.SERVICE_NAME)
@Transactional
public class LoginServiceImpl implements LoginService, Serializable {
	private static final long serialVersionUID = -748218780390076591L;
	
	private SecurityUser securityUser;
	private boolean administrationPermited = false;
	private boolean developmentPermited = false;
	private boolean operatorModePermited = false;
	
	@Autowired
	DocumAuthenticationManager documAuthenticationManager;
	@Autowired
	SecurityUserService securityUserService;
	@Autowired
	CryptoService cryptoService;
	
	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException, DataAccessException {
		this.administrationPermited = false;
		this.developmentPermited = false;
		this.operatorModePermited = false;
		Authentication auth = documAuthenticationManager.getAuthentication();
		this.securityUser  = securityUserService.getUser(auth.getPrincipal().toString());
		if (this.securityUser == null) {
			throw new UsernameNotFoundException("Неверное имя пользователя");
		} else {
			String password = "";
			if (!this.securityUser.getPassword().equals(password)) {
				password = cryptoService.decryptText(this.securityUser.getPassword());
			}
			if (!password.equals(auth.getCredentials().toString())) {
				throw new UsernameNotFoundException("Неверный пароль");
			}
		}
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(SecurityRole role: this.securityUser.getSecurityRoles()) {
			if (role.getRole().equals(SecurityRoleEnum.SUPERUSER)) {
				this.administrationPermited = true;
			} else if (role.getRole().equals(SecurityRoleEnum.DEVELOPER)) {
				this.developmentPermited = true;
				this.administrationPermited = true;
			} else if (role.getRole().equals(SecurityRoleEnum.USER)) {
				this.operatorModePermited = true;
			}
			authorities.add(new SwitchUserGrantedAuthority(role.getRole().name(), auth));
		}
		return new User(
				auth.getPrincipal().toString(), 
				auth.getCredentials().toString(), 
				true, true, true, true,
				authorities);
	}

	@Override
	public List<SecurityRole> getUserRoles() {
		return this.securityUser.getSecurityRoles();
	}

	@Override
	public boolean getAdministrationPermited() {
		return this.administrationPermited;
	}
	
	@Override
	public boolean getDevelopmentPermited() {
		return this.developmentPermited;
	}

	@Override
	public boolean getOperatorModePermited() {
		return this.operatorModePermited;
	}

}
