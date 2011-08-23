package com.docum.service.impl;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
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
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.FileUrl;
import com.docum.domain.po.common.Report;
import com.docum.service.ApplicationConfigService;
import com.docum.service.BaseService;
import com.docum.service.ReportingService;
import com.docum.util.ListHandler;
import com.docum.util.XMLUtil;
import com.docum.view.wrapper.ContainerPresentation;

@Service(ReportingService.SERVICE_NAME)
@Transactional
public class ReportingServiceImpl implements Serializable, ReportingService {
	private static final long serialVersionUID = -4974869292960516986L;
	
	@Autowired
	ReportingDao reportingDao;
	@Autowired
	BaseService baseService;
	@Autowired
	ApplicationConfigService applicationConfigService;
	
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	private int starOfficeConnectionPort;
	private List<Container> containers;
	private Map<Container, ContainerPresentation> containerPresentationMap;
	private Map<String, Object[]> temlateAccordance = new HashMap<String, Object[]>();

	@Override
	public void createReport(Report report) throws Exception {
		if (report == null || report.getId() == null) {
			throw new Exception("Id отчета не может быть пустым при создании файла");
		}
		containers = new ArrayList<Container>();
		containerPresentationMap = new HashMap<Container, ContainerPresentation>();
		for (Container container: report.getContainers()) {
			Container c = this.baseService.getObject(Container.class, container.getId());
			this.containers.add(c);
			containerPresentationMap.put(c, new ContainerPresentation(c));
		}
		initTemlateAccordance(report);
		String reportFileName = REPORT_FILENAME_PREFIX + report.getId();
		String location = FacesContext.getCurrentInstance().getExternalContext()
			.getRealPath("/") +	REPORTS_LOCATION; 
		OdfTextDocument odt = OdfTextDocument.loadDocument(location + REPORT_TEMPLATE_FILENAME);
		int l = odt.getContentDom().getChildNodes().getLength();
		for (int i = 0; i < l; i++) {
			Node node = odt.getContentDom().getChildNodes().item(i);
			processNode(node);
		}
		l = odt.getStylesDom().getChildNodes().getLength();
		for (int i = 0; i < l; i++) {
			Node node = odt.getStylesDom().getChildNodes().item(i);
			processNode(node);
		}
		addMarksImages(odt);
		odt.save(location + reportFileName + ".odt");
		OpenOfficeConnection officeConnection = 
			new SocketOpenOfficeConnection(starOfficeConnectionPort);
		officeConnection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(officeConnection);
		converter.convert(
			new File(location + reportFileName + ".odt"), 
			new File(location + reportFileName + ".pdf"));
		officeConnection.disconnect();
	}
	
	private void addMarksImages(OdfTextDocument odt) throws Exception {
		OdfTable odfTable = odt.getTableByName("TableShippingMark");
		for (Container container: this.containers) {
			if (container.getActualCondition() == null) {
				continue;
			}
			Set<Cargo> cargoes = container.getActualCondition().getCargoes();
			if (cargoes == null || cargoes.size() == 0) {
				int currRow = odfTable.getRowCount() - 1;
				if ( currRow > 1) {
					odfTable.appendRow();
					odfTable.appendRow();
					currRow += 2;
				} else {
					odfTable.appendRow();
				}
				odfTable.getCellByPosition(0, currRow).setStringValue(container.getNumber());
				odfTable.getCellByPosition(0, currRow + 1).setStringValue("UNAVAILABLE");
				odfTable.getCellByPosition(1, currRow + 1).setStringValue("Нет фотографии");
				continue;
			}
			int currRow = odfTable.getRowCount() - 1;
			if (currRow > 1) {
				odfTable.appendRow();
				currRow++;
			}
			odfTable.getCellByPosition(0, currRow).setStringValue(container.getNumber());
			for (Cargo cargo: cargoes) {
				odfTable.appendRow();
				currRow = odfTable.getRowCount() - 1;
				OdfTableCell odfTableCell;
				FileUrl fileUrl = cargo.getInspectionInfo().getShippingMarkEng();
				if (fileUrl != null) {
					addImage(odt, fileUrl, odfTable, 0, currRow);
				} else {
					odfTableCell = odfTable.getCellByPosition(0, currRow);
					odfTableCell.setStringValue("UNAVAILABLE");					
				}
				fileUrl = cargo.getInspectionInfo().getShippingMark();
				if (fileUrl != null) {
					addImage(odt, fileUrl, odfTable, 1, currRow);
				} else {
					odfTableCell = odfTable.getCellByPosition(1, currRow);
					odfTableCell.setStringValue("Нет фотографии");					
				}
			}
		}
	}
	
	private void addImage(OdfTextDocument odt, FileUrl imageURL, OdfTable odfTable, 
			int column, int row) throws Exception {
		String storagePath = "file:///" + applicationConfigService.getImagesStoragePath() + "/";
		odt.getPackage().insert(new URI(storagePath + imageURL.getValue()), 
			imageURL.getValue(), null);
		OdfTableCell odfTableCell = odfTable.getCellByPosition(column, row);
		TableTableCellElement tableCellElement = 
			(TableTableCellElement) odfTableCell.getOdfElement();
		TextPElement textPElement = tableCellElement.newTextPElement();
		initImageAttributes(textPElement.newDrawFrameElement(), imageURL.getValue());
	}
	
	private void initImageAttributes(DrawFrameElement drawFrameElement, String fileUrl)
			throws Exception{
		if (fileUrl == null) {
			throw new Exception("Не указано расположение изображения");
		}
		drawFrameElement.setDrawZIndexAttribute(0);
		drawFrameElement.setDrawStyleNameAttribute("fr1");
		drawFrameElement.setSvgHeightAttribute("6.283cm");
		drawFrameElement.setSvgWidthAttribute("8.371cm");
		DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
		drawImageElement.setXlinkHrefAttribute(fileUrl);
		drawImageElement.setXlinkActuateAttribute("onLoad");
		drawImageElement.setXlinkShowAttribute("embed");
		drawImageElement.setXlinkTypeAttribute("simple");
	}
	
	private void initTemlateAccordance(final Report report) {
		this.temlateAccordance.put("Report",  new Object[]{report, "number"});
		this.temlateAccordance.put("actualCargoesEnglishName",  
			new Object[]{containerPresentationMap, "actualCargoesEnglishName"});
		this.temlateAccordance.put("actualCargoesName",  
			new Object[]{containerPresentationMap, "actualCargoesName"});
		this.temlateAccordance.put("cargoSuppliers",  
				new Object[]{containerPresentationMap, "actualCargoSuppliers"});
		this.temlateAccordance.put("cargoSuppliersEng",  
				new Object[]{containerPresentationMap, "actualCargoSuppliersEng"});
	}
	
	public boolean checkStarOfficeConnection() {
		try {
			OpenOfficeConnection officeConnection = 
				new SocketOpenOfficeConnection(starOfficeConnectionPort);
			officeConnection.connect();
			officeConnection.disconnect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private void processNode(Node node) throws Exception {
		if (node.getNodeValue() != null) {
			int statementBeginPos = node.getNodeValue().indexOf(STATEMENT_BEGIN);
			int statementEndPos = node.getNodeValue().indexOf(STATEMENT_END);
			if (statementBeginPos != -1) {
				replaceNodeValue(node, node.getNodeValue(), statementBeginPos, statementEndPos);
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
			int statementBeginPos, int statementEndPos) throws Exception {
		if (statementEndPos == -1) {
			return;
		}
		String result = processedValue.substring(
			statementBeginPos + STATEMENT_BEGIN.length(), statementEndPos);
		if (this.temlateAccordance.keySet().contains(result)) {
			Object[] o = this.temlateAccordance.get(result);
			if (o[0] instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> objectsMap = (Map<Object, Object>) o[0];
				List<Object> propertyValues = new ArrayList<Object>();
				for (Object keyObject:  objectsMap.keySet()) {
					Object valueObject = objectsMap.get(keyObject);
					Object nestedPropertValue = 
						XMLUtil.propertyUtilsBean.getNestedProperty(valueObject, o[1].toString());
					propertyValues.add(nestedPropertValue);
				}
				node.setNodeValue(ListHandler.getUniqueResult(propertyValues));
			} else {
				Object value = XMLUtil.propertyUtilsBean.getNestedProperty(o[0], o[1].toString());
				node.setNodeValue(value != null ? value.toString() : "");
			}
		} else {
			String props[] = result.split("\\.");
			List<Object> propertyValues = new ArrayList<Object>();
			for (Container container: this.containers) {
				Object propertyValue = XMLUtil.getObjectProperty(container, props[0]);
				if (propertyValue != null) {
					if (propertyValue instanceof List && props.length == 1) {
						@SuppressWarnings("unchecked")
						List<Object> objects = (List<Object>) propertyValue;
						propertyValues.add(ListHandler.getUniqueResult(objects));
					} else if (propertyValue instanceof List && props.length == 2) {
						@SuppressWarnings("unchecked")
						List<Object> objects = (List<Object>) propertyValue;
						List<Object> values = new ArrayList<Object>();
						for (Object object: objects) {
							values.add(XMLUtil.propertyUtilsBean.getSimpleProperty(object, props[1]));
						}
						propertyValues.add(ListHandler.getUniqueResult(objects));
					} else {
						Object value = XMLUtil.propertyUtilsBean.getNestedProperty(container, result);
						propertyValues.add(value != null ? value.toString() : "");
					}
				}
			}
			node.setNodeValue(ListHandler.getUniqueResult(propertyValues));
		}
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
