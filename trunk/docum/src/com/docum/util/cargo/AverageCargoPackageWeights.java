package com.docum.util.cargo;

import java.io.Serializable;

public class AverageCargoPackageWeights implements Serializable {
	private static final long serialVersionUID = 2644759056777972632L;

	private double grossWeight;
	private double netWeight;
	private double tareWeight;

	public AverageCargoPackageWeights(double grossWeight, double netWeight, double tareWeight) {
		super();
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.tareWeight = tareWeight;
	}

	public double getGrossWeight() {
		return grossWeight;
	}
	public double getNetWeight() {
		return netWeight;
	}
	public double getTareWeight() {
		return tareWeight;
	}
}
