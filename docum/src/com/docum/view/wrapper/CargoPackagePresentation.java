package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;

public class CargoPackagePresentation implements Serializable {
	private static final long serialVersionUID = -7646051869636422101L;
	
	private CargoPackage cargoPackage;

	private CargoPresentation cargoPresentation;

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}

	public void setCargo(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
	}

	public CargoPackagePresentation(CargoPackage cargoPackage,
			CargoPresentation cargoPresentation) {
		this.cargoPackage = cargoPackage;
		this.cargoPresentation = cargoPresentation;
	}
	
	public List<CargoPackageCalibre> getCalibres(){
		if (cargoPackage == null) {
			return null;
		}
		
		if (cargoPackage.getCalibres() == null){
			return null;
		}
		
		List<CargoPackageCalibre> result = new ArrayList<CargoPackageCalibre>(
				cargoPackage.getCalibres());
		Collections.sort(result, new Comparator<CargoPackageCalibre>() {
			@Override
			public int compare(CargoPackageCalibre o1, CargoPackageCalibre o2) {
				return o1.getCalibreValue()
						.compareTo(o2.getCalibreValue());
			}
		});
		return result;
	}

	public String getMeasureName(){
		return cargoPackage != null ? cargoPackage.toString() : null;
	}

	public CargoPresentation getCargoPresentation() {
		return cargoPresentation;
	}
	
	
}
