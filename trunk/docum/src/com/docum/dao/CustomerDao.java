package com.docum.dao;

import com.docum.domain.po.common.Customer;

public interface CustomerDao extends BaseDao {

	public static final String GET_DEFAULT_CUSTOMER = "getDefaultCustomer";
	
	public Customer getDefaultCustomer();
}
