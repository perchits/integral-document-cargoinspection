package com.docum.exception;

import java.io.UnsupportedEncodingException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class ContentTypePhaseListener implements PhaseListener {

	private static final long serialVersionUID = -250445046380678917L;

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		try {			
			fc.getExternalContext().setRequestCharacterEncoding("UTF-8");
			fc.getExternalContext().setResponseContentType(
					"text/html; charset=UTF-8");			
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

	}
}
