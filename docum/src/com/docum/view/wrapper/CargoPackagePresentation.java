package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.docum.domain.Stats;
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
	
	private List<AvgDefect> avgDefects = new ArrayList<AvgDefect>();
		
	public CargoPackagePresentation(CargoPackage cargoPackage,
			CargoPresentation cargoPresentation) {
		setCargoPackage(cargoPackage);
		this.cargoPresentation = cargoPresentation;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}
	
	public boolean isActual(){
		return cargoPackage != null ? cargoPackage.getCargo().getCondition().isSurveyable():
			false;
	}
	
	public boolean isAvgWeight(){
		return averageWeights != null &&  isActual();
	}

	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
		if(cargoPackage.getCargo().getCondition().isSurveyable()) {
			averageWeights = CargoUtil.calcAverageWeights(cargoPackage.getWeights());
			setAvgDefects(CargoUtil.calcAverageDefects(cargoPackage.getCargo()));
		} else {
			averageWeights = null;
			avgDefects.clear();
		}
	}
	
	public void setAvgDefects(Stats.CargoDefects defects){
		avgDefects.clear();
		if (defects == null || defects.getCategoryNames() == null) {
			return;
		}
		for (int i = 0 ; i <  defects.getCategoryNames().length; i++){
			avgDefects.add(new AvgDefect(
				defects.getCategoryNames()[i],
				defects.getAverageCalibreDefects().getPercentages()[i]
			));			
		}		
	}
	
	public List<AvgDefect> getAvgDefects() {
		return avgDefects;
	}
	
	public List<CargoPackageCalibre> prepareCalibres(){
		if (cargoPackage == null) {
			return null;
		}
		return cargoPackage.getCalibres();
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
	
	public static class AvgDefect implements Serializable {
		private static final long serialVersionUID = -7317123835372816280L;
		private String categoryName;
		private double percentage;			
		
		public AvgDefect(String categoryName, double percentage) {
			this.categoryName = categoryName;
			this.percentage = percentage;
		}
		
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		public double getPercentage() {
			return percentage;
		}
		public void setPercentage(double percentage) {
			this.percentage = percentage;
		}
				
	}
	
}
