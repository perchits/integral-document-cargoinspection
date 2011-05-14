package com.docum.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.docum.util.DocumLogger;

public class DocumExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public DocumExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			FacesContext fc = FacesContext.getCurrentInstance();
			try {
				if (t.getCause() != null) {
					fc.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Ошибочка вышла...", t.getCause().getMessage()));
				}
				fc.renderResponse();
				DocumLogger.log(t);
				// redirect to error view etc....				
			} finally {
				// after exception is handeled, remove it from queue
				i.remove();
			}
		}
		getWrapped().handle();
	}
}
