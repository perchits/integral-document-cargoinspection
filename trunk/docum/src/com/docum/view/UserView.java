package com.docum.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.SecurityRole;
import com.docum.domain.po.common.SecurityUser;
import com.docum.service.CryptoService;
import com.docum.service.LoginService;
import com.docum.view.dict.BaseView;

@Controller("userBean")
@Scope("session")
public class UserView extends BaseView {
	private static final long serialVersionUID = 5553546701444680471L;
	
	@Autowired
	LoginService loginService;
	@Autowired
	CryptoService cryptoService;
	
	private static final String sign = "Пользователь";
	private SecurityUser user = new SecurityUser();
	private SecurityRole availableRole;
	private SecurityRole selectedRole;
	private List<SecurityRole> availableRoles;
	private List<SecurityRole> selectedRoles;
	
	@Override
	public void saveObject() {
		String password = this.user.getPassword();
		if (password != null && !password.equals("")) {
			this.user.setPassword(cryptoService.encryptText(password)); 
		}
		super.getBaseService().save(getBeanObject());
		refreshObjects();
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return user.getLogin();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.user = new SecurityUser();		
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return this.user != null ? this.user : new SecurityUser();		
	}
	
	public SecurityUser getUser() {
		return user;
	}

	public void setUser(SecurityUser user) {
		this.user = user;
	}

	public boolean getAdministrationPermited() {
		return loginService.getAdministrationPermited();
	}

	public SecurityRole getAvailableRole() {
		return availableRole;
	}

	public void setAvailableRole(SecurityRole availableRole) {
		this.availableRole = availableRole;
	}

	public SecurityRole getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(SecurityRole selectedRole) {
		this.selectedRole = selectedRole;
	}

	public List<SecurityRole> getAvailableRoles() {
		return super.getBaseService().getAll(SecurityRole.class, DEFAULT_SORT_FIELDS);
	}

	public List<SecurityRole> getSelectedRoles() {
		return this.user.getSecurityRoles();
	}
}
