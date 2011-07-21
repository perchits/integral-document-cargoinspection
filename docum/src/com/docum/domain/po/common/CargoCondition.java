package com.docum.domain.po.common;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class CargoCondition extends IdentifiedEntity {
	private static final long serialVersionUID = 6450548355480762800L;

	@OneToMany(mappedBy = "condition", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<Cargo> cargoes = new HashSet<Cargo>();
	
	Double temperature;

	public CargoCondition() {
		super();
	}

	public CargoCondition(Double temperature) {
		super();
		this.temperature = temperature;
	}

	public abstract Container getContainer();

	public abstract void setContainer(Container container);
	
	public abstract boolean hasDefects();
	
	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Set<Cargo> getCargoes() {
		return cargoes;
	}

	public void setCargoes(Set<Cargo> cargoes) {
		this.cargoes = cargoes;
	}

	public void addCargo(Cargo cargo) {
		if(cargo != null) {
			cargoes.add(cargo);
			cargo.setCondition(this);
		}
	}

	public void removeCargo(Cargo cargo) {
		if(cargo != null && cargoes.remove(cargo)) {
			cargoes.remove(cargo);
			cargo.setCondition(null);
		}
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoCondition)) {
			return false;
		}
		
		if(getId() == null || ((CargoCondition) obj).getId() == null) {
			return false;
		}
		return EqualsHelper.equals(getId(), ((CargoCondition) obj).getId());
	}

	public int hashCode() {
		if(getId() == null) {
			return super.hashCode();
		}
		return HashCodeHelper.hashCode(getId());
	}
}
