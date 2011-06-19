package com.docum.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.common.Voyage;
import com.docum.service.BaseService;
import com.docum.util.AlgoUtil;
import com.docum.view.wrapper.VoyagePresentation;
import com.docum.view.wrapper.VoyageTransformer;

@Controller("documentBean")
@Scope("session")
public class DocumentView implements Serializable {
	private static final long serialVersionUID = -2377086383437629982L;
	
	@Autowired
	InvoiceView invoiceView;
	@Autowired
	OrderView orderView;
	@Autowired
	BillOfLadingView billOfLadingView;
	@Autowired
	private BaseService baseService;
	
	private static final int MAX_LIST_SIZE = 10;
	private VoyagePresentation selectedVoyage;
	
	public List<VoyagePresentation> voyageAutocomplete(Object suggest) throws Exception {
		String pref = (String) suggest;
		ArrayList<VoyagePresentation> result = new ArrayList<VoyagePresentation>();
		
		for (VoyagePresentation voyage : getVoyages()) {
			if ((voyage.getVoyageInfo() != null && voyage.getVoyageInfo()
					.toLowerCase().indexOf(pref.toLowerCase()) >= 0)
					|| "".equals(pref)) {
				result.add(voyage);
				if (result.size() >= MAX_LIST_SIZE)
					break;
			}
		}
		return result;
	}
	
	public List<VoyagePresentation> getVoyages() {
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("arrivalDate", SortOrderEnum.DESC);
		List<Voyage> voyages = (List<Voyage>) baseService.getAll(Voyage.class, sortFields);
		List<VoyagePresentation> result = new ArrayList<VoyagePresentation>(voyages.size());
		AlgoUtil.transform(result, voyages, new VoyageTransformer(false));
		return result;
	}
	
	public void voyageSelect(SelectEvent event) {
		invoiceView.setSelectedVoyage(selectedVoyage.getVoyage());
		invoiceView.refreshObjects();
		orderView.setSelectedVoyage(selectedVoyage.getVoyage());
		orderView.refreshObjects();
		billOfLadingView.setSelectedVoyage(selectedVoyage.getVoyage());
		billOfLadingView.refreshObjects();
	}

	public VoyagePresentation getSelectedVoyage() {
		return selectedVoyage;
	}

	public void setSelectedVoyage(VoyagePresentation selectedVoyage) {
		this.selectedVoyage = selectedVoyage;
	}

}
