package com.docum.view.container.unit;

import java.io.Serializable;

import com.docum.domain.TemperatureSpyStateEnum;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.Inspection;
import com.docum.service.BaseService;
import com.docum.service.FileProcessingService;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.FileListDlgView;
import com.docum.view.container.InspectionDlgView;

public class InspectionUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = -3715245363081562382L; 
	private Container container;
	private ContainerHolder containerHolder;
	private Inspection inspection; 
	private InspectionDlgView inspectionDlg; 
	private BaseService baseService;	
	private FileListDlgView inspectionImgDlg;
	private FileListDlgView inspectionScanDlg;
	private FileProcessingService fileService;
	
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
			inspection.setContainer(container);
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
	
	public Double getMinTemperature() {
		return container.getDeclaredCondition().getMinTemperature();
	}
	
	public Double getMaxTemperature() {
		return container.getDeclaredCondition().getMaxTemperature();
	}
	
	public Boolean getHasTemperatureTestDeviation() {
		return container.getActualCondition().getHasTemperatureTestDeviation();
	}
	
	public String getTemperatureSpyState(){
		TemperatureSpyStateEnum state = container.getActualCondition().getTemperatureSpyState(); 
		return state == null ? "" : state.getName();
	}
	
	public String getTemperatureSpyNumber(){
		return container.getActualCondition().getTemperatureSpyNumber();
	}
	
	public Double getTemperature() {
		return container.getActualCondition().getTemperature();
	}	
	
	public FileListDlgView getInspectionImgDlg() {
		return inspectionImgDlg;
	}
	
	public FileListDlgView getInspectionScanDlg() {
		return inspectionScanDlg;
	}
	
	public void handleImages() {
		inspectionImgDlg = new FileListDlgView( inspection.getImages(),
				"Общие фотографии по инспекции", fileService, inspection.getContainer());
		inspectionImgDlg.addHandler(this);
	}
	
	public void handleScans() {
		inspectionScanDlg = new FileListDlgView( inspection.getScans(),
				"Скан-копии документов по инспекции", fileService, 
				inspection.getContainer(),"/(\\.|\\/)(gif|jpe?g|png)$/","Добавить скан-копии");
		inspectionScanDlg.addHandler(this);
	}
	
	public int getImagesCount() {
		return inspection == null ? 0 : inspection.getImages().size();
	}
	
	public int getScansCount() {
		return inspection == null ? 0 : inspection.getScans().size();
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
		}  else {
			if (dialog instanceof FileListDlgView) {
				if (DialogActionEnum.ACCEPT.equals(action)) {
					containerHolder.saveContainer();
				}
			}
		}

	}	

}
