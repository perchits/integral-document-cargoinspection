package com.docum.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class MyExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public MyExceptionHandler(ExceptionHandler wrapped) {
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
			try {
				// log error
				FacesContext fc = FacesContext.getCurrentInstance();
				if (t.getCause() != null) {
					fc.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Ошибочка вышла...", t
									.getCause().getMessage()));
				}
				fc.renderResponse();
				// redirect to error view etc....
			} finally {
				// after exception is handeled, remove it from queue
				i.remove();
			}
		}
		getWrapped().handle();

	}
}
