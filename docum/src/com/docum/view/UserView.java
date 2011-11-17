package com.docum.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.SecurityRole;
import com.docum.domain.po.common.SecurityUser;
import com.docum.service.CryptoService;
import com.docum.service.LoginService;
import com.docum.util.ListHandler;
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
	private List<SecurityRole> availableRoles = new ArrayList<SecurityRole>();
	private List<SecurityRole> selectedRoles = new ArrayList<SecurityRole>();
	private DualListModel<SecurityRole> roles = new DualListModel<SecurityRole>(); 
	

	@Override
	public void saveObject() {
		this.user.setSecurityRoles(this.roles.getTarget());
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
				super.getBaseService().getAll(SecurityRole.class, getDefaultSortFields()));
			ListHandler.sublist(availableRoles, selectedRoles);
			this.roles.setSource(availableRoles);
			this.roles.setTarget(selectedRoles);
			setTitle("Правка: " + getBriefInfo());
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
	public String getBriefInfo() {
		return user.getLogin();
	}

	@Override
	public void newObject() {
		super.newObject();
		this.user = new SecurityUser();
		this.selectedRoles.clear();
		this.availableRoles.clear();
		this.availableRoles.addAll(
			super.getBaseService().getAll(SecurityRole.class, getDefaultSortFields()));
		this.roles.setSource(availableRoles);
		this.roles.setTarget(selectedRoles);
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
	
	public boolean getOperatorModePermited() {
		return loginService.getOperatorModePermited();
	}
	
	public boolean getDevelopmentPermited() {
		return loginService.getDevelopmentPermited();
	}

	public DualListModel<SecurityRole> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<SecurityRole> roles) {
		this.roles = roles;
	}
}
