package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.docum.dao.SupplierDao;
import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;

public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierDao supplierDao; 

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
	
	@Override
	public Long saveSupplier(Supplier supplier) {
		return supplierDao.saveObject(supplier);
	}

	@Override
	public void deleteSupplier(Supplier supplier) {
		supplierDao.deleteObject(supplier);
	}
}
