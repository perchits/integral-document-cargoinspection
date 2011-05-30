package com.docum.view.wrapper;

import com.docum.domain.po.common.Voyage;
import com.docum.util.AlgoUtil;

public class VoyageTransformer implements
		AlgoUtil.TransformFunctor<VoyagePresentation, Voyage> {
	@Override
	public VoyagePresentation transform(Voyage voyage) {
		return new VoyagePresentation(voyage);
	}
}