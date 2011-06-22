package com.docum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docum.dao.SecurityUserDao;
import com.docum.domain.po.common.SecurityUser;
import com.docum.service.SecurityUserService;

@Service(SecurityUserService.SERVICE_NAME)
public class SecurityUserServiceImpl extends BaseServiceImpl implements SecurityUserService {
	private static final long serialVersionUID = 7296835305359269381L;

	@Autowired
	SecurityUserDao securityUserDao;

	@Override
	public SecurityUser getUser(String login) {
		return securityUserDao.getUser(login);
	}

}
