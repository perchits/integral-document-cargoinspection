package com.docum.test.data;

import java.util.ArrayList;
import java.util.List;

import com.docum.domain.po.common.Article;
import com.docum.domain.po.common.ArticleCategory;

public class ArticleDataPreparator {
	
	private static String[][] articleNames = new String[][] {
			{"Помело свежий", "Fresh Pomelo"},
			{"Чеснок свежий", "Fresh Garlic"},
			{"Яблоки свежие", "Fresh Apples"},
			{"Грейпфрут свежий", "Fresh Grapefruit"},
			{"Картофель свежий", "Fresh Potatoes"}
	};

	private static String[][] categoryNames = new String[][] {
			{"Сорт 1", "Class I"},
			{"Сорт 2", "Class II"},
			{"Сорт 1", "Cat 1"},
			{"Сорт 2", "Cat 2"},
			{"-", "Class I"},
			{"-", "Class II"},
			{"-", "Cat 1"},
			{"-", "Cat 2"}};
	
	public static List<Article> prepareArticles(TestDataPersister persister) {
		List<Article> result = new ArrayList<Article>();
		for(String[] rec : articleNames) {
			Article article = new Article(rec[0], rec[1]);
			article.setCategories(prepareCategories(persister));
			result.add(article);
		}
		persister.persist(result);
		return result;
	}
	
	private static List<ArticleCategory> prepareCategories(TestDataPersister persister) {
		List<ArticleCategory> result = new ArrayList<ArticleCategory>();
		for(String[] rec : categoryNames) {
			result.add(new ArticleCategory(rec[0], rec[1]));
		}
		persister.persist(result);
		return result;
	}
}
