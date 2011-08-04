package com.docum.view.wrapper;

import java.io.Serializable;

import com.docum.domain.po.common.NormativeDocument;

public class NormativeDocumentPresentation implements Serializable {
	
	private static final long serialVersionUID = -2987782777885981208L;

	private NormativeDocument normativeDocument;
	
	public NormativeDocumentPresentation(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}
	
	public String toString() {
		return getName();
	}
	
	public String getName() {		
		return normativeDocument != null ? normativeDocument.getName() : null; 
	}

	public String getEnglishName() {		
		return normativeDocument != null ? normativeDocument.getEnglishName() : null; 
	}

	public NormativeDocument getNormativeDocument() {
		return normativeDocument;
	}

	public void setNormativeDocument(NormativeDocument normativeDocument) {
		this.normativeDocument = normativeDocument;
	}
	
}
