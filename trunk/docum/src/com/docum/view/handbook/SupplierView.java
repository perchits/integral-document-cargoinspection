package com.docum.view.handbook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.docum.persistence.IdentifiedEntity;
import com.docum.persistence.common.Supplier;

@ManagedBean(name = "supplierBean")
@SessionScoped
public class SupplierView extends BaseView implements Serializable {
	private static final long serialVersionUID = -676095247499740650L;
	
	private static final String sign = "Поставщик";
	private Supplier supplier = new Supplier();

	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	@Override
	public void newObject() {
		super.newObject();
		supplier = new Supplier();
	}


	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public String getBase() {
		return supplier.getName();
	}

	@Override
	public IdentifiedEntity getBeanObject() {
		return supplier != null ? this.supplier : new Supplier();
	}
}
