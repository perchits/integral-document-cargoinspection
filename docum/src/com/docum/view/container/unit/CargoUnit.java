package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.FileUrl;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;
import com.docum.service.FileProcessingService;
import com.docum.util.AlgoUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.FileUploadUtil;
import com.docum.view.container.CargoDlgView;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.FileListDlgView;
import com.docum.view.wrapper.CargoPresentation;
import com.docum.view.wrapper.CargoTransformer;

public class CargoUnit implements Serializable, DialogActionHandler {
	private static final long serialVersionUID = 4121556886204075852L;
	private CargoDlgView cargoDlg;
	private Cargo cargo;
	
	private BaseService baseService;
	private ArticleService articleService;
	private ContainerHolder containerHolder;
	private CargoFeatureUnit cargoFeatureUnit;
	private CargoPackageUnit cargoPackageUnit;
	private CargoCondition cargoCondition;
	private CargoDefectUnit cargoDefectUnit;	
	private FileProcessingService fileService;
	private String removeFunctionName;	
	private FileListDlgView imageListDialog;

	public CargoUnit(ContainerHolder containerHolder) {
		this.containerHolder = containerHolder;
		cargoFeatureUnit = new CargoFeatureUnit(containerHolder);
		cargoPackageUnit = new CargoPackageUnit(containerHolder);
		cargoDefectUnit = new CargoDefectUnit(containerHolder);
	}

	public void setContext(ContainerContext context) {		
		baseService = context.getBaseService();
		articleService = context.getArticleService();
		fileService = context.getFileService();
	}
	
	public ContainerContext populateContext(){
		ContainerContext context = new ContainerContext();		
		context.setBaseService(baseService);
		return context;
	}

	public void setCargoCondition(CargoCondition cargoCondition) {
		this.cargoCondition = cargoCondition;
	}
	
	public CargoFeatureUnit getCargoFeatureUnit() {	
		return cargoFeatureUnit;
	}

	public CargoPackageUnit getCargoPackageUnit() {		
		cargoPackageUnit.setContext(populateContext());
		return cargoPackageUnit;
	}
	
	public CargoDefectUnit getCargoDefectUnit() {
		return cargoDefectUnit;
	}
	
	public void setCargo(CargoPresentation cargo) {
		this.cargo = cargo.getCargo();
		containerHolder.setDlgCargoUnit(this);
	}

	public void setEditCargo(CargoPresentation cargo) {
		setCargo(cargo);
		prepareCargoDialog(new Cargo(this.cargo));
	}

	public CargoDlgView getCargoDlg() {
		return cargoDlg;
	}

	private void prepareCargoDialog(Cargo cargo) {		
		cargoDlg = new CargoDlgView(cargo, articleService, baseService);
		cargoDlg.addHandler(this);
		containerHolder.setDlgCargoUnit(this);
	}

	public void addCargo() {
		Cargo cargo = new Cargo(cargoCondition);
		prepareCargoDialog(cargo);
	}

	public void deleteCargo() {
		cargoCondition.getCargoes().remove(cargo);
		containerHolder.saveContainer();
		cargo = null;		
	}

	public String getCargoName() {
		return new CargoPresentation(cargo).getArticle();
	}
	
	public List<CargoPresentation> getContainerCargoes() {
		if (cargoCondition != null) {
			Collection<Cargo> c = cargoCondition.getCargoes();
			List<CargoPresentation> result = new ArrayList<CargoPresentation>(
					c.size());
			AlgoUtil.transform(result, c, new CargoTransformer());
			return result;
		} else {
			return null;
		}
	}
	
	public String getRemoveFunctionName() {
		return removeFunctionName;
	}

	public void setRemoveFunctionName(String removeFunctionName) {
		this.removeFunctionName = removeFunctionName;
	}	

	public void uploadSticker(FileUploadEvent event) {		
		FileUrl sticker = new FileUrl(FileUploadUtil.handleUploadedFile(fileService, cargo
				.getCondition().getContainer(), event));
		cargo.getInspectionInfo().setSticker(sticker);
		containerHolder.saveContainer();
	}
	
	public void removeSticker() {		
		fileService.deleteImage(cargo.getInspectionInfo().getSticker().getValue());
		cargo.getInspectionInfo().setSticker(null);
		containerHolder.saveContainer();
	}	
	
	public void uploadStickerEng(FileUploadEvent event) {		
		FileUrl sticker = new FileUrl(FileUploadUtil.handleUploadedFile(fileService, cargo
				.getCondition().getContainer(), event));
		cargo.getInspectionInfo().setStickerEng(sticker);
		containerHolder.saveContainer();
	}
	
	public void removeStickerEng() {		
		fileService.deleteImage(cargo.getInspectionInfo().getStickerEng().getValue());
		cargo.getInspectionInfo().setStickerEng(null);
		containerHolder.saveContainer();
	}

	public void uploadShippingMark(FileUploadEvent event) {		
		FileUrl shippingMark = new FileUrl(FileUploadUtil.handleUploadedFile(fileService, cargo
				.getCondition().getContainer(), event));
		cargo.getInspectionInfo().setShippingMark(shippingMark);
		containerHolder.saveContainer();
	}
	
	public void removeShippingMark() {		
		fileService.deleteImage(cargo.getInspectionInfo().getShippingMark().getValue());
		cargo.getInspectionInfo().setShippingMark(null);
		containerHolder.saveContainer();
	}
	
	public void uploadShippingMarkEng(FileUploadEvent event) {		
		FileUrl shippingMark = new FileUrl(FileUploadUtil.handleUploadedFile(fileService, cargo
				.getCondition().getContainer(), event));
		cargo.getInspectionInfo().setShippingMarkEng(shippingMark);
		containerHolder.saveContainer();
	}
	
	public void removeShippingMarkEng() {		
		fileService.deleteImage(cargo.getInspectionInfo().getShippingMarkEng().getValue());
		cargo.getInspectionInfo().setShippingMarkEng(null);
		containerHolder.saveContainer();
	}
	
	public FileListDlgView getImageListDialog() {
		return imageListDialog;
	}
	
	public void handleImages(){		
		imageListDialog = new FileListDlgView(cargo.getInspectionInfo().getImages(),
				"Картинки"); 
		imageListDialog.addHandler(this);
	}
	
	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof CargoDlgView) {
			CargoDlgView d = (CargoDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				Cargo c = d.getCargo();
				if (c.getId() == null) {
					c = d.getCargo();
					cargoCondition.addCargo(c);
				} else {
					cargo.copy(d.getCargo());
				}
				containerHolder.saveContainer();				
			}
		}
	}

}
