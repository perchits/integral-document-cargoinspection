package com.docum.dao;

import java.util.List;

import com.docum.persistence.common.Supplier;

public interface SupplierDao extends BaseDao {

	public Supplier getSupplier(Long supplierId);
	
	public Supplier getSupplier(String supplierName);
	
	public List<Supplier> getAllSuppliers();
	
	public void deleteSupplier(Long supplierId);
}
