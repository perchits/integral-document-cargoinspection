package com.docum.service.impl;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.domain.po.common.Container;
import com.docum.service.ReportingService;
import com.docum.util.DocumLogger;

@Service(ReportingService.SERVICE_NAME)
@Transactional
public class ReportingServiceImpl implements Serializable, ReportingService {
	private static final long serialVersionUID = -4974869292960516986L;

	@Override
	public void createReport(Container container) {
		try {
			String reportFileName = "/resultReport.odt";
			String location = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/") +	"/resources/reporting"; 
			OdfTextDocument odt = OdfTextDocument.loadDocument(location + "/documTemplate.odt");
			odt.save(location + reportFileName);
	 	} catch (Exception e) {
			DocumLogger.log(e);
		}

	}

}
