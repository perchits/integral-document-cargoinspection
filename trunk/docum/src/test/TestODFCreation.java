package test;

import java.net.URI;

import junit.framework.TestCase;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestODFCreation extends TestCase {
	
	private static final String LOCATION = "src/test/testResources";
	
	public void testODFCreation() {
		try {
			OdfTextDocument odt = OdfTextDocument.loadDocument(LOCATION + "/testTemplate.odt");
			odt.save(LOCATION + "/testResult.odt");
			odt = OdfTextDocument.loadDocument(LOCATION + "/testResult.odt");
			odt.getPackage().insert(new URI(LOCATION + "/1.png"), "Pictures/1.png", null);
			int l = odt.getContentDom().getChildNodes().getLength();
			for (int i = 0; i < l; i++) {
				Node node = odt.getContentDom().getChildNodes().item(i);
				processNode(node);
			}
			odt.getTableByName("HeaderTable").getCellByPosition(1, 1). setStringValue("!!!!");
			odt.save(LOCATION + "/testResult.odt");
		} catch(Exception e) {
			TestCase.fail(e.getMessage());
		}
	}
	
	private void processNode(Node node) {
		if (node.getNodeValue() != null) {
			System.out.println(node.getNodeName() + " : " + node.getNodeValue());
		}
		if (node instanceof TableTableCellElement) {
			TableTableCellElement tableCellElement = (TableTableCellElement) node;
			DrawFrameElement drawFrameElement = tableCellElement.newDrawFrameElement();
			drawFrameElement.setAttribute("draw:z-index", "0");
			drawFrameElement.setAttribute("draw:style-name", "fr1");
			drawFrameElement.setAttribute("svg:height", "6.283cm");
			drawFrameElement.setAttribute("svg:width", "8.371cm");
			DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
			drawImageElement.setXlinkHrefAttribute("Pictures/1.pngs");
			drawImageElement.setXlinkActuateAttribute("onLoad");
			drawImageElement.setXlinkShowAttribute("embed");
			drawImageElement.setXlinkTypeAttribute("simple");
			//System.out.println(node.getNodeName() + " : " + node.getNodeValue());
		}
		if (node instanceof OdfDrawImage) {
			System.out.println(" " + node.getAttributes().getNamedItem("xlink:href").getNodeValue());
		}
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				processNode(nodeList.item(i));
			}
		}
	}
}
