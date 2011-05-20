package com.docum.dao.impl;

import org.springframework.stereotype.Repository;

import com.docum.dao.SupplierDao;
import com.docum.domain.po.common.Supplier;

@Repository("supplierDao")
public class SupplierDaoImpl extends BaseDaoImpl implements SupplierDao {
	private static final long serialVersionUID = 2696884917423567369L;

	@Override
	public Supplier getSupplier(Long supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier getSupplier(String supplierName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSupplier(Long supplierId) {
		// TODO Auto-generated method stub

	}
}
