package com.docum.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.InspectionBriefDao;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.InspectionBrief;

@Repository
public class InspectionBriefDaoImpl extends BaseDaoImpl implements InspectionBriefDao {
	private static final long serialVersionUID = 1181415270009680920L;

	@Override
	public InspectionBrief getInspectionBriefByContainer(Container container) {
		Query query = entityManager.createNamedQuery(GET_INSPECTION_BRIEF_BY_CONTAINER_QUERY);
		query.setParameter("container", container);
		InspectionBrief result = (InspectionBrief) query.getSingleResult();
		return result;
	}

}
