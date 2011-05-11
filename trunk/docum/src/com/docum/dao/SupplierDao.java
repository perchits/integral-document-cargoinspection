package com.docum.dao;

import com.docum.persistence.common.Supplier;

public interface SupplierDao extends BaseDao {

	public Supplier getSupplier(Long supplierId);
	
	public Supplier getSupplier(String supplierName);	
	
	public void deleteSupplier(Long supplierId);
}
