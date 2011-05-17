package com.docum.view.handbook.dialog;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.docum.persistence.IdentifiedEntity;

public abstract class BaseDialog {
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	//TODO refactor
	abstract public String getSing();
	//TODO refactor
	abstract public String getBase();
	
	abstract public IdentifiedEntity getBeanObject();	 

	public void edit(ActionEvent actionEvent) {		
		if (getBeanObject() != null) {
			setTitle("Правка: " + getBase());
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ошибочка вышла...",
					getSing() + " для редактирования не выбран!"));			
		}
	}
	
}
