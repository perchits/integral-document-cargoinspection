package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.List;

import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoPackageCalibre;

public class CalibrePresentation implements Serializable{
	private static final long serialVersionUID = 1212483539063009292L;
	private CargoPackageCalibre calibre;	

	public CalibrePresentation(CargoPackageCalibre cargoPackageCalibre) {
		this.calibre = cargoPackageCalibre;
	}

	public CargoPackageCalibre getCalibre() {
		return calibre;
	}
	
	public List<CargoCalibreDefect> getDefects(){		
		return calibre.getCalibreDefects();
	}

	public void setCalibre(CargoPackageCalibre cargoPackageCalibre) {
		this.calibre = cargoPackageCalibre;
	}
	
	public String getCalibreValue() {
		return toString();
	}	
	
	public double getPackageCount() {
		return calibre != null ? calibre.getPackageCount() : null;
	}
	
	public String toString() {		
			return calibre != null ? calibre.getCalibreValue() : null;		
	}
	
}
