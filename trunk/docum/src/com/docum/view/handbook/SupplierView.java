package com.docum.view.handbook;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	private String title;

	public List<Supplier> getSuppliers() {
		if (suppliers == null) {
			refreshSuppliers();
		}
		return suppliers;
	}

	public void editSupplier() {
		if (supplier != null) {
			setTitle("Правка: " + supplier.getName());
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ошибочка вышла...",
					"Поставщик для редактирования не выбран!"));			

		}
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
		setTitle("Новый поставщик");		
	}

	public void saveSupplierAction() {
		if (supplier.getId() != null) {
			Supplier serviceSupplier = supplierService.getSupplier(supplier.getId());
			serviceSupplier.copy(supplier);
			supplier = serviceSupplier;
		}
		supplier = supplierService.saveSupplier(supplier);
		/*
		 * int index = suppliers.indexOf(supplier); if (index != -1) {
		 * suppliers.set(index, supplier); } else { suppliers.add(supplier); }
		 */

		refreshSuppliers();

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
