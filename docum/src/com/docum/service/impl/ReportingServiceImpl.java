package com.docum.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docum.domain.po.common.Container;
import com.docum.service.ReportingService;
import com.docum.util.DocumLogger;
import com.docum.util.XMLUtil;

@Service(ReportingService.SERVICE_NAME)
@Transactional
public class ReportingServiceImpl implements Serializable, ReportingService {
	private static final long serialVersionUID = -4974869292960516986L;
	
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	
	private Container container;

	@Override
	public void createReport(Container container) {
		try {
			this.container = container;
			String reportFileName = "/resultReport";
			String location = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/") +	"/resources/reporting"; 
			OdfTextDocument odt = OdfTextDocument.loadDocument(location + "/documTemplate.odt");
			int l = odt.getContentDom().getChildNodes().getLength();
			for (int i = 0; i < l; i++) {
				Node node = odt.getContentDom().getChildNodes().item(i);
				processNode(node);
			}
			odt.save(location + reportFileName + ".odt");
			//TODO set as Spring bean property
			OpenOfficeConnection officeConnection = new SocketOpenOfficeConnection(8100);
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
			if (propertyValue instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> objects = (List<Object>) propertyValue;
				StringBuffer stringBuffer = new StringBuffer();
				for (Object object: objects) {
					stringBuffer.append(object).append(", ");
				}
				int length = stringBuffer.length();
				stringBuffer.replace(length - 2, length - 1 , "");
				node.setNodeValue(stringBuffer.toString());
			} else {
				node.setNodeValue(
					XMLUtil.propertyUtilsBean.getNestedProperty(container, result).toString());
			}
		}
	}

}
