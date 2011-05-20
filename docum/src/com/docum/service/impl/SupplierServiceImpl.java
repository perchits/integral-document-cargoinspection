package com.docum.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.SupplierDao;
import com.docum.domain.po.common.Supplier;
import com.docum.service.SupplierService;

@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements SupplierService, Serializable {
	private static final long serialVersionUID = -2732138912731078684L;

	@Autowired
	private SupplierDao supplierDao; 

	@Override
	public Supplier getSupplier(Long supplierId) {
		return supplierDao.getObject(Supplier.class, supplierId);
	}

	@Override
	public Supplier getSupplier(String supplierName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierDao.getAll(Supplier.class, null);
	}

	@Override
	public void deleteSupplier(Long supplierId) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Supplier saveSupplier(Supplier supplier) {
		return supplierDao.saveObject(supplier);
	}

	@Override
	public void deleteSupplier(Supplier supplier) {
		supplierDao.deleteObject(supplier);
	}
}
