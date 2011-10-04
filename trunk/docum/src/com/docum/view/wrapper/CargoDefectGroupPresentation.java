package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.docum.domain.po.common.CargoDefect;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.util.AlgoUtil;

public class CargoDefectGroupPresentation implements Serializable {
	private static final long serialVersionUID = 2568146058868607686L;
	private CargoDefectGroup cargoDefectGroup;

	public CargoDefectGroupPresentation(CargoDefectGroup cargoDefectGroup) {
		this.cargoDefectGroup = cargoDefectGroup;
	}

	public CargoDefectGroup getCargoDefectGroup() {
		return cargoDefectGroup;
	}

	public void setCargoDefectGroup(CargoDefectGroup cargoDefectGroup) {
		this.cargoDefectGroup = cargoDefectGroup;
	}	

	public List<CargoDefectPresentation> getDefects() {
		if (cargoDefectGroup == null) {
			return null;
		}
		Collection<CargoDefect> cd = cargoDefectGroup.getDefects();
		List<CargoDefectPresentation> result = new ArrayList<CargoDefectPresentation>(
				cd.size());
		AlgoUtil.transform(result, cd, new CargoDefectTransformer());
		return result;		
	}
	
	public int getDefectCount(){
		if (cargoDefectGroup == null || cargoDefectGroup.getDefects() == null) {
			return 0;
		} else {
			return cargoDefectGroup.getDefects().size();
		}
	}
	
	public String getTitle(){
		return toString();
	}
	
	public String toString(){
		return cargoDefectGroup != null && 
			cargoDefectGroup.getArticleCategory() != null ?
					cargoDefectGroup.getArticleCategory().getName():
						"Категория для группы дефектов не указана...";
	}

}
