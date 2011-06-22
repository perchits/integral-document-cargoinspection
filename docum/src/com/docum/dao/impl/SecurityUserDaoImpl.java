package com.docum.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.SecurityUserDao;
import com.docum.domain.po.common.SecurityUser;
import com.docum.util.DocumLogger;

@Repository
public class SecurityUserDaoImpl extends BaseDaoImpl implements SecurityUserDao {
	private static final long serialVersionUID = -4855469264178268738L;

	@Override
	public SecurityUser getUser(String login) {
		Query query = entityManager.createNamedQuery(GET_USER_BY_LOGIN_QUERY);
		query.setParameter("login", login);
		SecurityUser result = null;
		try {
			Object object = query.getSingleResult();
			if (object != null) {
				result = (SecurityUser) query.getSingleResult();
			}
		} catch (NoResultException e) {
			DocumLogger.log(new Exception("Логин не найден: ") + login);
			return null;
		}
		
		return result;
	}

}
