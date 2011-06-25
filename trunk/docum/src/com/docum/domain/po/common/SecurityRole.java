package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.domain.po.SecurityRoleEnum;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class SecurityRole extends IdentifiedEntity {
	private static final long serialVersionUID = -3019829979794557340L;
	
	@Enumerated(EnumType.STRING)
	private SecurityRoleEnum role;
	
	public SecurityRole() {
		
	}
	
	public SecurityRole(SecurityRoleEnum securityRoleEnum) {
		this.role = securityRoleEnum;
	}

	public SecurityRoleEnum getRole() {
		return role;
	}

	public void setRole(SecurityRoleEnum role) {
		this.role = role;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SecurityRole)) {
			return false;
		}
		return EqualsHelper.equals(this.role.name(), ((SecurityRole) obj).getRole().name());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
