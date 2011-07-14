package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class ActualCargoCondition extends CargoCondition {
	private static final long serialVersionUID = -6128268661397690352L;

	@OneToOne(mappedBy="actualCondition")
	private Cargo cargo;
	
	public ActualCargoCondition() {
		super();
	}

	public ActualCargoCondition(Cargo cargo, Double temperature) {
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

		if (!(obj instanceof ActualCargoCondition)) {
			return false;
		}
		
		if(getId() == null || ((ActualCargoCondition) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((ActualCargoCondition) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
}
