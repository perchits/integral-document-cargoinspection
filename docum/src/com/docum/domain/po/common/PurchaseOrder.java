package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class PurchaseOrder extends IdentifiedEntity {
	private static final long serialVersionUID = -5483165526419958870L;
	
	private String number;
	
	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public PurchaseOrder() {
		super();
	}

	public PurchaseOrder(String number) {
		super();
		this.number = number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public List<Container> getContainers() {
		return containers;
	}
	
}
