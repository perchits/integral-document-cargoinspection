package com.docum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.ContainerDao;
import com.docum.domain.po.common.Container;
import com.docum.service.ContainerService;

@Service(ContainerService.SERVICE_NAME)
@Transactional
public class ContainerServiceImpl extends BaseServiceImpl implements ContainerService {

	private static final long serialVersionUID = 9011562038896560162L;

	@Autowired
	public ContainerDao containerDao;
	
	@Override
	public List<Container> getContainersByVoyage(Long voyageId) {		
		return containerDao.getContainersByVoyage(voyageId);
	}

	@Override
	public List<Container> getContainersByInvoice(Long invoiceId) {
		return containerDao.getContainersByInvoice(invoiceId);
	}

	@Override
	public List<Container> getContainersByPurchaseOrder(Long orderId) {
		return containerDao.getContainersByPurchaseOrder(orderId);
	}

	@Override
	public List<Container> getContainersByBillOfLading(Long billOfLadingId) {
		return containerDao.getContainersByBillOfLading(billOfLadingId);
	}

		
	@Override
	public List<Container> getContainersWithoutReport() {
		return containerDao.getContainersWithoutReport();
	}
	
	@Override
	public List<Container> getContainersByReport(Long reportId) {
		return containerDao.getContainersByReport(reportId);
	}
	
	@Override
	public List<Container> getContainersWithoutReportByVoyage(Long voyageId) {
		return containerDao.getContainersWithoutReportByVoyage(voyageId);
	}
}
