package com.docum.util;

import java.io.Serializable;
import java.util.UUID;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReportUtil implements Serializable {
	private static final long serialVersionUID = -6276477440612413243L;
	
	private Node tempNode;
	
	public String insertTableCopy(OdfTextDocument odt, String tableName) throws Exception {
		String result = null;
		int l = odt.getContentDom().getChildNodes().getLength();
		Node sourceNode = null;
		for (int i = 0; i < l; i++) {
			Node node = odt.getContentDom().getChildNodes().item(i);
			sourceNode = findTableNode(node, tableName);
		}
		if (this.tempNode != null) {
			l = this.tempNode.getAttributes().getLength();
			for (int i = 0; i <l; i++) {
				Node n = this.tempNode.getAttributes().item(i);
				if (n.getNodeName().equals("table:name")) {
					result = tableName + UUID.randomUUID(); 
					n.setNodeValue(result);
				}
			}	
			sourceNode.getParentNode().insertBefore(this.tempNode, sourceNode);
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
					this.tempNode = node.cloneNode(true);
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
		StringBuffer stringBuffer = new StringBuffer(String.valueOf(rating));
		if (rating > 0) {
			stringBuffer.insert(0, "+");
		}
		tableCell.setStringValue(stringBuffer.toString());
	}
}
