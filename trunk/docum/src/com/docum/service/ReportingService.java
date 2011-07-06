package com.docum.service;

import com.docum.domain.po.common.Container;

public interface ReportingService {
	public static final String SERVICE_NAME = "reportingService";

	public void createReport(Container container);
}
