package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoArticleFeature;
import com.docum.domain.po.common.CargoCondition;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.domain.po.common.CargoPackage;
import com.docum.util.AlgoUtil;

public class CargoPresentation implements Serializable {
	private static final long serialVersionUID = -3161032049705097594L;
	private Cargo cargo;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoPresentation(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoCondition getActiveCargoCondition() {
		return cargo.getCondition();
	}

	public String getArticle() {
		return cargo != null && cargo.getArticle() != null ? cargo.getArticle()
				.getName() : null;
	}

	public String getArticleCategory() {
		return cargo != null && cargo.getArticleCategory() != null ? cargo
				.getArticleCategory().getName() : null;
	}

	public String getSupplier() {
		return cargo != null && cargo.getSupplier() != null ? cargo
				.getSupplier().getCompany().getName() : null;
	}

	public List<CargoArticleFeature> getFeatures() {
		if (cargo == null) {
			return null;
		}
		List<CargoArticleFeature> result = new ArrayList<CargoArticleFeature>(
				cargo.getFeatures());
		Collections.sort(result, new Comparator<CargoArticleFeature>() {
			@Override
			public int compare(CargoArticleFeature o1, CargoArticleFeature o2) {
				return o1.getFeature().getName()
						.compareTo(o2.getFeature().getName());
			}
		});
		return result;
	}
	
	public Boolean getActual(){
		return cargo.getCondition().isSurveyable();
	}

	public List<CargoDefectGroupPresentation> getDefectGroups() {
		if (cargo == null) {
			return null;
		}
		Collection<CargoDefectGroup> cd = cargo.getInspectionInfo().getDefectGroups();
		List<CargoDefectGroupPresentation> result = new ArrayList<CargoDefectGroupPresentation>(
				cd.size());
		AlgoUtil.transform(result, cd, new CargoDefectGroupTransformer());
		return result;
	}
	
	public String getSticker(){
		if (cargo == null || cargo.getInspectionInfo() == null) {
			return null;
		}
		return cargo.getInspectionInfo().getSticker() != null ?
				cargo.getInspectionInfo().getSticker().getValue() : null;
	}
	
	public String getStickerEng(){
		if (cargo == null || cargo.getInspectionInfo() == null) {
			return null;
		}		
		return cargo.getInspectionInfo().getStickerEng() != null ?
				cargo.getInspectionInfo().getStickerEng().getValue() : null;
	}
	
	public String getShippingMark(){
		if (cargo == null || cargo.getInspectionInfo() == null) {
			return null;
		}
		return cargo.getInspectionInfo().getShippingMark() != null ?
				cargo.getInspectionInfo().getShippingMark().getValue() : null;
	}
	
	public String getShippingMarkEng(){
		if (cargo == null || cargo.getInspectionInfo() == null) {
			return null;
		}
		return cargo.getInspectionInfo().getShippingMarkEng() != null ?
				cargo.getInspectionInfo().getShippingMarkEng().getValue() : null;
	}
	
	public String getNormativeDoc(){
		return cargo != null && cargo.getInspectionInfo() != null
		&& cargo.getInspectionInfo().getNormativeDocument() != null ?
				cargo.getInspectionInfo().getNormativeDocument().getName(): null;
	}

	public List<CargoPackagePresentation> getPackages() {
		if (cargo == null) {
			return null;
		}

		Collection<CargoPackage> cp = cargo.getCargoPackages();

		List<CargoPackagePresentation> result = new ArrayList<CargoPackagePresentation>(
				cp.size());
		AlgoUtil.transform(result, cp, new CargoPackageTransformer());

		Collections.sort(result, new Comparator<CargoPackagePresentation>() {
			@Override
			public int compare(CargoPackagePresentation o1,
					CargoPackagePresentation o2) {
				return o1.getMeasureName().compareTo(o2.getMeasureName());
			}
		});
		return result;
	}
}
