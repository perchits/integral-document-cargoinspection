package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.Supplier;

public interface SupplierService {
	
	public Supplier saveSupplier(Supplier supplier);
	
	public Supplier getSupplier(Long supplierId);
	
	public Supplier getSupplier(String supplierName);
	
	public List<Supplier> getAllSuppliers();
	
	public void deleteSupplier(Supplier supplier);
	
	public void deleteSupplier(Long supplierId);
}
