package com.docum.domain.po.common;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

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
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Measure)) {
			return false;
		}
		
		if(getId() == null || ((Measure) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((Measure) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
	
	public String toString(){		
		return getName();
	}

}
