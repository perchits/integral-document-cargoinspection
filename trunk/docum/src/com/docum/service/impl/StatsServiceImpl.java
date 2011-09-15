package com.docum.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.Stats.CargoParty;
import com.docum.domain.po.common.Invoice;
import com.docum.service.InvoiceService;
import com.docum.service.StatsService;
import com.docum.util.stats.StatsCargoGroup;
import com.docum.util.stats.StatsHelper;

@Service(StatsService.SERVICE_NAME)
@Transactional
public class StatsServiceImpl implements StatsService {

	@Autowired
	private InvoiceService invoiceSvc;

	@Override
	public List<CargoParty> getCargoParties(Long invoiceId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<StatsCargoGroup> getCargoGroups(Long invoiceId) {
		Invoice invoice = invoiceSvc.getObject(Invoice.class, invoiceId);
		if(invoice == null) {
			return null;
		}
		return StatsHelper.getCargoGroups(invoice.getContainers());
	}
}
