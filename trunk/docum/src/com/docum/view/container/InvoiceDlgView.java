package com.docum.view.container;

import java.io.Serializable;

import com.docum.domain.po.common.Invoice;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;

public class InvoiceDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -8167987929961469160L;
	private Invoice invoice;
	
	public InvoiceDlgView(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	public void setCalibre(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);		
	}
	
	public String getTitle() {		
		return invoice != null ? 
				invoice.getId() == null ? "Добавление инвойса" : "Правка инвойса" : "";
		
	}

}
