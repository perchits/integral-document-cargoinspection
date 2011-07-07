package com.docum.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.util.DocumLogger;

@Controller("imagePlaceholderBean")
@Scope("session")
public class ImagePlaceholderView {
	
	public void handleFileUpload(FileUploadEvent event) {  
		DocumLogger.log("Uploaded: " + event.getFile().getFileName());  
  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}
