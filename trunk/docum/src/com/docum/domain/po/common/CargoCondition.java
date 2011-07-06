package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.util.EqualsHelper;
import com.docum.util.HashCodeHelper;

@Entity
public class CargoCondition extends IdentifiedEntity {
	private static final long serialVersionUID = 6450548355480762800L;

	@ManyToOne(optional=false)
	private Cargo cargo;
	
	@OneToMany(mappedBy="condition", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoPackage> cargoPackages = new HashSet<CargoPackage>();
	
	Double temperature;

	public CargoCondition() {
		super();
	}

	public CargoCondition(Cargo cargo, Double temperature) {
		super();
		this.cargo = cargo;
		this.temperature = temperature;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public List<CargoPackage> getCargoPackages() {
		return new ArrayList<CargoPackage>(cargoPackages);
	}

	public void setCargoPackages(List<CargoPackage> cargoPackages) {
		this.cargoPackages.clear();
		this.cargoPackages.addAll(cargoPackages);
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoCondition)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((CargoCondition) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
