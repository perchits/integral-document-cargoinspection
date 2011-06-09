package com.docum.service;

import java.util.List;

import com.docum.domain.po.common.Invoice;

public interface InvoiceService extends BaseService {

	public static final String SERVICE_NAME = "invoiceService";

	public List<Invoice> getInvoicesByVoyage(Long voyageId);
	public List<Invoice> getInvoicesByPurchaseOrder(Long orderId);
	public List<Invoice> getInvoicesByBillOfLading(Long billOfLadingId);
}
