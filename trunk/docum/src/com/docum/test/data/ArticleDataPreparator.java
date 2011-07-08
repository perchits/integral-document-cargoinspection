package com.docum.test.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;

public class ArticleDataPreparator {
	
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
				{"Сорт 1", "Cat 1"},
				{"Сорт 2", "Cat 2"}}));
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
				{"Сорт 1", "Cat 1"},
				{"Сорт 2", "Cat 2"}}));
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
				{"Сорт 1", "Cat 1"},
				{"Сорт 2", "Cat 2"}}));
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

	private static Set<ArticleCategory> prepareCategories(TestDataPersister persister,
			Article article, String[][] categoryNames) {
		Set<ArticleCategory> result = new HashSet<ArticleCategory>();
		for(String[] rec : categoryNames) {
			result.add(new ArticleCategory(article, rec[0], rec[1]));
		}
		persister.persist(result);
		return result;
	}
}
