package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.Invoice;

public interface InvoiceDao extends BaseDao {

	public static final String GET_INVOICES_BY_VOYAGE_QUERY = "getInvoicesByVoyage";

	public List<Invoice> getInvoicesByVoyage(Long voyageId);

}