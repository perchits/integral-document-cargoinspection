package com.docum.persistence.common;

import java.io.Serializable;

import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Tare extends IdentifiedEntity implements Serializable {

	static final long serialVersionUID = -7305850912227263851L;
	private String name;

	public Tare(){		
	}
	
	public Tare(String name){
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
