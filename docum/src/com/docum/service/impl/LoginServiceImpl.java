package com.docum.service.impl;

import java.util.ArrayList;
import java.util.Collection;

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

import com.docum.domain.po.common.SecurityUser;
import com.docum.service.CryptoService;
import com.docum.service.DocumAuthenticationManager;
import com.docum.service.LoginService;
import com.docum.service.SecurityUserService;

@Service(LoginService.SERVICE_NAME)
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	DocumAuthenticationManager documAuthenticationManager;
	@Autowired
	SecurityUserService securityUserService;
	@Autowired
	CryptoService cryptoService;
	
	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException, DataAccessException {
		Authentication auth = documAuthenticationManager.getAuthentication();
		SecurityUser user = securityUserService.getUser(auth.getPrincipal().toString());
		if (user == null) {
			throw new UsernameNotFoundException("Неверное имя пользователя");
		} else {
			String password = cryptoService.decryptText(user.getPassword()); 
			if (!password.equals(auth.getCredentials().toString())) {
				throw new UsernameNotFoundException("Неверный пароль");
			}
		}
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SwitchUserGrantedAuthority("user", auth));
		return new User(
				auth.getPrincipal().toString(), 
				auth.getCredentials().toString(), 
				true, true, true, true,
				authorities);
	}

}
