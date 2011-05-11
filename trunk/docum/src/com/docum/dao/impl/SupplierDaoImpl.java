package com.docum.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docum.dao.SupplierDao;
import com.docum.persistence.common.Supplier;

@Service("supplierDao")
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
	public List<Supplier> getAllSuppliers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSupplier(Long supplierId) {
		// TODO Auto-generated method stub

	}
}
