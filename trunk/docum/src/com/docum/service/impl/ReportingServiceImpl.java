package com.docum.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docum.dao.ReportingDao;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.service.ReportingService;
import com.docum.util.DocumLogger;
import com.docum.util.XMLUtil;

@Service(ReportingService.SERVICE_NAME)
@Transactional
public class ReportingServiceImpl implements Serializable, ReportingService {
	private static final long serialVersionUID = -4974869292960516986L;
	
	@Autowired
	ReportingDao reportingDao;
	
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	private int starOfficeConnectionPort;
	
	private Container container;

	@Override
	public void createReport(Container container, Long reportId) {
		try {
			if (reportId == null) {
				throw new Exception("Id отчета не может быть пустым при создании файла");
			}
			this.container = container;
			String reportFileName = "/resultReport_" + reportId;
			String location = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/") +	"/resources/reporting"; 
			OdfTextDocument odt = OdfTextDocument.loadDocument(location + "/documTemplate.odt");
			int l = odt.getContentDom().getChildNodes().getLength();
			for (int i = 0; i < l; i++) {
				Node node = odt.getContentDom().getChildNodes().item(i);
				processNode(node);
			}
			odt.save(location + reportFileName + ".odt");
			OpenOfficeConnection officeConnection = 
				new SocketOpenOfficeConnection(starOfficeConnectionPort);
			officeConnection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(officeConnection);
			converter.convert(
				new File(location + reportFileName + ".odt"), 
				new File(location + reportFileName + ".pdf"));
			officeConnection.disconnect();
	 	} catch (Exception e) {
			DocumLogger.log(e);
		}
	}
	
	private void processNode(Node node) throws Exception {
		if (node.getNodeValue() != null) {
			int statementBeginPos = node.getNodeValue().indexOf(STATEMENT_BEGIN);
			int statementEndPos = node.getNodeValue().indexOf(STATEMENT_END);
			if (statementBeginPos != -1) {
				replaceNodeValue(node, node.getNodeValue(), this.container, 
					statementBeginPos, statementEndPos);
			}
		}
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				processNode(nodeList.item(i));
			}
		}
	}
	
	private void replaceNodeValue(Node node, String processedValue, 
			Container container, int statementBeginPos, int statementEndPos) throws Exception {
		if (statementEndPos == -1) {
			return;
		}
		String result = processedValue.substring(
			statementBeginPos + STATEMENT_BEGIN.length(), statementEndPos);
		String props[] = result.split("\\.");
		Object propertyValue = XMLUtil.getObjectProperty(container, props[0]);
		if (propertyValue != null) {
			if (propertyValue instanceof List && props.length == 1) {
				@SuppressWarnings("unchecked")
				List<Object> objects = (List<Object>) propertyValue;
				node.setNodeValue(getResult(objects));
			} else if (propertyValue instanceof List && props.length == 2) {
				@SuppressWarnings("unchecked")
				List<Object> objects = (List<Object>) propertyValue;
				List<Object> values = new ArrayList<Object>();
				for (Object object: objects) {
					values.add(XMLUtil.propertyUtilsBean.getSimpleProperty(object, props[1]));
				}
				node.setNodeValue(getResult(values));
			} else {
				node.setNodeValue(
					XMLUtil.propertyUtilsBean.getNestedProperty(container, result).toString());
			}
		}
	}
	
	private String getResult(List<Object> data) {
		StringBuffer result = new StringBuffer();
		Set<Object> uniqueObjects = new HashSet<Object>();
		for (Object object: data) {
			uniqueObjects.add(object.toString());
		}
		for (Object object: uniqueObjects) {
			result.append(object).append(", ");
		}
		int length = result.length();
		result.replace(length - 2, length - 1 , "");
		
		return result.toString();
	}

	public int getStarOfficeConnectionPort() {
		return starOfficeConnectionPort;
	}

	public void setStarOfficeConnectionPort(int starOfficeConnectionPort) {
		this.starOfficeConnectionPort = starOfficeConnectionPort;
	}
	
	@Override
	public List<Report> getReportsByVoyage(Long voyageId) {
		return reportingDao.getReportsByVoyage(voyageId);
	}

}
