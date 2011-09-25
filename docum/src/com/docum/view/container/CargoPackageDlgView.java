package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageWeight;
import com.docum.domain.po.common.Measure;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.wrapper.WeightPresentation;

public class CargoPackageDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -7662302467873387704L;
	
	private CargoPackage cargoPackage;
	private List<Measure> measures; 
	private CargoPackageWeight weight;


	public CargoPackageDlgView(CargoPackage cargoPackage, List<Measure> measures) {
		this.measures = measures;
		this.cargoPackage = cargoPackage;
	}

	public CargoPackageWeight getWeight() {
		return weight;
	}

	public void setWeight(CargoPackageWeight weight) {
		this.weight = weight;
	}
	
	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}	

	public List<Measure> getMeasures() {
		return measures;
	}

	public List<WeightPresentation> getWeights(){
		if (cargoPackage == null) {
			return null;
		}
		List<WeightPresentation> result = new ArrayList<WeightPresentation>();
		for (CargoPackageWeight w : cargoPackage.getWeights()) {
			result.add(new WeightPresentation(w));
		}
		return result;
	}
	
	public void addWeight(){
		CargoPackageWeight weight = new CargoPackageWeight();
		weight.setCargoPackage(cargoPackage);
		cargoPackage.getWeights().add(weight);		
	}
	
	public void removeWeight(){
		cargoPackage.getWeights().remove(weight);		
	}
	
	public boolean isActual(){
		return cargoPackage != null? 
				cargoPackage.getCargo().getCondition().isSurveyable() : false;
	}
	
	public String getTitle() {						
		return String.format("Редактирование упаковки для груза «%1$s»" , 
				cargoPackage != null && cargoPackage.getCargo() != null ? 
						cargoPackage.getCargo().toString() : "" );
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);		
	}
	
}