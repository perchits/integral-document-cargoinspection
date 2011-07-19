package com.docum.service;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.InspectionBrief;

public interface InspectionBriefService extends BaseService {

	public static final String SERVICE_NAME = "inspectionBriefService";

	public InspectionBrief getInspectionBriefByContainer(Container container);
}
