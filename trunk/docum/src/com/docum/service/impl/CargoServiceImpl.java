package com.docum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.dao.CargoDao;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoCalibreDefect;
import com.docum.domain.po.common.CargoInspectionInfo;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.CargoPackageCalibre;
import com.docum.service.CargoService;
import com.docum.util.AlgoUtil;
import com.docum.util.cargo.CargoUtil;

@Service(CargoService.SERVICE_NAME)
@Transactional
public class CargoServiceImpl extends BaseServiceImpl implements CargoService {
	private static final long serialVersionUID = 1716750300940533007L;

	@Autowired
	public CargoDao cargoDao;

	@Override
	public CargoInspectionInfo getCargoInspectionInfo(Long cargoId) {
		return cargoDao.getCargoInspectionInfo(cargoId);
	}
	
	@Override
	public CargoInspectionInfo saveCargoInspectionInfo(CargoInspectionInfo cargoInspectionInfo) {
		return super.save(cargoInspectionInfo);
	}

	@Override
	public void processCategoryDefects(Cargo cargo) {
		if(!cargo.getCondition().isSurveyable()) {
			return;
		}
		//Добавляем недостающие дефекты
		for(CargoPackage cp : cargo.getCargoPackages()) {
			for(CargoPackageCalibre calibre : cp.getCalibres()) {
				for(ArticleCategory category : CargoUtil.getCargoCategoriesForDefects(cargo)) {
					if(!findDefect(calibre, category)) {
						CargoCalibreDefect defect = new CargoCalibreDefect(category, calibre);
						calibre.getCalibreDefects().add(defect);
					}
				}
			}
		}
	}
	
	private boolean findDefect(final CargoPackageCalibre calibre, final ArticleCategory category) {
		return AlgoUtil.find(calibre.getCalibreDefects(),
				new AlgoUtil.FindPredicate<CargoCalibreDefect>() {
			@Override
			public boolean isIt(CargoCalibreDefect defect) {
				return defect.getArticleCategory().equals(category) &&
					defect.getCalibre().equals(calibre);
			}
		}) != null;
	}
}
