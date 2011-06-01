package com.docum.domain.po.common;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoCondition extends IdentifiedEntity {
	private static final long serialVersionUID = 6450548355480762800L;

	@OneToMany(mappedBy="condition")
	private List<CargoPackage> cargoPackages;
	
	Double temperature;

	public CargoCondition() {
		super();
	}

	public CargoCondition(Cargo cargo, Double temperature) {
		super();
		this.temperature = temperature;
	}
	
	public List<CargoPackage> getCargoPackages() {
		return cargoPackages;
	}

	public void setCargoPackages(List<CargoPackage> cargoPackage) {
		this.cargoPackages = cargoPackage;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

}
