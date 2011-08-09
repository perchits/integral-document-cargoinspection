package com.docum.view;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.docum.domain.po.common.Container;
import com.docum.service.FileProcessingService;
import com.docum.util.FacesUtil;
import com.docum.view.container.unit.InspectionUnit;

public class FileUploadUtil {
	private static final Logger logger = Logger.getLogger(InspectionUnit.class);

	public static String handleUploadedFile(FileProcessingService svc, Container container,
			FileUploadEvent event) {
		String path = makePath(container);
		String fileName = event.getFile().getFileName();
		String result = null;
		try {
			result = svc.saveImage(path, fileName, event.getFile().getInputstream());
		} catch (IOException e) {
			logger.error("Can't upload file", e);
			FacesUtil.error("Ошибка загрузки", fileName + " сохранить не удалось.");
			return null;
		}
		FacesUtil.message("Загрузка файла", fileName + " сохранен.");
		return path + "/" + result;
	}
	
	public static String makePath(Container container) {
		return String.format("%08d", container.getId()) + "-" + container.getNumber();
	}
}
