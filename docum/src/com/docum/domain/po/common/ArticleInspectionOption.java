package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.docum.domain.po.OrderedEntity;
import com.docum.util.OrderedEntityUtil;
@Entity
public class ArticleInspectionOption extends OrderedEntity {
	private static final long serialVersionUID = 2579866812610907991L;

	private String name;

	private String englishName;
	
	@ManyToOne(optional=true)
	private Article article;

	@ManyToOne
	private ArticleInspectionOption parent;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn(name="ord")
	private List<ArticleInspectionOption> children = new ArrayList<ArticleInspectionOption>();

	public ArticleInspectionOption() {
		
	}

	public ArticleInspectionOption(String name, String englishName) {
		this.name = name;
		this.englishName = englishName;
	}

	public ArticleInspectionOption(Article article, String name, String englishName) {
		this(name, englishName);
		this.article = article;
	}
	
	public ArticleInspectionOption(Article article, String name, String englishName,
			ArticleInspectionOption parent) {
		this(article, name, englishName);
		this.parent = parent;
	}	
	
	/**
	 * Копирующий конструктор
	 */
	public ArticleInspectionOption(ArticleInspectionOption option) {
		this(option.article, option.name, option.englishName);		
		for (ArticleInspectionOption child : option.getChildren()) {
			addChild(new ArticleInspectionOption(child));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public ArticleInspectionOption getParent() {
		return parent;
	}

	public void setParent(ArticleInspectionOption parent) {
		this.parent = parent;
	}

	public List<ArticleInspectionOption> getChildren() {
		return children;
	}

	public void setChildren(List<ArticleInspectionOption> children) {
		this.children = children;
	}
	
	public void addChild(ArticleInspectionOption child){
		child.setParent(this);
		/*Нельзя назначать товар, ибо хибернейт замапит*/
		/*child.setArticle(article);*/
		OrderedEntityUtil.add(child, children);
	}
	
	public void removeChild(ArticleInspectionOption child){
		if(OrderedEntityUtil.remove(child, children)) {
			child.setParent(null);
		}
	}

	public void setChildOrd(ArticleInspectionOption child, int ord) {
		OrderedEntityUtil.setOrd(child, children, ord);
	}

	public void moveChildUp(ArticleInspectionOption child) {
		OrderedEntityUtil.moveUp(child, children);
	}

	public void moveChildDown(ArticleInspectionOption child) {
		OrderedEntityUtil.moveDown(child, children);
	}
	
	@Override
	public String getEntityName() {
		return "Свойство";
	}
	
	@Override
	public GenderEnum getEntityGender() {
		return GenderEnum.NEUTER;
	}
	
}
