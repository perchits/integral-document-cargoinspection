package com.docum.persistence.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Invoice extends IdentifiedEntity{
	private static final long serialVersionUID = 4144517745472469185L;

	private String number;
	@ManyToMany
	private Set<Container> containers = new HashSet<Container>();

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

	public void setContainers(Set<Container> containers) {
		this.containers = containers;
	}

	public Set<Container> getContainers() {
		return containers;
	}
	
}
