package com.docum.persistence.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.docum.persistence.DocumEntity;

@Entity
public class Supplier extends DocumEntity implements Serializable {

	private static final long serialVersionUID = 7918790765968857071L;
	
	private String name;

	public Supplier() {
	}
	
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
