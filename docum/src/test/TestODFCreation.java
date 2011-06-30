package test;

import junit.framework.TestCase;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawImage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestODFCreation extends TestCase {
	
	private static final String LOCATION = "src/test/testResources";
	
	public void testODFCreation() {
		try {
			OdfTextDocument odt = OdfTextDocument.loadDocument(LOCATION + "/testTemplate.odt");
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
		System.out.println(node.getNodeName() + " : " + node.getNodeValue());
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
