package com.docum.persistence.common;

import javax.persistence.Entity;

import com.docum.domain.ContainerStateEnum;
import com.docum.persistence.IdentifiedEntity;

@Entity
public class Container extends IdentifiedEntity{
	private static final long serialVersionUID = -1325845205678208996L;

	private String number;
	private Invoice invoice;
	private BillOfLading billOfLading;
	private ContainerStateEnum state;

	public String getNumber() {
		return number;
	}
	public void setNumber(String name) {
		this.number = name;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public BillOfLading getBillOfLading() {
		return billOfLading;
	}
	public void setBillOfLading(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
	}
	public void setState(ContainerStateEnum state) {
		this.state = state;
	}
	public ContainerStateEnum getState() {
		return state;
	}
}
