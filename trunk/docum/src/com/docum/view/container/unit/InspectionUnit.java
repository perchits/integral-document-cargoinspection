package com.docum.view.container.unit;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Inspection;
import com.docum.service.BaseService;
import com.docum.service.FileProcessingService;
import com.docum.util.FacesUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.FileUploadUtil;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.InspectionDlgView;

public class InspectionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -3715245363081562382L;
	private static final Logger logger = Logger.getLogger(InspectionUnit.class); 
	private Container container;
	private ContainerHolder containerHolder;
	private Inspection inspection; 
	private InspectionDlgView inspectionDlg; 
	private BaseService baseService;
	private FileProcessingService fileService;

	private String stickerImage;

	public Inspection getInspection() {
		return inspection;
	}
	
	public void setContext(ContainerContext context) {		
		container = context.getContainer();
		inspection = container.getInspection();
		baseService = context.getBaseService();
		fileService = context.getFileService();
	}
	
	public String getSurveyPlace() {
		return inspection != null && inspection.getSurveyPlace() != null ? 
				inspection.getSurveyPlace().getRussianName() : "Место инспекции не указано";  
	}
	
	public String getSurveyor() {
		return inspection != null && inspection.getSurveyor() != null ? 
				inspection.getSurveyor().getName() : "";  
	}
	
	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public InspectionUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;		
	}

	public InspectionDlgView getInspectionDlg() {
		return inspectionDlg;
	}

	public void setInspectionDlg(InspectionDlgView inspectionDlg) {
		this.inspectionDlg = inspectionDlg;
	}
	
	public void doInspection(){
		if (inspection == null) {
			inspection = new Inspection();
		}
		prepareInspectionDialog(inspection);
	}
	
	private void prepareInspectionDialog(Inspection inspection) {		
		inspectionDlg = new InspectionDlgView(inspection, baseService);
		inspectionDlg.addHandler(this);		
	}
	
	public String getTitle() {
		return "Редактирование инспекции...";
				
	}
	
	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof InspectionDlgView) {
			InspectionDlgView d = (InspectionDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				Inspection inspection = d.getInspection();
				if (inspection.getId() == null) {
					inspection.setContainer(container);
					container.setInspection(inspection);
				}
				containerHolder.saveContainer();
			}
		}

	}

	public String getStickerImage() {
		return stickerImage;
	}

	public void setStickerImage(String stickerImage) {
		this.stickerImage = stickerImage;
	}
	
	public void uploadStickerImage(FileUploadEvent event) {
		stickerImage = FileUploadUtil.handleUploadedFile(fileService, container, event);
	}

}
