package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.InvoiceDao;
import com.docum.domain.po.common.Invoice;
import com.docum.service.InvoiceService;

@Service(InvoiceService.SERVICE_NAME)
@Transactional
public class InvoiceServiceImpl extends BaseServiceImpl implements InvoiceService {

	private static final long serialVersionUID = 6963837401609745464L;

	@Autowired
	private InvoiceDao invoiceDao;

	@Override
	public List<Invoice> getInvoicesByVoyage(Long voyageId) {
		return invoiceDao.getInvoicesByVoyage(voyageId);
	}

	@Override
	public List<Invoice> getInvoicesByPurchaseOrder(Long orderId) {
		return invoiceDao.getInvoicesByPurchaseOrder(orderId);
	}

	@Override
	public List<Invoice> getInvoicesByBillOfLading(Long billOfLadingId) {
		return invoiceDao.getInvoicesByBillOfLading(billOfLadingId);
	}

}
