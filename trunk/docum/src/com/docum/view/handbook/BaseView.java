package com.docum.view.handbook;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.docum.domain.po.IdentifiedEntity;
import com.docum.service.BaseService;

public abstract class BaseView {
	private String title;

	@ManagedProperty(value = "#{baseService}")
	private BaseService baseService;

	private List<? extends IdentifiedEntity> objects;

	public List<? extends IdentifiedEntity> getAllObjects() {
		if (this.objects == null) {
			refreshObjects();
		}
		return this.objects;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract String getSign();

	// TODO rename
	public abstract String getBase();

	abstract public IdentifiedEntity getBeanObject();

	public void refreshObjects() {
		this.objects = baseService.getAll(getBeanObject().getClass(), null);
	}

	public void saveObject() {
		if (getBeanObject().getId() != null) {
			baseService.updateObject(getBeanObject());
		} else {
			baseService.saveObject(getBeanObject());
		}
		refreshObjects();
	}

	public void deleteObject() {
		baseService.deleteObject(getBeanObject().getClass(), getBeanObject()
				.getId());
		refreshObjects();
	}

	public void newObject() {
		setTitle("Новый " + getSign().toLowerCase());
	}

	public void editObject(ActionEvent actionEvent) {
		if (getBeanObject().getId() != null) {
			setTitle("Правка: " + getBase());
		} else {
			String message = String.format(
					"%1$s для редактирование не выбран!", getSign());
			showErrorMessage(message);
		}
	}

	public void beforeDeleteObject(ActionEvent actionEvent) {
		if (getBeanObject().getId() == null) {
			String message = String.format("%1$s для удаления не выбран!",
					getSign());
			showErrorMessage(message);
		}
	}

	private void showErrorMessage(String message) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Ошибочка вышла...", message));
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public List<? extends IdentifiedEntity> getObjects() {
		return objects;
	}

	public void setObjects(List<? extends IdentifiedEntity> objects) {
		this.objects = objects;
	}

	public BaseService getBaseService() {
		return baseService;
	}
}
