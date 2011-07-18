package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Measure;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class CargoPackageDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -7662302467873387704L;
	
	private CargoPackage cargoPackage;
	private List<Measure> measures; 

	public CargoPackageDlgView(CargoPackage cargoPackage, List<Measure> measures) {
		this.measures = measures;
		this.cargoPackage = cargoPackage;
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

	public String getTitle() {						
		return String.format("Редактирование упаковки для груза «%1$s»" , 
				cargoPackage != null && cargoPackage.getCargo() != null ? 
						cargoPackage.getCargo().toString() : "" );
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);		
	}
	
}
