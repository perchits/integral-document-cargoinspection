package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;

@ManagedBean(name = "supplier")
@SessionScoped
public class SupplierView implements Serializable {
	private static final long serialVersionUID = -676095247499740650L;	
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	private List<Supplier> suppliers;
	private Supplier supplier = new Supplier();
	
	public List<Supplier> getSuppliers() {
		if(suppliers == null) {
			suppliers = supplierService.getAllSuppliers();
		}
		return suppliers;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;		
	}

	public Supplier getSupplier() {
		return supplier;
	}
	
	public void newSupplier(){
		supplier = new Supplier();
		System.out.println("Новый...");
	}
	
	public void saveSupplierAction(){		
		supplierService.saveSupplier(supplier);
		System.out.println(supplier.getName());
	}
}
