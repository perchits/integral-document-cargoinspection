package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class DeclaredCargoCondition extends CargoCondition {
	private static final long serialVersionUID = -4262422380889110415L;

	@OneToOne(mappedBy="declaredCondition")
	private Cargo cargo;

	public DeclaredCargoCondition() {
		super();
	}

	public DeclaredCargoCondition(Cargo cargo, Double temperature) {
		super(temperature);
		this.cargo = cargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
