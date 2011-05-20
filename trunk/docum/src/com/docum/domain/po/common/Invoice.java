package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Invoice extends IdentifiedEntity{
	private static final long serialVersionUID = 4144517745472469185L;

	private String number;
	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public Invoice() {
		super();
	}

	public Invoice(String number) {
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
