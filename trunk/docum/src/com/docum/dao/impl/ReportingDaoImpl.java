package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.ReportingDao;
import com.docum.domain.po.common.Report;

@Repository
public class ReportingDaoImpl extends BaseDaoImpl implements ReportingDao {
	private static final long serialVersionUID = 5251330002523259979L;

	@Override
	public List<Report> getReportsByVoyage(Long voyageId) {
		Query query = entityManager.createNamedQuery(GET_REPORTS_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<Report> result = query.getResultList();
		return result;
	}

}
