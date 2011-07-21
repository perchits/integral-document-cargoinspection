package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class DeclaredCargoCondition extends CargoCondition {
	private static final long serialVersionUID = -4262422380889110415L;

	@OneToOne(optional=false)
	private Container container;

	public DeclaredCargoCondition() {
		super();
	}

	public DeclaredCargoCondition(Container container) {
		this();
		this.container = container;
	}

	public DeclaredCargoCondition(Container container, Double temperature) {
		super(temperature);
		this.container = container;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public boolean hasDefects() {
		return false;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof DeclaredCargoCondition)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((DeclaredCargoCondition) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
