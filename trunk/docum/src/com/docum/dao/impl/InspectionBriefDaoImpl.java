package com.docum.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.InspectionBriefDao;
import com.docum.domain.po.common.InspectionBrief;

@Repository
public class InspectionBriefDaoImpl extends BaseDaoImpl implements InspectionBriefDao {
	private static final long serialVersionUID = 1181415270009680920L;

	@Override
	public InspectionBrief getInspectionBriefByContainer(Long containerId) {
		Query query = entityManager.createNamedQuery(GET_INSPECTION_BRIEF_BY_CONTAINER_QUERY);
		query.setParameter("containerId", containerId);
		//TODO refactor
		if (query.getResultList().size() > 0) {
			return (InspectionBrief) query.getSingleResult();
		} else {
			return null;
		}
	}

}
