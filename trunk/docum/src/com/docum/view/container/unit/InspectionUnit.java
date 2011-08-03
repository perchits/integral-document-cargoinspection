package com.docum.view.container.unit;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Inspection;
import com.docum.service.BaseService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.InspectionDlgView;

public class InspectionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -3715245363081562382L;
	private Container container;
	private ContainerHolder containerHolder;
	private Inspection inspection; 
	private InspectionDlgView inspectionDlg; 
	private BaseService baseService;	

	private String stickerImage;

	public Inspection getInspection() {
		return inspection;
	}
	
	public void setContext(ContainerContext context) {		
		container = context.getContainer();
		inspection = container.getInspection();
		baseService = context.getBaseService();
	}
	
	public String getSurveyPlace() {
		return inspection != null && inspection.getSurveyPlace() != null ? 
				inspection.getSurveyPlace().getRussianName() : "Место инспекции не указано";  
	}
	
	public String getNormDocument() {
		return inspection != null && inspection.getNormativeDocument() != null ? 
				inspection.getNormativeDocument().getName() : "";  
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
	
//	public void uploadStickerImage() throws IOException {  
//        if(stickerImage == null) {
//        	return;
//        }
//		String fileName = FilenameUtils.getName(stickerImage.getName());
//        String contentType = stickerImage.getContentType();
//        byte[] bytes = stickerImage.getBytes();
//
//        // Now you can save bytes in DB (and also content type?)
//
//        FacesContext.getCurrentInstance().addMessage(null, 
//            new FacesMessage(String.format("File '%s' of type '%s' successfully uploaded!", fileName, contentType)));
//    }  	

	public void uploadStickerImage(FileUploadEvent event) {
		stickerImage = event.getFile().getFileName();
		FacesMessage msg = new FacesMessage("Succesful", stickerImage + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
