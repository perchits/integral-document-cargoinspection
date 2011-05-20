package com.docum.domain.po.common;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoCondition extends IdentifiedEntity {
	private static final long serialVersionUID = 6450548355480762800L;

	@OneToMany
	private List<CargoPackage> cargoPackage;
	
	Double temperature;

	public CargoCondition() {
		super();
	}

	public CargoCondition(List<CargoPackage> cargoPackage, Double temperature) {
		super();
		this.cargoPackage = cargoPackage;
		this.temperature = temperature;
	}
	
	public List<CargoPackage> getCargoPackage() {
		return cargoPackage;
	}

	public void setCargoPackage(List<CargoPackage> cargoPackage) {
		this.cargoPackage = cargoPackage;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

}
