package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Measure extends IdentifiedEntity {

	private static final long serialVersionUID = 34410299838532629L;

	private String name;

	public Measure() {

	}

	public Measure(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
