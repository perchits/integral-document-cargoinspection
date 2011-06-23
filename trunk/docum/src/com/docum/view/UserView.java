package com.docum.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.common.SecurityUser;
import com.docum.service.LoginService;
import com.docum.view.dict.BaseView;

@Controller("userBean")
@Scope("session")
public class UserView extends BaseView {
	private static final long serialVersionUID = 5553546701444680471L;
	
	@Autowired
	LoginService loginService;
	private static final String sign = "Пользователь";
	private SecurityUser user = new SecurityUser();

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
}
