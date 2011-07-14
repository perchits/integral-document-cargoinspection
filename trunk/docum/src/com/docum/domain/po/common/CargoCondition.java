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

	@OneToMany(mappedBy="condition", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoPackage> cargoPackages = new HashSet<CargoPackage>();
	
	Double temperature;

	public CargoCondition() {
		super();
	}

	public CargoCondition(Double temperature) {
		super();
		this.temperature = temperature;
	}

	public abstract Cargo getCargo();

	public abstract void setCargo(Cargo cargo);
	
	public void removePackage(CargoPackage cargoPackage){
		if(cargoPackages.remove(cargoPackage)) {
			cargoPackage.setCondition(null);
		}
	}
	
	public void addPackage(CargoPackage cargoPackage){
		cargoPackages.add(cargoPackage);
		cargoPackage.setCondition(this);
	}
	
	public Set<CargoPackage> getCargoPackages() {
		return cargoPackages;
	}

	public void setCargoPackages(Set<CargoPackage> cargoPackages) {		
		this.cargoPackages = cargoPackages;
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
