package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.BillOfLadingDao;
import com.docum.domain.po.common.BillOfLading;
import com.docum.service.BillOfLadingService;

@Service(BillOfLadingService.SERVICE_NAME)
@Transactional
public class BillOfLadingServiceImpl extends BaseServiceImpl implements
		BillOfLadingService {

	private static final long serialVersionUID = -1266440231946079043L;

	@Autowired
	private BillOfLadingDao billOfLadingDao;

	@Override
	public List<BillOfLading> getBillsByVoyage(Long voyageId) {
		return billOfLadingDao.getBillsByVoyage(voyageId);
	}

	@Override
	public List<BillOfLading> getBillsByInvoice(Long invoiceId) {
		return billOfLadingDao.getBillsByInvoice(invoiceId);
	}

	@Override
	public List<BillOfLading> getBillsByPurchaseOrder(Long orderId) {
		return billOfLadingDao.getBillsByPurchaseOrder(orderId);
	}

}
