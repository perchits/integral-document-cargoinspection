package com.docum.view.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		List<CargoCalibreDefect> result = 
			new ArrayList<CargoCalibreDefect>(calibre.getCalibreDefects());
		Collections.sort(result, new Comparator<CargoCalibreDefect>() {
			@Override
			public int compare(CargoCalibreDefect o1,
					CargoCalibreDefect o2) {
				return new Integer(o1.getArticleCategory().getOrd()).compareTo(o2.getArticleCategory().getOrd());
			}
		});
		return result;
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
