package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class CargoPackage extends IdentifiedEntity {
	private static final long serialVersionUID = 1238283870788720201L;

	@ManyToOne(optional=false)
	private	CargoCondition condition;
	
	@ManyToOne(optional=false)
	private Measure measure;

	private double count;

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

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

}
