package com.docum.domain.po;

import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class OrderedEntity extends IdentifiedEntity {
	private static final long serialVersionUID = -455030215282669365L;
	private int ord;
	
	public OrderedEntity() {
		super();
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

}
