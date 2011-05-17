package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Supplier;
import com.docum.service.SupplierService;
import com.docum.view.handbook.dialog.BaseDialog;

@ManagedBean(name = "supplierBean")
@SessionScoped
public class SupplierView extends BaseDialog implements Serializable {
	private static final long serialVersionUID = -676095247499740650L;
	private static final String sing = "Поставщик";
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	private List<Supplier> suppliers;
	private Supplier supplier = new Supplier();

	public List<Supplier> getSuppliers() {
		if (suppliers == null) {
			refreshSuppliers();
		}
		return suppliers;
	}

	public void deleteSupplier() {
		supplierService.deleteSupplier(supplierService.getSupplier(supplier
				.getId()));
		refreshSuppliers();
	}

	public void refreshSuppliers() {
		suppliers = supplierService.getAllSuppliers();
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

	public void newSupplier() {
		supplier = new Supplier();
		setTitle("Новый " + getSing());
	}

	public void saveSupplierAction() {
		if (this.supplier.getId() != null) {
			Supplier supplier = supplierService.getSupplier(this.supplier
					.getId());
			supplier.copy(this.supplier);
			this.supplier = supplier;
		}
		this.supplier = supplierService.saveSupplier(supplier);
		refreshSuppliers();
	}

	@Override
	public String getSing() {
		return sing;
	}

	@Override
	public String getBase() {
		return supplier.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return supplier;
	}

}
