package com.docum.persistence.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.persistence.IdentifiedEntity;

@Entity
public class CargoPackage extends IdentifiedEntity {
	private static final long serialVersionUID = 1238283870788720201L;
	
	@ManyToOne
	private Tare tare;
	
	private Integer count;
}
