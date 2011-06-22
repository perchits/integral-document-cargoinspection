package com.docum.dao;

import com.docum.domain.po.common.SecurityUser;

public interface SecurityUserDao extends BaseDao {

	public static final String GET_USER_BY_LOGIN_QUERY = "getUserByLogin";
	
	public SecurityUser getUser(String login);
}
