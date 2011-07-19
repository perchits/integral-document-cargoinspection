package com.docum.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

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
	private List<SecurityRole> availableRoles = new ArrayList<SecurityRole>();
	private List<SecurityRole> selectedRoles = new ArrayList<SecurityRole>();
	
	@PostConstruct
	public void initRoles() {
		this.availableRoles.addAll( 
			super.getBaseService().getAll(SecurityRole.class, DEFAULT_SORT_FIELDS));
	}
	
	@Override
	public void saveObject() {
		this.user.setSecurityRoles(this.selectedRoles);
		String password = this.user.getPassword();
		if (password != null && !password.equals("")) {
			this.user.setPassword(cryptoService.encryptText(password)); 
		}
		super.getBaseService().save(getBeanObject());
		refreshObjects();
	}
	
	@Override
	public void editObject(ActionEvent actionEvent) {
		if (getBeanObject().getId() != null) {
			this.selectedRoles.clear();
			this.availableRoles.clear();
			this.selectedRoles.addAll(this.user.getSecurityRoles());
			this.availableRoles.addAll(
				super.getBaseService().getAll(SecurityRole.class, DEFAULT_SORT_FIELDS));
			setTitle("Правка: " + getBase());
		} else {
			String message = String.format(
					"%1$s для редактирование не выбран!", getSign());
			showErrorMessage(message);
			addCallbackParam("dontShow", true);
		}
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
		this.selectedRoles.clear();
		this.availableRoles.clear();
		this.availableRoles.addAll(
			super.getBaseService().getAll(SecurityRole.class, DEFAULT_SORT_FIELDS));
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
	
	public boolean getDevelopmentPermited() {
		return loginService.getDevelopmentPermited();
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
		if (this.selectedRoles != null) {
			this.availableRoles.removeAll(this.selectedRoles);
		}
		return this.availableRoles;
	}

	public List<SecurityRole> getSelectedRoles() {
		return this.selectedRoles;
	}
	
	public void addRole() {
		if (this.availableRole != null) {
			this.availableRoles.remove(this.availableRole);
			this.selectedRoles.add(this.availableRole);
		}
	}
	
	public void removeRole() {
		if (this.selectedRole != null) {
			this.selectedRoles.remove(this.selectedRole);
			this.availableRoles.add(this.selectedRole);
		}
	}
}
