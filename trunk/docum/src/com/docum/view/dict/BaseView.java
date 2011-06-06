package com.docum.view.dict;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;

import com.docum.domain.SortOrderEnum;
import com.docum.domain.po.IdentifiedEntity;
import com.docum.service.BaseService;

public abstract class BaseView implements Serializable{
	private static final long serialVersionUID = -1072752910650707550L;
	protected static Map<String, SortOrderEnum> DEAFULT_SORT_FIELDS = new HashMap<String, SortOrderEnum>();
	
	static {
		DEAFULT_SORT_FIELDS.put("id", SortOrderEnum.ASC);
	}

	private String title;

	@Autowired
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
		HashMap<String, SortOrderEnum> sortFields = new HashMap<String, SortOrderEnum>();
		sortFields.put("id", SortOrderEnum.ASC);
		this.objects = baseService.getAll(getBeanObject().getClass(), sortFields);
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
		baseService.deleteObject(getBeanObject().getClass(), getBeanObject().getId());
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

	public List<? extends IdentifiedEntity> getObjects() {
		return objects;
	}

	public void setObjects(List<? extends IdentifiedEntity> objects) {
		this.objects = objects;
	}

	protected BaseService getBaseService() {
		return baseService;
	}
}
