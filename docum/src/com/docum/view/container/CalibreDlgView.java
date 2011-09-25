package com.docum.view.container;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.wrapper.CalibrePresentation;

public class CalibreDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -8167987929961469160L;
	private CargoPackageCalibre calibre;
	
	public CalibreDlgView(CargoPackageCalibre calibre) {
		this.calibre = calibre;
	}
	
	public CargoPackageCalibre getCalibre() {
		return calibre;
	}
	public void setCalibre(CargoPackageCalibre calibre) {
		this.calibre = calibre;
	}
	
	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);		
	}
	
	public boolean isEdit(){
		return calibre == null || calibre.getId() == null ? false :
			calibre.getCargoPackage().getCargo().getCondition().isSurveyable();
	}
	
	public List<CargoCalibreDefect> getDefects(){
		return new CalibrePresentation(calibre).getDefects();
	}
	
	public String getTitle() {		
		String action = calibre != null ? calibre.getId() == null ? "Добавление" : "Правка" : "";
		return String.format("%1$s калибра для упаковки «%2$s»" , action ,
				calibre != null && calibre.getCargoPackage() != null ?
						calibre.getCargoPackage().toString() : "" );
	}

}
