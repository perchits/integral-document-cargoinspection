package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class City extends IdentifiedEntity {
	private static final long serialVersionUID = -8223280387401163563L;
	private String name;
	private Boolean our = false;

	public City() {
	}

	public City(City city) {
		copy(city);
	}

	public City(String name, Boolean our) {
		this.name = name;
		this.our = our;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void copy(City city) {
		this.name = city.name;
		this.our = city.our;
	}

	public void setOur(Boolean our) {
		this.our = our;
	}

	public Boolean getOur() {
		return our;
	}
}
