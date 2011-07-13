package com.docum.view.wrapper;

import com.docum.domain.po.common.CargoPackage;
import com.docum.util.AlgoUtil;

public class CargoPackageTransformer implements
AlgoUtil.TransformFunctor<CargoPackagePresentation, CargoPackage> {

	@Override
	public CargoPackagePresentation transform(CargoPackage from) {		
		return new CargoPackagePresentation(from);
	}

}
