package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Cargo extends IdentifiedEntity {
	private static final long serialVersionUID = 4275515653210816278L;
	
	@ManyToOne(optional=false)
	private Article article;

	@ManyToOne(optional=false)
	private Supplier supplier;
	
	@ManyToOne(optional=false)
	private Container container;
	
	@OneToOne//(mappedBy="cargo")
	private CargoCondition declaredCondition;

	@OneToOne//(mappedBy="cargo")
	private CargoCondition actualCondition;
	
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
		return declaredCondition;
	}

	public void setDeclaredCondition(CargoCondition declaredCondition) {
		this.declaredCondition = declaredCondition;
	}

	public CargoCondition getActualCondition() {
		return actualCondition;
	}

	public void setActualCondition(CargoCondition actualCondition) {
		this.actualCondition = actualCondition;
	}
   
}
