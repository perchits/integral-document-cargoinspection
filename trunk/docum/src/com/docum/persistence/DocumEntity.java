package com.docum.persistence;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Index;

@MappedSuperclass
public class DocumEntity implements Serializable {

	private static final long serialVersionUID = 8647183098509121405L;
	
	private Long id;

	public DocumEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Index (name = "indexId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
