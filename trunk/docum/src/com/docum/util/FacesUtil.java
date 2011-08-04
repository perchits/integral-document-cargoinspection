package com.docum.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void putFlashParam(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);		
	}
	public static Object getFlashParam(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
	}
	
	public static void message(FacesMessage.Severity severity, String summary, String message) {
		FacesMessage msg = new FacesMessage(severity, summary, message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void message(String summary, String message) {
		message(FacesMessage.SEVERITY_INFO, summary, message);
	}

	public static void message(String summary) {
		message(summary, null);
	}
	
	public static void warn(String summary, String message) {
		message(FacesMessage.SEVERITY_WARN, summary, message);
	}

	public static void error(String summary, String message) {
		message(FacesMessage.SEVERITY_ERROR, summary, message);
	}
	
	public static void fatal(String summary, String message) {
		message(FacesMessage.SEVERITY_FATAL, summary, message);
	}
}
