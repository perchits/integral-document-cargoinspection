package com.docum.persistence.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.docum.persistence.DocumEntity;

@Entity
@SequenceGenerator(name = "idgen", sequenceName="supplier_seq")
public class Supplier extends DocumEntity implements Serializable {

	private static final long serialVersionUID = 7918790765968857071L;
	
	@Column(nullable = false)
	private String name;

	public Supplier() {
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
