package com.docum.util.cargo;

public class AverageCargoPackageWeights {
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