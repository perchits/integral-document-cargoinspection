package com.docum.persistence.common;

import java.util.ArrayList;
import java.util.List;

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
	private List<Invoice> invoices = new ArrayList<Invoice>();
	
	@ManyToMany(mappedBy = "containers")
	private List<BillOfLading> billOfLadings = new ArrayList<BillOfLading>();

	@OneToMany(mappedBy = "container")
	private List<Cargo> cargoes = new ArrayList<Cargo>();
	
	@ManyToOne
	private Voyage voyage;
	
	@ManyToOne
	private City city;
	
	public Container() {
		super();
	}

	public Container(String number) {
		super();
		this.number = number;
	}

	public Container(String number, ContainerStateEnum state, Voyage voyage, City city) {
		super();
		this.number = number;
		this.state = state;
		this.voyage = voyage;
		this.city = city;
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
	
	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoice(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<BillOfLading> getBillOfLadings() {
		return billOfLadings;
	}

	public void setBillOfLading(List<BillOfLading> billOfLadings) {
		this.billOfLadings = billOfLadings;
	}

	public List<Cargo> getCargoes() {
		return cargoes;
	}

	public void setCargoes(List<Cargo> cargoes) {
		this.cargoes = cargoes;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
