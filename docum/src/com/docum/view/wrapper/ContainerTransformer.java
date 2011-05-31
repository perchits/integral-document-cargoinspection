package com.docum.view.wrapper;

import com.docum.domain.po.common.Container;
import com.docum.util.AlgoUtil;

public class ContainerTransformer implements
		AlgoUtil.TransformFunctor<ContainerPresentation, Container> {
	@Override
	public ContainerPresentation transform(Container container) {
		return new ContainerPresentation(container);
	}
}
