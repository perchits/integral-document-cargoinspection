package com.docum.domain.po.common;

import java.io.Serializable;

import javax.persistence.Entity;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

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

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Vessel)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((Vessel) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
