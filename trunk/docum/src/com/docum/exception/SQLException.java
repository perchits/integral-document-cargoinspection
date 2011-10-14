package com.docum.exception;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.docum.util.DocumLogger;

public class SQLException implements Serializable{
	private static final long serialVersionUID = -1273788210122657550L;
	
	public static void showErrorMessage(String message) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Ошибочка вышла...", message));
	}
	
	public static void integrityViolation(Exception e) {
		String message = 
			"Данные, которые вы пытаетесь удалить используются "
			+ "другими справочниками или документами, "
			+ "их удаление невозможно. Внимание, последние изменения " 
			+ "не были сохранены!";
		processException(e,message);
	}
	
	public static void commonException(Exception e) {
		String message = 
			"При сохранении произошла необрабатываемая ошибка! " +
			"Внимание, последние изменения не были сохранены!";			
		processException(e,message);
	}
	
	public static void processException(Exception e, String message){
		showErrorMessage(message);
		DocumLogger.log(e);
	}
	
	
}
