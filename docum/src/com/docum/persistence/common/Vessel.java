package com.docum.persistence.common;

import java.io.Serializable;

import javax.persistence.Entity;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class Vessel extends IdentifiedEntity implements Serializable {

	private static final long serialVersionUID = -8435521372184842224L;

	private String name;

	public Vessel() {
	}

	public Vessel(String vesselName) {
		this.name = vesselName;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String vesselName) {
		this.name = vesselName;
	}

}
