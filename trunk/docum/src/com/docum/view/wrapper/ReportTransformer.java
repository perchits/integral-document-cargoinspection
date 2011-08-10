package com.docum.view.wrapper;

import com.docum.domain.po.common.Report;
import com.docum.util.AlgoUtil;

public class ReportTransformer implements
	AlgoUtil.TransformFunctor<ReportPresentation, Report>{

	@Override
	public ReportPresentation transform(Report from) {
		return new ReportPresentation(from);
	}

}
