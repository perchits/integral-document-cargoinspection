package com.docum.domain.po.common;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.docum.dao.SecurityUserDao;
import com.docum.domain.po.IdentifiedEntity;

@Entity
@NamedQueries({
	@NamedQuery(
			name = SecurityUserDao.GET_USER_BY_LOGIN_QUERY,
			query = "SELECT user FROM SecurityUser user " +
				"WHERE user.login=:login"
	)
})
public class SecurityUser extends IdentifiedEntity {
	private static final long serialVersionUID = 8146197207074652866L;
	
	private String fullName;
	@Column(unique=true)
	private String login;
	private String password;
	private String description;
	@ManyToMany(fetch=FetchType.EAGER)
	private List<SecurityRole> securityRoles;
	
	public SecurityUser() {
		
	}
	
	public SecurityUser(String login, String password, List<SecurityRole> roles) {
		this.login = login;
		this.password = password;
		this.securityRoles = roles;
	}

	public List<SecurityRole> getSecurityRoles() {
		return securityRoles;
	}

	public void setSecurityRoles(List<SecurityRole> securityRoles) {
		this.securityRoles = securityRoles;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
