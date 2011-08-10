package com.docum.view.wrapper;

import java.io.Serializable;

import com.docum.domain.po.common.NormativeDocument;

public class NormativeDocumentPresentation implements Serializable {
	
	private static final long serialVersionUID = -2987782777885981208L;

	private NormativeDocument document;
	
	public NormativeDocumentPresentation(NormativeDocument normativeDocument) {
		this.document = normativeDocument;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {		
		return document != null ? document.getName() : null; 
	}

	public String getEnglishName() {		
		return document != null ? document.getEnglishName() : null; 
	}

	public NormativeDocument getNormativeDocument() {
		return document;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.document = normativeDocument;
	}
	
}
