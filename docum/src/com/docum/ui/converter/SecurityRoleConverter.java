package com.docum.ui.converter;

import javax.faces.convert.FacesConverter;

import com.docum.domain.po.common.SecurityRole;

@FacesConverter(value = "securityRoleConverter")
public class SecurityRoleConverter extends IdentifiedEntityAbstractConverter<SecurityRole> {
	public SecurityRoleConverter() {
		super(SecurityRole.class);
	}
	
}
