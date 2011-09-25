package com.docum.view.wrapper;

import java.io.Serializable;

import com.docum.domain.po.common.CargoPackageWeight;

public class WeightPresentation implements Serializable {
	private static final long serialVersionUID = 2247872844184349325L;
	private CargoPackageWeight weight;
	public CargoPackageWeight getWeight() {
		return weight;
	}
	public void setWeight(CargoPackageWeight weight) {
		this.weight = weight;
	}
	public WeightPresentation(CargoPackageWeight weight) {
		this.weight = weight;
	}
	
	public Double getNetWeight(){
		return weight != null ? 
				weight.getGrossWeight() - weight.getTareWeight(): null;
	}
	
}
