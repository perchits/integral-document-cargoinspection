package com.docum.view.wrapper;

import com.docum.domain.po.common.Cargo;
import com.docum.util.AlgoUtil;

public class CargoTransformer implements
		AlgoUtil.TransformFunctor<CargoPresentation, Cargo> {
	@Override
	public CargoPresentation transform(Cargo cargo) {
		return new CargoPresentation(cargo);
	}
}
