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
public class CargoPackage extends IdentifiedEntity {
	private static final long serialVersionUID = 1238283870788720201L;

	@ManyToOne(optional=false)
	private	CargoCondition condition;
	
	@ManyToOne(optional=false)
	private Measure measure;

	private double count;
	
	@OneToMany(mappedBy="cargoPackage", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoPackageCalibre> calibres = new HashSet<CargoPackageCalibre>();

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
		return new ArrayList<CargoPackageCalibre>(calibres);
	}

	public void setCalibres(List<CargoPackageCalibre> calibres) {
		this.calibres.clear();
		this.calibres.addAll(calibres);
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
}
