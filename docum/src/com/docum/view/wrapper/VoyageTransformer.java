package com.docum.view.wrapper;

import com.docum.domain.po.common.Voyage;
import com.docum.util.AlgoUtil;

public class VoyageTransformer implements AlgoUtil.TransformFunctor<VoyagePresentation, Voyage> {
	private boolean deep = true;
	public VoyageTransformer() {
	}
	public VoyageTransformer(boolean deep) {
		this.deep = deep;
	}
	@Override
	public VoyagePresentation transform(Voyage voyage) {
		return new VoyagePresentation(voyage, deep);
	}
}