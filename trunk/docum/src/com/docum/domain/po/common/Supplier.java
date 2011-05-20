package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Supplier extends IdentifiedEntity {

	private static final long serialVersionUID = 7918790765968857071L;

	private String name;

	public Supplier() {
	}

	public Supplier(Supplier supplier) {
		copy(supplier);
	}

	public Supplier(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void copy(Supplier supplier) {
		this.name = supplier.name;
	}
}
