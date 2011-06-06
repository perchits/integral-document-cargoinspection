package com.docum.domain.po.common;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoPackage extends IdentifiedEntity {
	private static final long serialVersionUID = 1238283870788720201L;

	@ManyToOne(optional=false)
	private	CargoCondition condition;
	
	@ManyToOne(optional=false)
	private Measure measure;

	private double count;
	
	@OneToMany(mappedBy="cargoPackage")
	private List<CargoPackageCalibre> calibres;

	public CargoPackage() {
	}

	public CargoPackage(CargoCondition condition, Measure measure, Double count) {
		super();
		this.setCondition(condition);
		this.measure = measure;
		this.count = count;
	}

	public CargoCondition getCondition() {
		return condition;
	}

	public void setCondition(CargoCondition condition) {
		this.condition = condition;
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

	public List<CargoPackageCalibre> getCalibres() {
		return calibres;
	}

	public void setCalibres(List<CargoPackageCalibre> calibres) {
		this.calibres = calibres;
	}

}
