package com.docum.util;

import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void putFlashParam(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);		
	}
	public static Object getFlashParam(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
	}
}
