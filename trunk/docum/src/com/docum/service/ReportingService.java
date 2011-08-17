package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.Report;

public interface ReportingService {
	public static final String SERVICE_NAME = "reportingService";
	public static final String REPORTS_LOCATION = "/resources/reporting";
	public static final String REPORT_FILENAME_PREFIX = "/resultReport_";
	public static final String REPORT_TEMPLATE_FILENAME = "/documTemplate.odt";

	public void createReport(Report report) throws Exception;
	public List<Report> getReportsByVoyage(Long voyageId);
	public boolean checkStarOfficeConnection();
}
