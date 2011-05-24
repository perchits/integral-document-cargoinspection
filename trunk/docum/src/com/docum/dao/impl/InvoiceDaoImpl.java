package com.docum.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.docum.dao.InvoiceDao;
import com.docum.domain.po.common.Invoice;

public class InvoiceDaoImpl extends BaseDaoImpl implements InvoiceDao {

	private static final long serialVersionUID = 8418761484297199308L;

	@Override
	public List<Invoice> getInvoicesByVoyage(Long voyageId) {
		Query query = entityManager.createNamedQuery(GET_INVOICES_BY_VOYAGE_QUERY);
		query.setParameter("voyageId", voyageId);
		@SuppressWarnings("unchecked")
		List<Invoice> result = query.getResultList();
		return result;
	}

}
