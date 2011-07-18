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
public class CargoPackage extends IdentifiedEntity {
	private static final long serialVersionUID = 1238283870788720201L;

	@ManyToOne(optional=false)
	private	Cargo cargo;
	
	@ManyToOne(optional=false)
	private Measure measure;

	private double count;
	
	@OneToMany(mappedBy="cargoPackage", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoPackageCalibre> calibres = new HashSet<CargoPackageCalibre>();

	public CargoPackage() {
		super();
	}

	public CargoPackage(Cargo cargo) {
		this();
		this.setCargo(cargo);
	}
	
	public CargoPackage(Cargo cargo, Measure measure, Double count) {
		this();
		this.setCargo(cargo);
		this.measure = measure;
		this.count = count;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public Set<CargoPackageCalibre> getCalibres() {
		return calibres;
	}

	public void setCalibres(Set<CargoPackageCalibre> calibres) {		
		this.calibres = calibres;
	}
	
	public void addCalibre(CargoPackageCalibre calibre){
		calibres.add(calibre);
		calibre.setCargoPackage(this);
	}
	
	public void removeCalibre(CargoPackageCalibre calibre) {
		if (calibres.remove(calibre)) {
			calibre.setCargoPackage(null);
		}
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof CargoPackage)) {
			return false;
		}

		return EqualsHelper.equals(getId(), ((CargoPackage) obj).getId());
	}

	public int hashCode() {
		return HashCodeHelper.hashCode(getId());
	}
	
	@Override
	public String toString() {		
		return getMeasure() != null ? getMeasure().getName() : "Не указано измерение"; 
	}
}
