package com.docum.test.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleDefect;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;

public class ArticleDataPreparator {
	private static String[][][] articleDefectNames = new String[][][]
	{
		{
			{"Незначительные дефекты формы, окраски", "Insignificant defects of shape and colouring"},
			{"Незначительные дефекты кожицы, незначительные помятости, зарубцевавшиеся трещины более 1 см", "Defects of skin, insignificant bruises, mechanical damages cicatrize cracks more 1 cm"},
			{"Опробковение рыльца более 1 кв. см", "Suberization of stigma more 1 cm"}
		},
		{
			{"Дефекты кожицы, помятости без серьезных повреждений, зарубцевавшиеся трещины более 3 см", "Defects of skin, bruises without heavy damages, cicatrize crack more 3 cm"},
			{"Перезревшие, признаки сморщивания (вялые плоды)", "Overriped / Signs of corrugation (sleepy fruits)"},
			{"Следы посторонних веществ", "Signs of strange substance"}
		},
		{
			{"Наличие солнечных и земляных ожогов; повреждения насекомыми", "Presence of sunburns and mud burns and damages by insects"},
			{"С посторонним запахом и/или привкусом", "With strange smell or smack"},
			{"Продукт, подверженный гниению или порче, непригодный к употреблению", "Product subject to molding or spoiling, that unsuitable to usage"},
			{"Сильные механические повреждения", "Strong mechanical damages"}
		}
	};
	private static TestDataEntityCounter<String[][]> articleDefectCounter =
		new TestDataEntityCounter<String[][]>(articleDefectNames);

	public static List<Article> prepareArticles(TestDataPersister persister) {
		List<Article> result = new ArrayList<Article>();
		result.add(prepareApples(persister));
		result.add(preparePears(persister));
		result.add(prepareGarlic(persister));
		result.add(prepareCognac(persister));
		persister.persist(result);
		return result;
	}

	private static Article prepareApples(TestDataPersister persister) {
		Article article = new Article("Яблоки", "Fresh Apples");
		persister.persist(article);
		article.setCategories(prepareCategories(persister, article, new String[][] {
				{"Сорт 1", "Class I"},
				{"Сорт 2", "Class II"},
				{"Брак", "Waste"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Вид", "Variety",
				new String[][] {
				{"Делишес", "Delicious"},
				{"Голден", "Golden"},
				{"Ред Чиф", "Red Cheaf"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Урожай", "Crop", null));
		persister.persist(article);
		return article;
	}

	private static Article preparePears(TestDataPersister persister) {
		Article article = new Article("Груши", "Fresh Pears");
		persister.persist(article);
		article.setCategories(prepareCategories(persister, article, new String[][] {
				{"Сорт 1", "Class I"},
				{"Сорт 2", "Class II"},
				{"Брак", "Waste"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Вид", "Variety",
				new String[][] {
				{"Конференц", "Conference"},
				{"Вильямс", "Williams"},
				{"Барлет", "Barlett"},
				{"Анжу", "Anjou"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Урожай", "Crop", null));
		persister.persist(article);
		return article;
	}
	
	private static Article prepareGarlic(TestDataPersister persister) {
		Article article = new Article("Чеснок", "Fresh Garlic");
		persister.persist(article);
		article.setCategories(prepareCategories(persister, article, new String[][] {
				{"Сорт 1", "Class I"},
				{"Сорт 2", "Class II"},
				{"Брак", "Waste"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article,
				"Страна происхождения", "Origin", new String[][] {
				{"Россия", "Russia"},
				{"Китай", "China"}}));
		persister.persist(article);
		return article;
	}

	private static Article prepareCognac(TestDataPersister persister) {
		Article article = new Article("Коньяк", "Cognac");
		persister.persist(article);
		article.setCategories(prepareCategories(persister, article, new String[][] {
			{"3 звезды", "3 stars"},
			{"5 звёзд", "5 stars"},
			{"XO", "XO"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Марка", "Mark",
				new String[][] {
				{"Юбилейный", "Yubileyny"},
				{"Хеннеси", "Hennesey"}}));
		article.getFeatures().add(prepareArticleFeature(persister, article, "Год", "Year", 
				new String[][] {
				{"2000", "2000"}, {"2001", "2001"}, {"2002", "2002"}}));
		persister.persist(article);
		return article;
	}

	private static ArticleFeature prepareArticleFeature(TestDataPersister persister,
			Article article, String name, String englishName, String[][] instanceNames) {
		ArticleFeature feature = new ArticleFeature(article, name, englishName,
				instanceNames != null);
		persister.persist(feature);
		if(instanceNames != null) {
			for(String[] rec : instanceNames) {
				feature.getInstances().add(new ArticleFeatureInstance(feature, rec[0], rec[1]));
			}
			persister.persist(feature.getInstances());
		}
		return feature;
	}

	private static List<ArticleCategory> prepareCategories(TestDataPersister persister,
			Article article, String[][] categoryNames) {
		List<ArticleCategory> result = new ArrayList<ArticleCategory>();
		int ord = 0;
		for(String[] rec : categoryNames) {
			ArticleCategory category = new ArticleCategory(article, rec[0], rec[1]);
			category.setOrd(ord++);
			category.setDefects(prepareDefects(persister, category));
			result.add(category);
		}
		persister.persist(result);
		return result;
	}

	private static Set<ArticleDefect> prepareDefects(TestDataPersister persister,
			ArticleCategory category) {
		Set<ArticleDefect> result = new HashSet<ArticleDefect>();
		String[][] defectNames = articleDefectCounter.next();
		for(String[] rec : defectNames) {
			ArticleDefect defect = new ArticleDefect(category);
			defect.setName(rec[0]);
			defect.setEnglishName(rec[1]);
			result.add(defect);
		}
		//нельзя вызывать, так как еще не сохранена категория, сохранится автоматом
		//persister.persist(result);
		return result;
	}
}
