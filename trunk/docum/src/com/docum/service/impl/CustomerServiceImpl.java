package com.docum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docum.dao.CustomerDao;
import com.docum.domain.po.common.Customer;
import com.docum.service.CustomerService;

@Service(CustomerService.SERVICE_NAME)
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {
	private static final long serialVersionUID = -7576722581896797650L;
	
	@Autowired
	CustomerDao customerDao;

	@Override
	public Customer getDefaultCustomer() {
		return customerDao.getDefaultCustomer();
	}

}
