package com.docum.service;

import com.docum.domain.po.common.SecurityUser;

public interface SecurityUserService extends BaseService {
	public static final String SERVICE_NAME = "securityUserService";
	
	public SecurityUser getUser(String login);
}
