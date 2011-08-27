package com.docum.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.CustomerDao;
import com.docum.domain.po.common.Customer;

@Repository
public class CutomerDaoImpl extends BaseDaoImpl implements CustomerDao {
	private static final long serialVersionUID = 2563642623665173547L;

	@Override
	public Customer getDefaultCustomer() {
		Query query = entityManager.createNamedQuery(GET_DEFAULT_CUSTOMER);
		Customer result = (Customer) query.getSingleResult();
		return result;
	}

}
