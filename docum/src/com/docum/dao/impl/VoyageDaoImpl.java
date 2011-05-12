package com.docum.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.docum.dao.VoyageDao;
import com.docum.persistence.common.Voyage;

@Service("voyageDao")
public class VoyageDaoImpl extends BaseDaoImpl implements VoyageDao {
	private static final long serialVersionUID = -6464067549821983064L;

	@Override
	public List<Voyage> getVoyagesByFinishStatus(boolean finished) {
		TypedQuery<Voyage> query = entityManager.createQuery(
			"select v from Voyage v where v.finished = :finished", Voyage.class);
		query.setParameter("finished", finished);
		return query.getResultList();
	}

}
