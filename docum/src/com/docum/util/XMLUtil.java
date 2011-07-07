package com.docum.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.w3c.dom.Node;

public class XMLUtil {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public static final PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
	
	public static void fillObjectByDataFromXmlAttributes(Object object, Node nodeWithAttributes) throws Exception {
		int length = nodeWithAttributes.getAttributes().getLength();
		try {
			for (int i = 0; i < length; i++) {
				Node childNode = nodeWithAttributes.getAttributes().item(i);
				Class<?> propType = propertyUtilsBean.getPropertyType(object, childNode.getNodeName());
				if (propType == null) {
					continue;
				}
				if (propType.toString().indexOf(Boolean.class.getName()) > 0) {
					propertyUtilsBean.setSimpleProperty(object, childNode.getNodeName(), 
							Boolean.valueOf(childNode.getNodeValue().trim()));
				} else if (propType.toString().indexOf(Date.class.getName()) > 0) {
					propertyUtilsBean.setSimpleProperty(object, childNode.getNodeName(), 
							dateFormat.parse(childNode.getNodeValue().trim()));
				} else if (propType.toString().indexOf(Double.class.getName()) > 0) {
					NumberFormat nf;
					if (childNode.getNodeValue().indexOf(",") != -1) {
						nf = NumberFormat.getInstance(Locale.FRENCH);
					}
					else {
						nf = NumberFormat.getInstance(Locale.US);
					}
					propertyUtilsBean.setSimpleProperty(object, childNode.getNodeName(),
							nf.parse(childNode.getNodeValue()).doubleValue());
				} else if (propType.toString().indexOf(Integer.class.getName()) > 0) {
					propertyUtilsBean.setSimpleProperty(object, childNode.getNodeName(),
							Integer.valueOf(childNode.getNodeValue()));
				} else {
					propertyUtilsBean.setSimpleProperty(object, childNode.getNodeName(), 
							childNode.getNodeValue().trim());
				}
			}
		} catch (Exception expt) {
			StringBuffer sb = new StringBuffer();
			sb.append("Исключение при обработке XML файла").append("\n");
			sb.append(expt.toString()).append("\n");
			for (int i = 0; i < length; i++) {
				Node childNode = nodeWithAttributes.getAttributes().item(i);
				sb.append(childNode.getNodeName()).append(" : ").append(childNode.getNodeValue()).append("\n");
			}
			DocumLogger.log(new Exception(sb.toString()));
		}
	}
	
	public static Object getObjectProperty(Object object, String propertyName) {
		Object result = null;
		try {
			result = propertyUtilsBean.getSimpleProperty(object, propertyName);
		} catch (Exception e) {
			return null;
		}
		return result;
	}
}
