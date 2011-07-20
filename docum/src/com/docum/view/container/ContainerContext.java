package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Container;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;

public class ContainerContext implements Serializable {
	private static final long serialVersionUID = -8618919955694844921L;
	private Container container;
	private Cargo cargo;
	private CargoPackage cargoPackage;
	private BaseService baseService;
	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoPackage getCargoPackage() {
		return cargoPackage;
	}

	public void setCargoPackage(CargoPackage cargoPackage) {
		this.cargoPackage = cargoPackage;
	}
}
