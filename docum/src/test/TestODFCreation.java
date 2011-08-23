package test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Report;
import com.docum.service.BaseService;
import com.docum.service.ReportingService;
import com.docum.util.XMLUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/docum-context.xml")
@Transactional
public class TestODFCreation extends TestCase {
	private static final String LOCATION = "src/test/testResources";
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	
	@Autowired
	BaseService baseService;
	@Autowired
	ReportingService reportingService;
	Container container;
	
	@Test
	public void testODFCreation() {
		try {
			Report report = baseService.getObject(Report.class, 1L);
			if (report != null) {
				reportingService.createReport(report);
			} else {
				TestCase.fail();
			}
			/*OdfTextDocument odt = OdfTextDocument.loadDocument(LOCATION + "/testTemplate.odt");
			odt.save(LOCATION + "/testResult.odt");
			odt = OdfTextDocument.loadDocument(LOCATION + "/testResult.odt");
			container = baseService.getObject(Container.class, 3L);
			int l = odt.getContentDom().getChildNodes().getLength();
			for (int i = 0; i < l; i++) {
				Node node = odt.getContentDom().getChildNodes().item(i);
				processNode(node);
			}
			addMarksImages(odt);
			odt.save(LOCATION + "/testResult.odt");
			OpenOfficeConnection officeConnection = new SocketOpenOfficeConnection(8100);
			officeConnection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(officeConnection);
			converter.convert(
				new File("e:/Work/NCSP/Development/docum/project/src/test/testResources/testResult.odt"), 
				new File("e:/Work/NCSP/Development/docum/project/src/test/testResources/testResult.pdf"));
			officeConnection.disconnect();*/
		} catch(Exception e) {
			TestCase.fail(e.getMessage());
		}
	}
	
	private void addMarksImages(OdfTextDocument odt) throws Exception {
		odt.getPackage().insert(
			new URI("file:///e:/Work/NCSP/Development/docum/project/temp/00000006-ZCSU5846814/smark_eng(0).jpg"), 
			"Pictures/1.png", null);
		OdfTableCell odfTableCell = odt.getTableByName("TableShippingMark").getCellByPosition(1, 2);
		TableTableCellElement tableCellElement = (TableTableCellElement) odfTableCell.getOdfElement();
		TextPElement textPElement = tableCellElement.newTextPElement();
		DrawFrameElement drawFrameElement = textPElement.newDrawFrameElement();
		drawFrameElement.setDrawZIndexAttribute(0);
		drawFrameElement.setDrawStyleNameAttribute("fr1");
		drawFrameElement.setSvgHeightAttribute("6.283cm");
		drawFrameElement.setSvgWidthAttribute("8.371cm");
		DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
		drawImageElement.setXlinkHrefAttribute("Pictures/11.png");
		drawImageElement.setXlinkActuateAttribute("onLoad");
		drawImageElement.setXlinkShowAttribute("embed");
		drawImageElement.setXlinkTypeAttribute("simple");
	}
	
	private void processNode(Node node) throws Exception {
		if (node.getNodeValue() != null) {
			int statementBeginPos = node.getNodeValue().indexOf(STATEMENT_BEGIN);
			if (statementBeginPos != -1) {
				System.out.println(node.getNodeName() + " : " + node.getNodeValue());
				replaceNodeValue(node, node.getNodeValue(), container, statementBeginPos);
			}
		}
		/*if (node instanceof TableTableCellElement) {
			TableTableCellElement tableCellElement = (TableTableCellElement) node;
			TextPElement textPElement = tableCellElement.newTextPElement();
			DrawFrameElement drawFrameElement = textPElement.newDrawFrameElement();
			drawFrameElement.setDrawZIndexAttribute(0);
			drawFrameElement.setDrawStyleNameAttribute("fr1");
			drawFrameElement.setSvgHeightAttribute("6.283cm");
			drawFrameElement.setSvgWidthAttribute("8.371cm");
			DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
			drawImageElement.setXlinkHrefAttribute("Pictures/1.png");
			drawImageElement.setXlinkActuateAttribute("onLoad");
			drawImageElement.setXlinkShowAttribute("embed");
			drawImageElement.setXlinkTypeAttribute("simple");
			//System.out.println(node.getNodeName() + " : " + node.getNodeValue());
		}
		if (node instanceof OdfDrawImage) {
			System.out.println(" " + node.getAttributes().getNamedItem("xlink:href").getNodeValue());
		}*/
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				processNode(nodeList.item(i));
			}
		}
	}
	
	private void replaceNodeValue(Node node, String processedValue, 
			Container container, int statementBeginPos) throws Exception {
		String result = processedValue.substring(statementBeginPos + STATEMENT_BEGIN.length(), 
			processedValue.indexOf(STATEMENT_END));
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
}

