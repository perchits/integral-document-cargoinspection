package com.docum.util.cargo;

import java.util.Collection;

import com.docum.domain.po.common.CargoPackageWeight;

public class CargoUtil {
	public static AverageCargoPackageWeights calcAverageWeights(
			Collection<CargoPackageWeight> weights) {
		double grossWeight = 0.0;
		double tareWeight = 0.0;
		if(!weights.isEmpty()) {
			for(CargoPackageWeight weight : weights) {
				grossWeight += weight.getGrossWeight();
				tareWeight += weight.getTareWeight();
			}
			grossWeight /= weights.size();
			tareWeight /= weights.size();
		}
		return new AverageCargoPackageWeights(grossWeight, grossWeight-tareWeight, tareWeight);
	}
}
