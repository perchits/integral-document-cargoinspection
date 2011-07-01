package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoPackageCalibre extends IdentifiedEntity{
	private static final long serialVersionUID = 5563348067378340274L;

	@ManyToOne(optional=false)
	private CargoPackage cargoPackage;
	
	private String calibreValue;
	
	private double packageCount;

	public CargoPackageCalibre() {
		super();
	}
	
	public CargoPackageCalibre(CargoPackage cargoPackage, String calibreValue,
			double packageCount) {
		super();
		this.cargoPackage = cargoPackage;
		this.calibreValue = calibreValue;
		this.packageCount = packageCount;
	}
	
	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}

	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
	}

	public String getCalibreValue() {
		return calibreValue;
	}

	public void setCalibreValue(String calibreValue) {
		this.calibreValue = calibreValue;
	}

	public double getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(double packageCount) {
		this.packageCount = packageCount;
	}
}