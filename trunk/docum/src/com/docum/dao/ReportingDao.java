package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Report;

public interface ReportingDao extends BaseDao {
	public static final String GET_REPORTS_BY_VOYAGE_QUERY = "getReportsByVoyage";
	
	public List<Report> getReportsByVoyage(Long voyageId);

}
