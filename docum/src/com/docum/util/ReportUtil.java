package com.docum.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReportUtil implements Serializable {
	private static final long serialVersionUID = -6276477440612413243L;
	public static final String DOUBLE_FORMAT = "%.3f";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	
	public String insertTableCopy(OdfTextDocument odt, String tableToFindName, 
			String tableBeforeInsertName) throws Exception {
		String result = null;
		int l = odt.getContentDom().getChildNodes().getLength();
		Node sourceNode = null;
		Node beforeInsertNode = null;
		for (int i = 0; i < l; i++) {
			Node node = odt.getContentDom().getChildNodes().item(i);
			sourceNode = findTableNode(node, tableToFindName);
			beforeInsertNode = findTableNode(node, tableBeforeInsertName);
		}
		if (sourceNode != null && beforeInsertNode != null) {
			Node newNode = sourceNode.cloneNode(true);
			l = newNode.getAttributes().getLength();
			for (int i = 0; i <l; i++) {
				Node n = newNode.getAttributes().item(i);
				if (n.getNodeName().equals("table:name")) {
					result = tableToFindName + UUID.randomUUID(); 
					n.setNodeValue(result);
				}
			}	
			beforeInsertNode.getParentNode().insertBefore(newNode, beforeInsertNode);
		}
		return result;
	}
	
	private Node findTableNode(Node node, String nodeName) {
		Node result = null;
		if (node.getNodeName().equals("table:table")) {
			int l = node.getAttributes().getLength();
			for (int i = 0; i <l; i++) {
				Node n = node.getAttributes().item(i);
				if (n.getNodeName().equals("table:name") && n.getNodeValue().equals(nodeName)) {
					return node;
				}
			}
		}
		if (result == null && node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				if (result == null) {
					result = findTableNode(nodeList.item(i), nodeName);
				} else {
					return result;
				}
			}
		}
		
		return result;
	}

	public void setRatingValue(OdfTableCell tableCell, double value1, double value2) {
		double rating = value1 - value2;
		StringBuffer stringBuffer = new StringBuffer(String.format(DOUBLE_FORMAT, rating));
		if (rating > 0) {
			stringBuffer.insert(0, "+");
		}
		tableCell.setStringValue(stringBuffer.toString());
	}
}
