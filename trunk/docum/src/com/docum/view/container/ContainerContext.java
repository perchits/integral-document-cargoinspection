package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.Container;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;
import com.docum.service.CargoService;
import com.docum.service.FileProcessingService;

public class ContainerContext implements Serializable {
	private static final long serialVersionUID = -8618919955694844921L;

	private BaseService baseService;
	private ArticleService articleService;
	private CargoService cargoService;
	private FileProcessingService fileService;
	private Container container;

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public CargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public FileProcessingService getFileService() {
		return fileService;
	}

	public void setFileService(FileProcessingService fileService) {
		this.fileService = fileService;
	}

}
