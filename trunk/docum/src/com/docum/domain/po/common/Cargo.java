package com.docum.domain.po.common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.docum.domain.po.IdentifiedEntity;

@Entity
public class Cargo extends IdentifiedEntity {
	private static final long serialVersionUID = 4275515653210816278L;
	
	@ManyToOne
	private Article article;

	//Вес по коносаменту
	private Double weight;
	
	@ManyToOne
	private Supplier supplier;
	
	@ManyToOne
	private Container container;
	
	@OneToOne
	private CargoCondition declaredCondition;

	@OneToOne
	private CargoCondition actualCondition;
	
	public Cargo(){
		super();
	}
	
	public Cargo(Article article, Double weight, Supplier supplier,
			Container container) {
		super();
		this.article = article;
		this.weight = weight;
		this.supplier = supplier;
		this.container = container;
	}

	public Article getArticle() {
		return article;
	}


	public void setArticle(Article goods) {
		this.article = goods;
	}


	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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