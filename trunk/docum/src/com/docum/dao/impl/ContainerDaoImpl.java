package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.docum.dao.ContainerDao;
import com.docum.domain.po.common.Container;

@Repository
public class ContainerDaoImpl extends BaseDaoImpl implements ContainerDao {

	private static final long serialVersionUID = 6738371757481216033L;

	@Override
	public List<Container> getContainersByVoyage(Long voyageId) {
		Query query = entityManager
				.createNamedQuery(GET_CONTAINERS_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}

	@Override
	public List<Container> getContainersByInvoice(Long invoiceId) {
		Query query = entityManager
				.createNamedQuery(GET_CONTAINERS_BY_INVOICE_QUERY);
		query.setParameter("invoiceId", invoiceId);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();
		return result;
	}

}
