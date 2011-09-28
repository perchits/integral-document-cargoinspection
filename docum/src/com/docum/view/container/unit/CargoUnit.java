package com.docum.view.container.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.CargoInspectionInfo;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.FileUrl;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.service.ArticleService;
import com.docum.service.BaseService;
import com.docum.service.FileProcessingService;
import com.docum.util.AlgoUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.DialogActionHandler;
import com.docum.view.FileUploadUtil;
import com.docum.view.container.CargoDlgView;
import com.docum.view.container.ContainerChangeListener;
import com.docum.view.container.ContainerContext;
import com.docum.view.container.ContainerHolder;
import com.docum.view.container.FileListDlgView;
import com.docum.view.wrapper.CargoPresentation;
import com.docum.view.wrapper.CargoTransformer;

public class CargoUnit implements Serializable, DialogActionHandler, ContainerChangeListener {
	private static final long serialVersionUID = 4121556886204075852L;

	private CargoDlgView cargoDlg;
	private CargoPresentation cargoPresentation;

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
		containerHolder.addContainerChangeListener(this);
	}

	public void setContext(ContainerContext context) {
		baseService = context.getBaseService();
		articleService = context.getArticleService();
		fileService = context.getFileService();
	}

	public ContainerContext populateContext() {
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
		this.cargoPresentation = cargo;
		containerHolder.setDlgCargoUnit(this);
	}
	
	public CargoPresentation getCargo() {
		return cargoPresentation;
	}

	public void setEditCargo(CargoPresentation cargo) {
		setCargo(cargo);
		prepareCargoDialog(new Cargo(this.cargoPresentation.getCargo()));
	}

	public CargoDlgView getCargoDlg() {
		return cargoDlg;
	}

	private void prepareCargoDialog(Cargo cargo) {
		NormativeDocument normativeDocument = null;
		boolean hasNormativeDocument = cargo.getCondition().isSurveyable();
		if(this.cargoPresentation != null) {
			normativeDocument = hasNormativeDocument && this.cargoPresentation.getInspectionInfo() != null ?
					this.cargoPresentation.getInspectionInfo().getNormativeDocument() : null;
		}
		cargoDlg = new CargoDlgView(cargo, hasNormativeDocument, normativeDocument,
				articleService, baseService);
		cargoDlg.addHandler(this);
		containerHolder.setDlgCargoUnit(this);
	}

	public void addCargo() {
		Cargo cargo = new Cargo(cargoCondition);
		prepareCargoDialog(cargo);
	}
	
	public void deleteCargo() {
		cargoCondition.getCargoes().remove(cargoPresentation.getCargo());
		saveContainer();
		cargoPresentation = null;
	}

	public String getCargoName() {
		return cargoPresentation.getArticle();
	}

	public List<CargoPresentation> getContainerCargoes() {
		if (cargoCondition != null) {
			Collection<Cargo> c = cargoCondition.getCargoes();
			List<CargoPresentation> result = new ArrayList<CargoPresentation>(c.size());
			AlgoUtil.transform(result, c, new CargoTransformer());
			Collections.sort(result, new Comparator<CargoPresentation>() {
				@Override
				public int compare(CargoPresentation o1, CargoPresentation o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
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

	private void saveContainer() {
		containerHolder.saveContainer();
	}

	public void uploadSticker(FileUploadEvent event) {
		FileUrl sticker = new FileUrl(FileUploadUtil.handleUploadedFile(fileService,
				cargoPresentation.getCargo().getCondition().getContainer(), event));
		cargoPresentation.getInspectionInfo().setSticker(sticker);
		saveContainer();
	}

	public void removeSticker() {
		fileService.deleteImage(cargoPresentation.getInspectionInfo().getSticker().getValue());
		cargoPresentation.getInspectionInfo().setSticker(null);
		saveContainer();
	}

	public void uploadStickerEng(FileUploadEvent event) {
		FileUrl sticker = new FileUrl(FileUploadUtil.handleUploadedFile(fileService,
				cargoPresentation.getCargo().getCondition().getContainer(), event));
		cargoPresentation.getInspectionInfo().setStickerEng(sticker);
		saveContainer();
	}

	public void removeStickerEng() {
		fileService.deleteImage(cargoPresentation.getInspectionInfo().getStickerEng().getValue());
		cargoPresentation.getInspectionInfo().setStickerEng(null);
		saveContainer();
	}

	public void uploadShippingMark(FileUploadEvent event) {
		FileUrl shippingMark = new FileUrl(FileUploadUtil.handleUploadedFile(fileService,
				cargoPresentation.getCargo().getCondition().getContainer(), event));
		cargoPresentation.getInspectionInfo().setShippingMark(shippingMark);
		saveContainer();
	}

	public void removeShippingMark() {
		fileService.deleteImage(cargoPresentation.getInspectionInfo().getShippingMark().getValue());
		cargoPresentation.getInspectionInfo().setShippingMark(null);
		saveContainer();
	}

	public void uploadShippingMarkEng(FileUploadEvent event) {
		FileUrl shippingMark = new FileUrl(FileUploadUtil.handleUploadedFile(fileService,
				cargoPresentation.getCargo().getCondition().getContainer(), event));
		cargoPresentation.getInspectionInfo().setShippingMarkEng(shippingMark);
		saveContainer();
	}

	public void removeShippingMarkEng() {
		fileService.deleteImage(cargoPresentation.getInspectionInfo().getShippingMarkEng().getValue());
		cargoPresentation.getInspectionInfo().setShippingMarkEng(null);
		saveContainer();
	}

	public FileListDlgView getImageListDialog() {
		return imageListDialog;
	}

	public void handleImages() {
		imageListDialog = new FileListDlgView(cargoPresentation.getInspectionInfo().getImages(),
				"Фотографии по грузу", fileService, cargoPresentation.getCargo().getCondition().getContainer());
		imageListDialog.addHandler(this);
	}
	
	@Override
	public void handleAction(AbstractDlgView dialog, DialogActionEnum action) {
		if (dialog instanceof CargoDlgView) {
			CargoDlgView d = (CargoDlgView) dialog;
			if (DialogActionEnum.ACCEPT.equals(action)) {
				Cargo c = d.getCargo();
				if (c.getId() == null) {
					cargoCondition.addCargo(c);
					cargoPresentation = new CargoPresentation(c);
				} else {
					cargoPresentation.getCargo().copy(d.getCargo());
				}
				
				CargoInspectionInfo inspectionInfo = cargoPresentation.getInspectionInfo();
				if(cargoPresentation.getCargo().getCondition().isSurveyable() && inspectionInfo != null) {
					inspectionInfo.setNormativeDocument(d.getNormativeDocument());
				}
				saveContainer();
			}
		} else {
			if (dialog instanceof FileListDlgView) {
				if (DialogActionEnum.ACCEPT.equals(action)) {
					saveContainer();
				}
			}
		}
	}

	@Override
	public void containerChanged(Container container) {
		if(cargoPresentation == null) {
			return;
		}
		Cargo cargo = cargoPresentation.getCargo();
		for(Cargo c : container.getDeclaredCondition().getCargoes()) {
			if(c.equals(cargo)) {
				cargoPresentation.setCargo(c);
				return;
			}
		}
		for(Cargo c : container.getActualCondition().getCargoes()) {
			if(c.equals(cargo)) {
				cargoPresentation.setCargo(c);
				return;
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargoPresentation == null) ? 0 : cargoPresentation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CargoUnit other = (CargoUnit) obj;
		if (cargoPresentation == null) {
			if (other.cargoPresentation != null)
				return false;
		} else if (!cargoPresentation.equals(other.cargoPresentation))
			return false;
		return true;
	}

}
