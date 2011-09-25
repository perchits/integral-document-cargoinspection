package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.util.AlgoUtil;
import com.docum.util.cargo.AverageCargoPackageWeights;
import com.docum.util.cargo.CargoUtil;

public class CargoPackagePresentation implements Serializable {
	private static final long serialVersionUID = -7646051869636422101L;
	
	private CargoPackage cargoPackage;

	private CargoPresentation cargoPresentation;
	
	private AverageCargoPackageWeights averageWeights;		

	public CargoPackagePresentation(CargoPackage cargoPackage,
			CargoPresentation cargoPresentation) {
		setCargoPackage(cargoPackage);
		this.cargoPresentation = cargoPresentation;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}	

	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
		if(cargoPackage.getCargo().getCondition().isSurveyable()) {
			averageWeights = CargoUtil.calcAverageWeights(cargoPackage.getWeights());
		} else {
			averageWeights = null;
		}
	}

	public List<CargoPackageCalibre> prepareCalibres(){
		if (cargoPackage == null) {
			return null;
		}
		return cargoPackage.getCalibres();
//		if (cargoPackage.getCalibres() == null){
//			return null;
//		}
//		List<CargoPackageCalibre> result = new ArrayList<CargoPackageCalibre>(
//				cargoPackage.getCalibres());
//		Collections.sort(result, new Comparator<CargoPackageCalibre>() {
//			@Override
//			public int compare(CargoPackageCalibre o1, CargoPackageCalibre o2) {
//				return o1.getCalibreValue()
//						.compareTo(o2.getCalibreValue());
//			}
//		});
//		return result;
	}

	public List<CalibrePresentation> getCalibres(){		
		Collection<CargoPackageCalibre> from = prepareCalibres();
		if (from == null) {
			return null;
		}
		List<CalibrePresentation> result = new ArrayList<CalibrePresentation> (from.size());
		AlgoUtil.transform(result, from, new CalibreTransformer());
		return result;
	}
	
	public String getMeasureName(){
		return cargoPackage != null ? cargoPackage.toString() : null;
	}

	public CargoPresentation getCargoPresentation() {
		return cargoPresentation;
	}

	public AverageCargoPackageWeights getAverageWeights() {
		return averageWeights;
	}
	
	
}
