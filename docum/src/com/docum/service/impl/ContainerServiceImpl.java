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
public class ContainerServiceImpl extends BaseServiceImpl implements
		ContainerService {

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

}
