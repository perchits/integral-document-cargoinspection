package com.docum.persistence.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.ContainerStateEnum;
import com.docum.persistence.IdentifiedEntity;

@Entity
public class Container extends IdentifiedEntity {
	private static final long serialVersionUID = -1325845205678208996L;

	private String number;
	
	private ContainerStateEnum state;

	@ManyToMany(mappedBy = "containers")
	private Set<Invoice> invoices = new HashSet<Invoice>();
	
	@ManyToMany(mappedBy = "containers")
	private Set<BillOfLading> billOfLadings = new HashSet<BillOfLading>();

	@OneToMany(mappedBy = "container")
	private Set<Cargo> cargoes = new HashSet<Cargo>();
	
	@ManyToOne
	private Voyage voyage;
	
	public Container() {
		super();
	}

	public Container(String number) {
		super();
		this.number = number;
	}

	public Container(String number, ContainerStateEnum state) {
		super();
		this.number = number;
		this.state = state;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String name) {
		this.number = name;
	}

	public ContainerStateEnum getState() {
		return state;
	}

	public void setState(ContainerStateEnum state) {
		this.state = state;
	}
	
	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoice(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Set<BillOfLading> getBillOfLadings() {
		return billOfLadings;
	}

	public void setBillOfLading(Set<BillOfLading> billOfLadings) {
		this.billOfLadings = billOfLadings;
	}

	public Set<Cargo> getCargoes() {
		return cargoes;
	}

	public void setCargoes(Set<Cargo> cargoes) {
		this.cargoes = cargoes;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
}
