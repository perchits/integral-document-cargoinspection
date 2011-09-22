package com.docum.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
		processCategoryDefects(cargoInspectionInfo);
		return super.save(cargoInspectionInfo);
	}

	@Override
	public void processCategoryDefects(CargoInspectionInfo cargoInspectionInfo) {
		Cargo cargo = cargoInspectionInfo.getCargo();
		Set<CargoCalibreDefect> defects = cargoInspectionInfo.getCalibreDefects();
		Set<CargoPackageCalibre> calibreCache = new HashSet<CargoPackageCalibre>();
		//Добавляем недостающие дефекты
		for(CargoPackage cp : cargo.getCargoPackages()) {
			for(CargoPackageCalibre calibre : cp.getCalibres()) {
				calibreCache.add(calibre);
				for(ArticleCategory category : cargo.getArticle().getCategories()) {
					if(!findDefect(defects, calibre, category)) {
						CargoCalibreDefect defect = new CargoCalibreDefect(cargoInspectionInfo,
								category, calibre);
						defects.add(defect);
					}
				}
			}
		}
		//Удаляем лишние дефекты
		for(Iterator<CargoCalibreDefect> it = defects.iterator(); it.hasNext();) {
			if(!calibreCache.contains(it.next().getCalibre())) {
				it.remove();
			}
		}
		//super.save(defects);
	}
	
	private boolean findDefect(final Set<CargoCalibreDefect> defects,
			final CargoPackageCalibre calibre, final ArticleCategory category) {
		return AlgoUtil.find(defects, new AlgoUtil.FindPredicate<CargoCalibreDefect>() {
			@Override
			public boolean isIt(CargoCalibreDefect defect) {
				return defect.getArticleCategory().equals(category) &&
					defect.getCalibre().equals(calibre);
			}
		}) != null;
	}
}
