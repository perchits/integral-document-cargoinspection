package test;

import junit.framework.TestCase;

import org.odftoolkit.odfdom.doc.OdfTextDocument;

public class TestODFCreation extends TestCase {
	
	private static final String LOCATION = "src/test/testResources";
	
	public void testODFCreation() {
		try {
			OdfTextDocument odt = OdfTextDocument.loadDocument(LOCATION + "/testTemplate.odt");
			odt.addText("This is my very first ODF test");
			odt.getTableByName("HeaderTable").getCellByPosition(1, 1).setStringValue("!!!!");
			odt.save(LOCATION + "/testResult.odt");
		} catch(Exception e) {
			TestCase.fail(e.getMessage());
		}
	}
}
