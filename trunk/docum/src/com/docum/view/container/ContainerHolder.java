package com.docum.view.container;

import com.docum.view.container.unit.CalibreUnit;
import com.docum.view.container.unit.CargoDefectUnit;
import com.docum.view.container.unit.CargoFeatureUnit;
import com.docum.view.container.unit.CargoPackageUnit;
import com.docum.view.container.unit.CargoUnit;
import com.docum.view.container.unit.OptionUnit;

public interface ContainerHolder {
	public void saveContainer();
	public void setDlgCargoUnit(CargoUnit dlgCargoUnit);
	public void setDlgFeatureUnit(CargoFeatureUnit dlgFeatureUnit);
	public void setDlgPackageUnit(CargoPackageUnit dlgPackageUnit);
	public void setDlgCalibreUnit(CalibreUnit dlgCalibreUnit);
	public void setDlgDefectUnit(CargoDefectUnit dlgDefectUnit);
	public void setDlgOptionUnit(OptionUnit optionUnit);
	
	public void addContainerChangeListener(ContainerChangeListener listener);
	public void removeContainerChangeListener(ContainerChangeListener listener);
}
