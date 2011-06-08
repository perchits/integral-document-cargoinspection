package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.docum.dao.VoyageDao;
import com.docum.domain.po.common.Voyage;

@Repository("voyageDao")
public class VoyageDaoImpl extends BaseDaoImpl implements VoyageDao {
	private static final long serialVersionUID = -6464067549821983064L;

	@Override
	public List<Voyage> getVoyagesByFinishStatus(boolean finished) {
		TypedQuery<Voyage> query = entityManager.createQuery(
			"select v from Voyage v where v.finished = :finished", Voyage.class);
		query.setParameter("finished", finished);
		return query.getResultList();
	}

	@Override
	public List<Voyage> getVoyagesByInvoice(Long invoiceId) {
		Query query = entityManager.createNamedQuery(GET_VOYAGES_BY_INVOICE);
		query.setParameter("invoiceId", invoiceId);
		@SuppressWarnings("unchecked")
		List<Voyage> result = query.getResultList();
		return result;
	}

	@Override
	public List<Voyage> getVoyagesByPurchaseOrder(Long orderId) {
		Query query = entityManager.createNamedQuery(GET_VOYAGES_BY_PURCHASE_ORDER);
		query.setParameter("orderId", orderId);
		@SuppressWarnings("unchecked")
		List<Voyage> result = query.getResultList();
		return result;
	}

	@Override
	public List<Voyage> getVoyagesByBillOfLading(Long billOfLadingId) {
		Query query = entityManager.createNamedQuery(GET_VOYAGES_BY_BILL_OF_LADING);
		query.setParameter("billOfLadingId", billOfLadingId);
		@SuppressWarnings("unchecked")
		List<Voyage> result = query.getResultList();
		return result;
	}

}
