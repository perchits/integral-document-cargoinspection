package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Report extends IdentifiedEntity{
	private static final long serialVersionUID = 4735988174583929581L;

	private String number;
	private Date date;

	@ManyToMany
	private List<Container> containers = new ArrayList<Container>();

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
}
