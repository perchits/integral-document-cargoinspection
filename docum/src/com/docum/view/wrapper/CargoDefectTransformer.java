package com.docum.view.wrapper;

import com.docum.domain.po.common.CargoDefect;
import com.docum.util.AlgoUtil;

public class CargoDefectTransformer implements
AlgoUtil.TransformFunctor<CargoDefectPresentation, CargoDefect> {
	@Override
	public CargoDefectPresentation transform(CargoDefect from) {		
		return new CargoDefectPresentation(from);
	}	
}