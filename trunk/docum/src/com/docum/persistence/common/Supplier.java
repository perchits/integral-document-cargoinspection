package com.docum.persistence.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Supplier extends IdentifiedEntity implements Serializable {

	private static final long serialVersionUID = 7918790765968857071L;
	
	@Column(nullable = false)
	private String name;

	public Supplier() {
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
   
}
