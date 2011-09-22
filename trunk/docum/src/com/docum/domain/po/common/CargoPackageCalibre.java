package com.docum.domain.po.common;

import java.util.HashSet;
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
public class CargoPackageCalibre extends IdentifiedEntity{
	private static final long serialVersionUID = 5563348067378340274L;

	@ManyToOne(optional=false)
	private CargoPackage cargoPackage;
	
	private String calibreValue;
	
	private double packageCount;

	@OneToMany(mappedBy = "calibre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<CargoCalibreDefect> calibreDefects = new HashSet<CargoCalibreDefect>();
	
	public CargoPackageCalibre() {
		super();
	}

	public CargoPackageCalibre(CargoPackage cargoPackage) {
		this();
		this.cargoPackage = cargoPackage;
	}
	
	public CargoPackageCalibre(CargoPackage cargoPackage, String calibreValue,
			double packageCount) {
		this();
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

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoPackageCalibre)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((CargoPackageCalibre) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
}
