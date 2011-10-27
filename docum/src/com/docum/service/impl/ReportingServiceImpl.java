package com.docum.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.NestedNullException;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.docum.dao.ReportingDao;
import com.docum.domain.Stats.CargoCalibreDefects;
import com.docum.domain.Stats.CargoDefects;
import com.docum.domain.TemperatureSpyStateEnum;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleInspectionOption;
import com.docum.domain.po.common.Cargo;
import com.docum.domain.po.common.CargoDefect;
import com.docum.domain.po.common.CargoDefectGroup;
import com.docum.domain.po.common.CargoInspectionInfo;
import com.docum.domain.po.common.CargoInspectionOption;
import com.docum.domain.po.common.CargoPackage;
import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.FileUrl;
import com.docum.domain.po.common.Inspection;
import com.docum.domain.po.common.NormativeDocument;
import com.docum.domain.po.common.Report;
import com.docum.service.ApplicationConfigService;
import com.docum.service.BaseService;
import com.docum.service.CargoService;
import com.docum.service.ReportingService;
import com.docum.service.StatsService;
import com.docum.util.AlgoUtil;
import com.docum.util.DocumLogger;
import com.docum.util.ListHandler;
import com.docum.util.ReportUtil;
import com.docum.util.XMLUtil;
import com.docum.util.cargo.AverageCargoPackageWeights;
import com.docum.util.cargo.CargoUtil;
import com.docum.view.wrapper.ContainerPresentation;

@Service(ReportingService.SERVICE_NAME)
@Transactional
public class ReportingServiceImpl implements Serializable, ReportingService {
	private static final long serialVersionUID = -4974869292960516986L;
	
	@Autowired
	ReportingDao reportingDao;
	@Autowired
	BaseService baseService;
	@Autowired
	CargoService cargoService;
	@Autowired
	ApplicationConfigService applicationConfigService;
	@Autowired
	StatsService statsService;
	
	private static final String STATEMENT_BEGIN = "{$";
	private static final String STATEMENT_END = "}";
	private int starOfficeConnectionPort;
	private List<Container> containers;
	private Map<Container, ContainerPresentation> containerPresentationMap;
	private Map<String, Object[]> temlateAccordance = new HashMap<String, Object[]>();
	private ReportUtil reportUtil = new ReportUtil();
	private static final String TABLE_BRIX_SCALE = "TableBrixScale"; 
	private static final String TABLE_RIPENESS = "TableRipeness";

	@Override
	public void createReport(Report report) throws Exception {
		if (report == null || report.getId() == null) {
			throw new Exception("Id отчета не может быть пустым при создании файла");
		}
		containers = new ArrayList<Container>();
		containerPresentationMap = new HashMap<Container, ContainerPresentation>();
		for (Container container: report.getContainers()) {
			Container c = this.baseService.getObject(Container.class, container.getId());
			this.containers.add(c);
			containerPresentationMap.put(c, new ContainerPresentation(c));
		}
		initTemlateAccordance(report);
		String reportFileName = REPORT_FILENAME_PREFIX + report.getId();
		String location = FacesContext.getCurrentInstance().getExternalContext()
			.getRealPath("/") +	REPORTS_LOCATION; 
		OdfTextDocument odt = OdfTextDocument.loadDocument(location + REPORT_TEMPLATE_FILENAME);
		int l = odt.getContentDom().getChildNodes().getLength();
		for (int i = 0; i < l; i++) {
			Node node = odt.getContentDom().getChildNodes().item(i);
			processNode(node);
		}
		l = odt.getStylesDom().getChildNodes().getLength();
		for (int i = 0; i < l; i++) {
			Node node = odt.getStylesDom().getChildNodes().item(i);
			processNode(node);
		}
		addImages(odt, "TableShippingMark", 
				"Shipping mark in English is unavailable / Маркировка производителя на английском языке отсутствует", 
				"Shipping mark in Russian is unavailable / Маркировка производителя на русском языке отсутствует", 
			"shippingMarkEng", "shippingMark");
		addImages(odt, "TableStickerA4", 
				"Sticker in English is unavailable / Стикер на английском языке отсутствует", 
				"Sticker in Russian is unavailable / Стикер на русском языке отсутствует", 
			"stickerEng", "sticker");
		addTemperatureData(odt, "TableMeasurementTemperature");
		addNormativePaper(odt, "ТаблицаNormativePaper");
		addCargoAmount(odt, "TableCargoAmount");
		addGeneralCargoImages(odt, "TablePictures");
		odt.save(location + reportFileName + ".odt");
		OpenOfficeConnection officeConnection = 
			new SocketOpenOfficeConnection(starOfficeConnectionPort);
		officeConnection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(officeConnection);
		converter.convert(
			new File(location + reportFileName + ".odt"), 
			new File(location + reportFileName + ".pdf"));
		officeConnection.disconnect();
	}
	
	private void addNormativePaper(OdfTextDocument odt, String odfTableName) throws Exception {
		OdfTable odfTable = odt.getTableByName(odfTableName);
		if (odfTable == null) {
			return;
		}
		for (Container container: this.containers) {
			if (container.getActualCondition() == null) {
				continue;
			}
			for (final Cargo cargo: container.getActualCondition().getCargoes()) {
				StringBuffer sb = new StringBuffer();
				sb.append(cargo.getArticle().getEnglishName()).append(", ")
					.append(cargo.getSupplier().getCompany().getEnglishName()).append(" / ")
					.append(cargo.getArticle().getName()).append(", ")
					.append(cargo.getSupplier().getCompany().getName());
				if (cargo.getInspectionInfo() != null && 
						cargo.getInspectionInfo().getNormativeDocument() != null) {
					NormativeDocument normativeDocument = 
						cargo.getInspectionInfo().getNormativeDocument();
					String tableName = reportUtil.insertTableCopy(odt, odfTableName, odfTableName);
					OdfTable table = odt.getTableByName(tableName);
					table.getCellByPosition(0, 0).setStringValue(sb.toString());
					if (normativeDocument == null) {
						table.getCellByPosition(0, 1).setStringValue("No data/Нет данных");
						table.getCellByPosition(1, 1).setStringValue("No data/Нет данных");
					} else {
						table.getCellByPosition(0, 1).setStringValue(
							normativeDocument.getEnglishName() != null ? 
									normativeDocument.getEnglishName() : "No data/Нет данных");
						table.getCellByPosition(1, 1).setStringValue(
							normativeDocument.getName() != null ? 
									normativeDocument.getName() : "No data/Нет данных");
					}
				} else {
					String tableName = reportUtil.insertTableCopy(odt, odfTableName, odfTableName);
					OdfTable table = odt.getTableByName(tableName);
					table.getCellByPosition(0, 0).setStringValue(sb.toString());
					table.getCellByPosition(0, 1).setStringValue("No data/Нет данных");
					table.getCellByPosition(1, 1).setStringValue("No data/Нет данных");
				}
			}
		}
		odfTable.remove();
	}
	
	private void addTemperatureData(OdfTextDocument odt, String odfTableName) {
		OdfTable odfTable = odt.getTableByName(odfTableName);
		if (odfTable == null) {
			return;
		}
		List<String> containersWithTemperatureDeviation = new ArrayList<String>();
		List<String> containersWithoutTemperatureDeviation = new ArrayList<String>();
		List<String> containersWithTemperatureSpy = new ArrayList<String>();
		List<String> containersWithoutTemperatureSpy = new ArrayList<String>();
		int size = this.containers.size() - 1;
		for (int i = 0; i < size; i ++) {
			odfTable.appendRow();
		}
		int currRow = 2;
		StringBuffer sb;
		for (Container container: this.containers) {
			if (container.getActualCondition() != null) {
				if (container.getActualCondition().getHasTemperatureTestDeviation() != null && 
					container.getActualCondition().getHasTemperatureTestDeviation().equals(Boolean.TRUE)) {
					containersWithTemperatureDeviation.add(container.getNumber());
				} else if (container.getActualCondition().getHasTemperatureTestDeviation() != null &&
					container.getActualCondition().getHasTemperatureTestDeviation().equals(Boolean.FALSE)) {
					containersWithoutTemperatureDeviation.add(container.getNumber());
				} else if (container.getActualCondition().getHasTemperatureTestDeviation() == null) {
					containersWithoutTemperatureDeviation.add(container.getNumber());
				}
				if (container.getActualCondition().getTemperatureSpyState() != null && 
						container.getActualCondition().getTemperatureSpyState() != TemperatureSpyStateEnum.NOT_FOUND
						&& container.getActualCondition().getTemperatureSpyState() != TemperatureSpyStateEnum.UNKNOWN) {
					containersWithTemperatureSpy.add(
						ReportUtil.getComplexString(new String[]{
							container.getActualCondition().getTemperatureSpyState().getName(),
							container.getActualCondition().getTemperatureSpyNumber()}, ","));
				} else {
					containersWithoutTemperatureSpy.add(container.getNumber());
				} 
			}
			sb = new StringBuffer();
			Double min = container.getDeclaredCondition().getMinTemperature();
			Double max = container.getDeclaredCondition().getMaxTemperature();
			Double actual = container.getActualCondition().getTemperature();
			if(min != null) {
				if (min > 0) {
					sb.append("+");
				}
				sb.append(min).append("/");
			}
			if(max != null) {
				if (max > 0) {
					sb.append("+");
				}
				sb.append(max);
			}
			odfTable.getCellByPosition(0, currRow).setStringValue(container.getNumber());
			odfTable.getCellByPosition(1, currRow).setStringValue(sb.toString());
			sb = new StringBuffer();
			if(actual != null) {
				if (actual > 0) {
					sb.append("+");
				}
				sb.append(actual);
			}
			odfTable.getCellByPosition(2, currRow).setStringValue(sb.toString());
			currRow++;
		}
		
		odfTable = odt.getTableByName("TableMobileThermoUnit");
		currRow = 0;
		if (containersWithoutTemperatureSpy.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithoutTemperatureSpy),
				"Mobile Thermo-Unit was not found in cargo.");
			currRow++;
		}
		if (containersWithTemperatureSpy.size() > 0) {
			//TODO refactor
			String thermoSpyData= ListHandler.getUniqueResult(containersWithTemperatureSpy);
			thermoSpyData = 
				thermoSpyData.replaceAll(TemperatureSpyStateEnum.FOUND_BROKEN.getName(), "Found broken");
			thermoSpyData = 
				thermoSpyData.replaceAll(TemperatureSpyStateEnum.FOUND_DEVIATED.getName(), "Found deviated");
			thermoSpyData = 
				thermoSpyData.replaceAll(TemperatureSpyStateEnum.FOUND_OK.getName(), "Found normal state");
			setCellValueExt(odfTable, 0, currRow, 
				"Mobile Thermo-Unit: ",
				thermoSpyData);
			currRow++;
		}
		if (containersWithTemperatureDeviation.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithTemperatureDeviation),
				"According actual fixation of temperature on arrival container on the terminal it was exposed deviation of temperature mode.");
			currRow++;
		}
		if (containersWithoutTemperatureDeviation.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithoutTemperatureDeviation),
				"According actual fixation of temperature on arrival container on the terminal it was not exposed deviation of temperature mode.");
			currRow++;
		}
		if (containersWithoutTemperatureSpy.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithoutTemperatureSpy),
				"Мобильная термо-единица не была найдена в грузе.");
			currRow++;
		}
		if (containersWithTemperatureSpy.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				"Мобильная термо-единица: ",
				ListHandler.getUniqueResult(containersWithTemperatureSpy));
			currRow++;
		}
		if (containersWithTemperatureDeviation.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithTemperatureDeviation),
				"По фактической фиксации температуры по приходу контейнера на терминал выявлены отклонения в температурном режиме.");
			currRow++;
		}
		if (containersWithoutTemperatureDeviation.size() > 0) {
			setCellValueExt(odfTable, 0, currRow, 
				ListHandler.getUniqueResult(containersWithoutTemperatureDeviation),
				"По фактической фиксации температуры по приходу контейнера на терминал отклонений в температурном режиме не выявлено.");
			currRow++;
		}
	}
	
	private void addCargoAmount(OdfTextDocument odt, String odfTableName) throws Exception {
		OdfTable odfTable = odt.getTableByName(odfTableName);
		if (odfTable == null) {
			return;
		}
		String defectsTableName = "TableDefects";
		OdfTable defectsTable = odt.getTableByName(defectsTableName);
		String defectsByClassTableName = "TableDefectsByClass";
		OdfTable defectsByClassTable = odt.getTableByName(defectsByClassTableName);
		final String tableBeforeInsertName = "TableCargoAmountAndDefectsAux";
		String tableCargoDigitalDataName = 
			reportUtil.insertTableCopy(odt, "TableCargoDigitalData", tableBeforeInsertName);
		odt.getTableByName("TableCargoDigitalData").remove();
		for (Container container: this.containers) {
			List<CargoDefects> listCargoDefects = this.statsService.calcAverageDefects(container.getId());
			for (final Cargo cargo: container.getActualCondition().getCargoes()) {
				String tableName = reportUtil.insertTableCopy(odt, odfTableName, tableBeforeInsertName);
				processCargoInspectionOptions(odt, cargo, tableCargoDigitalDataName);
				tableName = reportUtil.insertTableCopy(odt, odfTableName, tableBeforeInsertName);
				if (tableName != null) {
					Cargo declaredCargo = AlgoUtil.find(container.getDeclaredCondition().getCargoes(), 
							new AlgoUtil.FindPredicate<Cargo>() {
						@Override
						public boolean isIt(Cargo c) {
							return c.getArticle().getName().equals(cargo.getArticle().getName()) && 
								c.getArticleCategory().getName().equals(cargo.getArticleCategory().getName()) && 
								c.getSupplier().getCompany().equals(cargo.getSupplier().getCompany());
						}
					});
					processCargoAmount(odt.getTableByName(tableName), container.getNumber(), 
							declaredCargo, cargo);
				}
				if (defectsTable != null && !listCargoDefects.isEmpty()) {
					tableName = reportUtil.insertTableCopy(odt, defectsTableName, tableBeforeInsertName);
					CargoDefects cargoDefects = AlgoUtil.find(listCargoDefects, 
							new AlgoUtil.FindPredicate<CargoDefects>() {
						@Override
						public boolean isIt(CargoDefects cd) {
							boolean result = false;
							if (cd.getCargoName().equals(cargo.getArticle().getName() + ", "  + 
									cargo.getArticleCategory().getName())) {
								if (cd.getCategoryNames() == null) {
									return false;
								}
								String[] clone = cd.getCategoryNames().clone();
								Arrays.sort(clone);
								for (ArticleCategory articleCategory: cargo.getArticle().getCategories()) {
									if (Arrays.binarySearch(
											clone, articleCategory.getName()) == -1) {
										return false;
									} else {
										result = true;
									}
								}
							}
							return result;
						}
					});
					if (cargoDefects != null) {
						processCargoDefects(odt.getTableByName(tableName), cargoDefects);
					} else {
						odt.getTableByName(tableName).remove();
					}
				}
				if (defectsByClassTable != null && cargo.getInspectionInfo() != null) {
					tableName = reportUtil.insertTableCopy(odt, defectsByClassTableName, tableBeforeInsertName);
					OdfTable tbl = odt.getTableByName(tableName);
					List<CargoDefectGroup> cargoDefectGroups = 
						cargo.getInspectionInfo().getDefectGroups();
					for(CargoDefectGroup cargoDefectGroup: cargoDefectGroups) {
						processCargoDefectsByClass(tbl, cargoDefectGroup);
					}
					int lastRow = tbl.getRowCount() - 1;
					if (lastRow == 1) {
						tbl.remove();
					} else {
						tbl.removeRowsByIndex(lastRow, lastRow);
					}
				}
			}
		}
		odt.getTableByName(TABLE_BRIX_SCALE).remove();
		if (defectsTable != null) {
			defectsTable.remove();
		}
		if (defectsByClassTable != null) {
			defectsByClassTable.remove();
		}
		odfTable.remove();
		odfTable = odt.getTableByName(TABLE_RIPENESS);
		if (odfTable != null && odfTable.getColumnCount() <3) {
			odfTable.remove();
		}
	}
	
	private void processCargoInspectionOptions(OdfTextDocument odt, Cargo cargo, 
			String tableBeforeInsertDataName) throws Exception {
		Iterator<CargoInspectionOption> iterator = 
			cargo.getInspectionInfo().getInspectionOptions().iterator();
		while (iterator.hasNext()) {
			CargoInspectionOption inspectionOption = iterator.next();
			if (inspectionOption.getArticleInspectionOption().getName().contains("Брикса")) {
				String tableName = 
					reportUtil.insertTableCopy(odt, TABLE_BRIX_SCALE, tableBeforeInsertDataName);
				StringBuffer stringBuffer = new StringBuffer(cargo.getArticle().getEnglishName());
				stringBuffer.append(", ").append(cargo.getSupplier().getCompany().getEnglishName())
					.append(" / ").append(cargo.getArticle().getName()).append(", ")
					.append(cargo.getSupplier().getCompany().getName());
				odt.getTableByName(tableName).getCellRangeByPosition(0, 0, 1, 0).merge();
				odt.getTableByName(tableName).getCellByPosition(0, 1)
					.setStringValue(stringBuffer.toString());
				odt.getTableByName(tableName).getCellByPosition(1, 1)
					.setStringValue(String.valueOf(inspectionOption.getValue()) + "°Bx");
			} else if (inspectionOption.getArticleInspectionOption().getParent() != null && 
					inspectionOption.getArticleInspectionOption().getParent().getName().toUpperCase()
					.contains("зрелость".toUpperCase())) {
				processRipeness(odt, inspectionOption);
			}
		}
	}
	
	private void processCargoDefectsByClass(OdfTable odfTable, CargoDefectGroup cargoDefectGroup) {
		int currRow = odfTable.getRowCount() - 1;
		List<String> cargoDefectsEng = new ArrayList<String>();
		List<String> cargoDefectsRus = new ArrayList<String>();
		StringBuffer sbEng;
		StringBuffer sbRus;
		for(Iterator<CargoDefect> itrDefectGroup = cargoDefectGroup.getDefects().iterator(); 
				itrDefectGroup.hasNext(); ) {
			sbEng = new StringBuffer();
			sbRus = new StringBuffer();
			CargoDefect cargoDefect = itrDefectGroup.next();
			if (cargoDefect.getArticleDefect() != null) {
				sbEng.append(cargoDefect.getArticleDefect().getEnglishName());
				sbRus.append(cargoDefect.getArticleDefect().getName());
			}
			if (cargoDefect.getEnglishName() != null) {
				if (sbEng.length() > 0) {
					sbEng.append(", ").append(cargoDefect.getEnglishName());
				} else {
					sbEng.append(cargoDefect.getEnglishName());
				}
			}
			if (cargoDefect.getName() != null) {
				if (sbRus.length() > 0) {
					sbRus.append(", ").append(cargoDefect.getName());
				} else {
					sbRus.append(cargoDefect.getName());
				}
			}
			cargoDefectsEng.add(sbEng.toString());
			cargoDefectsRus.add(sbRus.toString());
		}
		int engSize = cargoDefectsEng.size(); 
		int rusSize = cargoDefectsRus.size();
		if (engSize > 0 || rusSize > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append(cargoDefectGroup.getArticleCategory().getEnglishName()).append(" / ")
				.append(cargoDefectGroup.getArticleCategory().getName());
			odfTable.getCellByPosition(0, currRow).setStringValue(sb.toString());
			if (engSize > 0) {
				odfTable.getCellByPosition(1, currRow).setStringValue(
					ListHandler.getUniqueResult(cargoDefectsEng));
			}
			if (rusSize > 0) {
				odfTable.getCellByPosition(2, currRow).setStringValue(
						ListHandler.getUniqueResult(cargoDefectsRus));
			}
			odfTable.appendRow();
		}
	}
	
	private void processCargoAmount(OdfTable odfTable, String container, 
			Cargo declaredCargo, Cargo actualCargo) {
		int currRow = 0;
		odfTable.insertRowsBefore(currRow, 1);
		odfTable.getCellRangeByPosition(0, currRow, 6, currRow).merge();
		odfTable.getCellByPosition(0, currRow).setHorizontalAlignment("center");
		StringBuffer stringBuffer = new StringBuffer(container);
		stringBuffer.append(", ").append(actualCargo.getArticle().getName()).append(", ").append(
			actualCargo.getSupplier().getCompany().getName());
		odfTable.getCellByPosition(0, currRow).setStringValue(stringBuffer.toString());
		currRow++;
		odfTable.getCellByPosition(0, currRow).setHorizontalAlignment("center");
		odfTable.getCellByPosition(1, currRow).setHorizontalAlignment("center");
		currRow = 3;
		for(final CargoPackage cargoPackage: actualCargo.getCargoPackages()) {
			odfTable.getCellByPosition(0, currRow).setStringValue(cargoPackage.getMeasure()
					.getEnglishName() + " / " + cargoPackage.getMeasure().getName());
			if (declaredCargo != null) {
				CargoPackage declaredCargoPackage = AlgoUtil.find(declaredCargo.getCargoPackages(), 
						new AlgoUtil.FindPredicate<CargoPackage>() {
					@Override
					public boolean isIt(CargoPackage cp) {
						return cp.getMeasure().equals(cargoPackage.getMeasure());
					}
				});
				if (declaredCargoPackage != null) {
					odfTable.getCellByPosition(2, currRow).setStringValue(
							String.format(ReportUtil.DOUBLE_FORMAT0, declaredCargoPackage.getCount()));
					reportUtil.setRatingValue(odfTable.getCellByPosition(3, currRow),
							cargoPackage.getCount(), declaredCargoPackage.getCount(), 
							ReportUtil.DOUBLE_FORMAT0);
				} else {
					odfTable.getCellByPosition(2, currRow).setStringValue("0");
					reportUtil.setRatingValue(odfTable.getCellByPosition(3, currRow),
							cargoPackage.getCount(), 0, ReportUtil.DOUBLE_FORMAT0);
				}
			} else {
				odfTable.getCellByPosition(2, currRow).setStringValue("0");
				reportUtil.setRatingValue(odfTable.getCellByPosition(3, currRow),
						cargoPackage.getCount(), 0, ReportUtil.DOUBLE_FORMAT0);
			}
			odfTable.getCellByPosition(1, currRow).setStringValue(
					String.format(ReportUtil.DOUBLE_FORMAT0, cargoPackage.getCount()));
			AverageCargoPackageWeights averageCargoPackageWeights =
				CargoUtil.calcAverageWeights(cargoPackage.getWeights());
			if (averageCargoPackageWeights != null) {
				odfTable.getCellByPosition(4, currRow).setStringValue(
						String.format(ReportUtil.DOUBLE_FORMAT3, averageCargoPackageWeights.getGrossWeight()));
				odfTable.getCellByPosition(5, currRow).setStringValue(
						String.format(ReportUtil.DOUBLE_FORMAT3, averageCargoPackageWeights.getNetWeight()));
				odfTable.getCellByPosition(6, currRow).setStringValue(
						String.format(ReportUtil.DOUBLE_FORMAT3, averageCargoPackageWeights.getTareWeight()));
			}
			currRow++;
		}
	}
	
	private void processCargoDefects(OdfTable odfTable, CargoDefects cargoDefects) {
		int len = cargoDefects.getCategoryNames().length;
		odfTable.appendColumns(len);
		odfTable.getCellRangeByPosition(0, 0, 1 + len, 0).merge();
		odfTable.getCellRangeByPosition(0, 1, 1 + len, 1).merge();
		odfTable.getCellRangeByPosition(0, 2, 1 + len, 2).merge();
		odfTable.getCellRangeByPosition(0, 3, 1 + len, 3).merge();
		int currColumn = 2;
		for(int i = 0; i < len; i++) {
			odfTable.getCellByPosition(currColumn, 4).setStringValue(cargoDefects.
					getCategoryEnglishNames()[i] + " / " +  cargoDefects.getCategoryNames()[i]);
			currColumn++;
		}
		int currRow = 5;
		for (CargoCalibreDefects calibreDefect: cargoDefects.getCalibreDefects()) {
			odfTable.getCellByPosition(0, currRow).setStringValue(
				calibreDefect.getCalibreName());
			odfTable.getCellByPosition(1, currRow).setStringValue(
					String.format(ReportUtil.DOUBLE_FORMAT0, calibreDefect.getPackageCount()));
			currColumn = 2;
			for(int i = 0; i < len; i++) {
				odfTable.getCellByPosition(currColumn, currRow).setStringValue(
						String.format(ReportUtil.DOUBLE_FORMAT1, calibreDefect.getPercentages()[i]));
				currColumn++;
			}
			currRow++;
		}
		currColumn = 2;
		CargoCalibreDefects averageCalibreDefects = cargoDefects.getAverageCalibreDefects();
		len = averageCalibreDefects.getPercentages().length;
		odfTable.getCellRangeByPosition(0, currRow, 1, currRow).merge();
		odfTable.getCellByPosition(0, currRow).setStringValue("Summary / Итого");
		for(int i = 0; i < len; i++) {
			odfTable.getCellByPosition(currColumn, currRow).setStringValue(
				String.format(ReportUtil.DOUBLE_FORMAT1, averageCalibreDefects.getPercentages()[i]));
			currColumn++;
		}
	}
	
	private void setCellValueExt(OdfTable odfTable, int column, int row, 
			String data, String comment) {
		StringBuffer sb = new StringBuffer(data).append(" ").append(comment);
		odfTable.getCellByPosition(column, row).setStringValue(sb.toString());
		//odfTable.appendRow();
	}
	
	private void addGeneralCargoImages(OdfTextDocument odt, String odfTableName) throws Exception {
		OdfTable odfTable = odt.getTableByName(odfTableName);
		if (odfTable == null) {
			return;
		}
		for (Container container: this.containers) {
			Inspection inspection = container.getInspection();
			if (inspection == null || inspection.getImages() == null || inspection.getImages().isEmpty()) {
				continue;
			} else {
				odfTable.appendRow();
				odfTable.appendColumn();
				odfTable.getCellRangeByPosition(0, 0, 1, 0).merge();
				int currRow = odfTable.getRowCount() - 1;
				int columntIndex = 0;
				for (FileUrl fileUrl: inspection.getImages()) {
					addImage(odt, fileUrl, odfTable, columntIndex, currRow);
					switch (columntIndex) {
					case 0:
						columntIndex = 1;
						break;
					case 1: 	
						columntIndex = 0;
						currRow++;
					}
				}
				odfTable.appendRow();
			}
			if (container.getActualCondition() == null) {
				continue;
			}
			Set<Cargo> cargoes = container.getActualCondition().getCargoes();
			if (cargoes == null || cargoes.size() == 0) {
				continue;
			}
			OdfTable odfQualityExpertiseTable = odt.getTableByName("TableQualityExpertise");
			if (odfQualityExpertiseTable == null) {
				return;
			}
			odfQualityExpertiseTable.appendRow();
			odfQualityExpertiseTable.appendColumn();
			odfQualityExpertiseTable.getCellRangeByPosition(0, 0, 1, 0).merge();
			int currRow = odfQualityExpertiseTable.getRowCount() - 1;
			for (Cargo cargo: cargoes) {
				currRow = odfQualityExpertiseTable.getRowCount() - 1;
				int columntIndex = 0;
				CargoInspectionInfo inspectionInfo =
					cargoService.getCargoInspectionInfo(cargo.getId());
				for (FileUrl fileUrl: inspectionInfo.getImages()) {
					addImage(odt, fileUrl, odfQualityExpertiseTable, columntIndex, currRow);
					switch (columntIndex) {
					case 0:
						columntIndex = 1;
						break;
					case 1: 	
						columntIndex = 0;
						currRow++;
					}
				}
				odfQualityExpertiseTable.appendRow();
			}
		}
	}
	
	private void addImages(OdfTextDocument odt, String odfTableName, String engNoImageComment, 
			String rusNoImageComment, String engImageUrlProperty, String rusImageUrlProperty) throws Exception {
		OdfTable odfTable = odt.getTableByName(odfTableName);
		if (odfTable == null) {
			return;
		}
		for (Container container: this.containers) {
			if (container.getActualCondition() == null) {
				continue;
			}
			Set<Cargo> cargoes = container.getActualCondition().getCargoes();
			if (cargoes == null || cargoes.size() == 0) {
				int currRow = odfTable.getRowCount() - 1;
				if ( currRow > 1) {
					odfTable.appendRow();
					odfTable.appendRow();
					currRow += 2;
				} else {
					odfTable.appendRow();
				}
				odfTable.getCellByPosition(0, currRow).setStringValue(container.getNumber());
				odfTable.getCellByPosition(0, currRow + 1).setStringValue(engNoImageComment);
				odfTable.getCellByPosition(1, currRow + 1).setStringValue(rusNoImageComment);
				continue;
			}
			int currRow = odfTable.getRowCount() - 1;
			if (currRow > 1) {
				odfTable.appendRow();
				currRow++;
			}
			odfTable.getCellByPosition(0, currRow).setStringValue(container.getNumber());
			for (Cargo cargo: cargoes) {
				odfTable.appendRow();
				currRow = odfTable.getRowCount() - 1;
				OdfTableCell odfTableCell;
				CargoInspectionInfo inspectionInfo =
					cargoService.getCargoInspectionInfo(cargo.getId());
				FileUrl fileUrl = (FileUrl) XMLUtil.getObjectProperty(
					inspectionInfo, engImageUrlProperty);
				if (fileUrl != null) {
					addImage(odt, fileUrl, odfTable, 0, currRow);
				} else {
					odfTableCell = odfTable.getCellByPosition(0, currRow);
					odfTableCell.setStringValue(engNoImageComment);					
				}
				fileUrl = (FileUrl) XMLUtil.getObjectProperty(
					inspectionInfo, rusImageUrlProperty);
				if (fileUrl != null) {
					addImage(odt, fileUrl, odfTable, 1, currRow);
				} else {
					odfTableCell = odfTable.getCellByPosition(1, currRow);
					odfTableCell.setStringValue(rusNoImageComment);					
				}
			}
		}
	}
	
	private void addImage(OdfTextDocument odt, FileUrl imageURL, OdfTable odfTable, 
			int column, int row) throws Exception {
		String storagePath = "file:///" + applicationConfigService.getImagesStoragePath() + "/";
		try {
		odt.getPackage().insert(new URI(storagePath + imageURL.getValue()), 
			imageURL.getValue(), null);
		} catch (FileNotFoundException expt) {
			DocumLogger.log("FileNotFoundException: " + expt.getMessage());
		}
		OdfTableCell odfTableCell = odfTable.getCellByPosition(column, row);
		TableTableCellElement tableCellElement = 
			(TableTableCellElement) odfTableCell.getOdfElement();
		TextPElement textPElement = tableCellElement.newTextPElement();
		initImageAttributes(textPElement.newDrawFrameElement(), imageURL.getValue());
	}
	
	private void initImageAttributes(DrawFrameElement drawFrameElement, String fileUrl)
			throws Exception{
		if (fileUrl == null) {
			throw new Exception("Не указано расположение изображения");
		}
		drawFrameElement.setDrawZIndexAttribute(0);
		drawFrameElement.setDrawStyleNameAttribute("fr1");
		drawFrameElement.setSvgHeightAttribute("6.283cm");
		drawFrameElement.setSvgWidthAttribute("8.371cm");
		DrawImageElement drawImageElement = drawFrameElement.newDrawImageElement();
		drawImageElement.setXlinkHrefAttribute(fileUrl);
		drawImageElement.setXlinkActuateAttribute("onLoad");
		drawImageElement.setXlinkShowAttribute("embed");
		drawImageElement.setXlinkTypeAttribute("simple");
	}
	
	private void initTemlateAccordance(final Report report) {
		this.temlateAccordance.put("Report",  new Object[]{report, "number"});
		this.temlateAccordance.put("CargoReceiverName",  
			new Object[]{report, "customer.company.name"});
		this.temlateAccordance.put("CargoReceiverAddr",  
			new Object[]{report, "customer.company.address"});
		this.temlateAccordance.put("CargoReceiverNameEng",  
			new Object[]{report, "customer.company.englishName"});
		this.temlateAccordance.put("CargoReceiverAddrEng",  
			new Object[]{report, "customer.company.englishAddress"});
		this.temlateAccordance.put("ReportClient",  
			new Object[]{report, "customer.company.name"});
		this.temlateAccordance.put("ReportClientAddr",  
			new Object[]{report, "customer.company.address"});
		this.temlateAccordance.put("ReportClientEng",  
			new Object[]{report, "customer.company.englishName"});
		this.temlateAccordance.put("ReportClientAddrEng",  
			new Object[]{report, "customer.company.englishAddress"});
		this.temlateAccordance.put("actualCargoesEnglishName",  
			new Object[]{containerPresentationMap, "actualCargoesEnglishName"});
		this.temlateAccordance.put("actualCargoesName",  
			new Object[]{containerPresentationMap, "actualCargoesName"});
		this.temlateAccordance.put("cargoSuppliers",  
				new Object[]{containerPresentationMap, "actualCargoSuppliers"});
		this.temlateAccordance.put("cargoSuppliersEng",  
				new Object[]{containerPresentationMap, "actualCargoSuppliersEng"});
	}
	
	public boolean checkStarOfficeConnection() {
		try {
			OpenOfficeConnection officeConnection = 
				new SocketOpenOfficeConnection(starOfficeConnectionPort);
			officeConnection.connect();
			officeConnection.disconnect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private void processNode(Node node) throws Exception {
		if (node.getNodeValue() != null) {
			int statementBeginPos = node.getNodeValue().indexOf(STATEMENT_BEGIN);
			int statementEndPos = node.getNodeValue().indexOf(STATEMENT_END);
			if (statementBeginPos != -1) {
				replaceNodeValue(node, node.getNodeValue(), statementBeginPos, statementEndPos);
			}
		}
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			int length = nodeList.getLength();
			for(int i = 0; i< length; i++) {
				processNode(nodeList.item(i));
			}
		}
	}
	
	private void processContainerOrRefrigerator(Node node, String value) {
		String containerNumber = this.containers.get(0).getNumber().replaceAll(" ", "");
		if (containerNumber.length() < 11 || containerNumber.contains("-")) {
			if (value.equals("{$TypeRus}")) {
				node.setNodeValue("Рефрижераторная тележка");
			} else {
				node.setNodeValue("Refrigerator trailer");
			}
		} else {
			if (value.equals("{$TypeRus}")) {
				node.setNodeValue("Контейнер");
			} else {
				node.setNodeValue("Container");
			}
		}
	}
	
	private void replaceNodeValue(Node node, String processedValue, 
			int statementBeginPos, int statementEndPos) throws Exception {
		if (statementEndPos == -1) {
			return;
		}
		if (processedValue.contains("{$Type")) {
			processContainerOrRefrigerator(node, processedValue);
			return;
		}
		String result = processedValue.substring(
			statementBeginPos + STATEMENT_BEGIN.length(), statementEndPos);
		if (this.temlateAccordance.keySet().contains(result)) {
			Object[] o = this.temlateAccordance.get(result);
			if (o[0] instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> objectsMap = (Map<Object, Object>) o[0];
				List<Object> propertyValues = new ArrayList<Object>();
				for (Object keyObject:  objectsMap.keySet()) {
					Object valueObject = objectsMap.get(keyObject);
					Object nestedPropertValue = 
						XMLUtil.propertyUtilsBean.getNestedProperty(valueObject, o[1].toString());
					propertyValues.add(nestedPropertValue);
				}
				node.setNodeValue(ListHandler.getUniqueResult(propertyValues));
			} else {
				Object value = XMLUtil.propertyUtilsBean.getNestedProperty(o[0], o[1].toString());
				node.setNodeValue(value != null ? value.toString() : "");
			}
		} else {
			String props[] = result.split("\\.");
			List<Object> propertyValues = new ArrayList<Object>();
			for (Container container: this.containers) {
				Object propertyValue = XMLUtil.getObjectProperty(container, props[0]);
				if (propertyValue != null) { 
					if (propertyValue instanceof List && props.length == 1) {
						@SuppressWarnings("unchecked")
						List<Object> objects = (List<Object>) propertyValue;
						propertyValues.add(ListHandler.getUniqueResult(objects));
					} else if (propertyValue instanceof List && props.length == 2) {
						@SuppressWarnings("unchecked")
						List<Object> objects = (List<Object>) propertyValue;
						List<Object> values = new ArrayList<Object>();
						for (Object object: objects) {
							values.add(XMLUtil.propertyUtilsBean.getSimpleProperty(object, props[1]));
						}
						propertyValues.add(ListHandler.getUniqueResult(values));
					} else if (propertyValue instanceof Set && props.length == 1) {
						@SuppressWarnings("unchecked")
						Set<Object> objects = (Set<Object>) propertyValue;
						List<Object> values = new  ArrayList<Object>();
						for (Object object: objects) {
							values.add(object);
						}
						propertyValues.add(ListHandler.getUniqueResult(values));
					} else {
						if (propertyValue instanceof Date) {
							propertyValues.add(ReportUtil.DATE_FORMAT.format(propertyValue));
						} else {
							Object value = null;
							try {
								value = XMLUtil.propertyUtilsBean.getNestedProperty(container, result);
							} catch (NestedNullException expt) {
								DocumLogger.log("NestedNullException " + expt.getMessage());
							}
							if (value instanceof Date) {
								value = ReportUtil.DATE_FORMAT.format(value);
							}
							propertyValues.add(value != null ?  value.toString() : " ");
						}
					}
				}
			}
			node.setNodeValue(ListHandler.getUniqueResult(propertyValues));
		}
	}
	
	public int getStarOfficeConnectionPort() {
		return starOfficeConnectionPort;
	}

	public void setStarOfficeConnectionPort(int starOfficeConnectionPort) {
		this.starOfficeConnectionPort = starOfficeConnectionPort;
	}
	
	private void processRipeness(OdfTextDocument odt, CargoInspectionOption inspectionOption){
		OdfTable table = odt.getTableByName(TABLE_RIPENESS);
		if (table == null) {
			return;
		}
		table.appendColumn();
		int currColumn = table.getColumnCount() - 1;
		table.getCellRangeByPosition(0, 0, currColumn, 0).merge();
		StringBuffer buf = 
			new StringBuffer(inspectionOption.getArticleInspectionOption().getEnglishName());
		buf.append(" / ").append(inspectionOption.getArticleInspectionOption().getName());
		table.getCellByPosition(currColumn, 1).setStringValue(buf.toString());
		table.getCellByPosition(currColumn, 2).setStringValue(
			String.valueOf(inspectionOption.getValue()));
	}
	
	@Override
	public List<Report> getReportsByVoyage(Long voyageId) {
		return reportingDao.getReportsByVoyage(voyageId);
	}
	
	@Override
	public Long getReportsWithinYear(int year) {
		return reportingDao.getReportsWithinYear(year);
	}
}
