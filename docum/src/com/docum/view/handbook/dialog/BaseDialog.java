package com.docum.view.handbook.dialog;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.docum.persistence.IdentifiedEntity;
import com.docum.service.BaseService;

public abstract class BaseDialog {
	private String title;
	
	@ManagedProperty(value = "#{baseService}")
	private BaseService baseService;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	//TODO rename
	public abstract String getSing();
	//TODO rename
	public abstract String getBase();
	
	abstract public IdentifiedEntity getBeanObject();
	
	public void refreshObjects() {};
	
	public void saveObject() {
		if (getBeanObject() != null) {
			baseService.updateObject(getBeanObject());
		} else {
			baseService.saveObject(getBeanObject());
		}
		refreshObjects();
	}

	public void deleteObject() {
		baseService.deleteObject(getBeanObject().getClass(), getBeanObject().getId());
		refreshObjects();
	}
	
	public void newObject() {
		setTitle("Новый " + getSing().toLowerCase());
	}
	
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

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
}
