package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoPackageWeight extends IdentifiedEntity {
	private static final long serialVersionUID = -1413607642603585075L;

	@ManyToOne(optional=false)
	private	CargoPackage cargoPackage;

	private double grossWeight;

	private double tareWeight;
	
	public CargoPackageWeight() {
		super();
	}

	public CargoPackageWeight(CargoPackage cargoPackage, double grossWeight, double tareWeight) {
		this();
		this.cargoPackage = cargoPackage;
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}

	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(double tareWeight) {
		this.tareWeight = tareWeight;
	}
	
}
