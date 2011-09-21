package com.docum.view.wrapper;

import com.docum.domain.po.common.Cargo;
import com.docum.service.CargoService;
import com.docum.util.AlgoUtil;

public class CargoTransformer implements
		AlgoUtil.TransformFunctor<CargoPresentation, Cargo> {
	private CargoService cargoService;
	
	public CargoTransformer(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	@Override
	public CargoPresentation transform(Cargo cargo) {
		return new CargoPresentation(cargoService, cargo);
	}
}
