package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;

public interface ReportingService {
	public static final String SERVICE_NAME = "reportingService";

	public void createReport(Container container, Long reportId);
	public List<Report> getReportsByVoyage(Long voyageId);
	public boolean checkStarOfficeConnection();
}
