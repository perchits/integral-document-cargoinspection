package com.docum.view.wrapper;

import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.util.AlgoUtil;

public class CalibreTransformer implements
AlgoUtil.TransformFunctor<CalibrePresentation, CargoPackageCalibre> {
	@Override
	public CalibrePresentation transform(CargoPackageCalibre from) {		
		return new CalibrePresentation(from);
	}	
}