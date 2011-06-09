package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.PurchaseOrderDao;
import com.docum.domain.po.common.PurchaseOrder;
import com.docum.service.PurchaseOrderService;

@Service(PurchaseOrderService.SERVICE_NAME)
@Transactional
public class PurchaseOrderServiceImpl extends BaseServiceImpl implements PurchaseOrderService {
	private static final long serialVersionUID = -4434116303863604405L;
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Override
	public List<PurchaseOrder> getOrdersByVoyage(Long voyageId) {
		return purchaseOrderDao.getOrdersByVoyage(voyageId);
	}

	@Override
	public List<PurchaseOrder> getOrdersByInvoice(Long invoiceId) {
		return purchaseOrderDao.getOrdersByInvoice(invoiceId);
	}

	@Override
	public List<PurchaseOrder> getOrdersByBillOfLading(Long billOfLadingId) {
		return purchaseOrderDao.getOrdersByBillOfLading(billOfLadingId);
	}

}
