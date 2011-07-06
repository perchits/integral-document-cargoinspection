package com.docum.domain.po.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Cargo extends IdentifiedEntity {
	private static final long serialVersionUID = 4275515653210816278L;
	
	private static enum ConditionEnum {
		DECLARED, ACTUAL
	}
	
	@ManyToOne(optional=false)
	private Article article;

	@ManyToOne
	private ArticleCategory articleCategory;

	@OneToMany(mappedBy="cargo", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<CargoArticleFeature> features = new HashSet<CargoArticleFeature>();
	
	@ManyToOne(optional=false)
	private Supplier supplier;
	
	@ManyToOne(optional=false)
	private Container container;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@OrderColumn(nullable=false)
	private List<CargoCondition> conditions = Arrays.asList(new CargoCondition[] { new CargoCondition(this, 0.0),
					new CargoCondition(this, 0.0) });

	public Cargo(){
		super();
	}
	
	public Cargo(Article article, Supplier supplier,
			Container container) {
		super();
		this.article = article;
		this.supplier = supplier;
		this.container = container;
	}

	public Article getArticle() {
		return article;
	}


	public void setArticle(Article goods) {
		this.article = goods;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public CargoCondition getDeclaredCondition() {
		return conditions.get(ConditionEnum.DECLARED.ordinal());
	}

	public void setDeclaredCondition(CargoCondition declaredCondition) {
		conditions.set(ConditionEnum.DECLARED.ordinal(), declaredCondition);
	}

	public CargoCondition getActualCondition() {
		return conditions.get(ConditionEnum.ACTUAL.ordinal());
	}

	public void setActualCondition(CargoCondition actualCondition) {
		conditions.set(ConditionEnum.ACTUAL.ordinal(), actualCondition);
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<CargoArticleFeature> getFeatures() {
		return new ArrayList <CargoArticleFeature>(features);
	}

	public void setFeatures(List<CargoArticleFeature> features) {		
		this.features.clear();
		this.features.addAll(features);
	}
   
	@Override
	public String toString(){		
		return article != null ? article.getName() : "";
	}
}
