package com.docum.view;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.docum.domain.po.common.City;
import com.docum.service.BaseService;

@Controller("testView")
@Scope("session")
public class TestView implements Serializable{
	private static final long serialVersionUID = 5827007837538386824L;
	@Autowired
	BaseService baseService;

	private UploadedFile uploadedFile;
	
	public Object save() {
		City city = new City("Питер", "Piter", false);
		baseService.save(city);
		return "test";
	}

	public Object go() {
		City city = baseService.getObject(City.class, 1L);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("city", city);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("city1", getCity());
		return "test1?faces-redirect=true";
	}
	
	public City getCity() {
		return baseService.getObject(City.class, 2L);
	}
	
	public void handleFileUpload() throws IOException {  
        String fileName = FilenameUtils.getName(uploadedFile.getName());
        String contentType = uploadedFile.getContentType();
        byte[] bytes = uploadedFile.getBytes();

        // Now you can save bytes in DB (and also content type?)

        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(String.format("File '%s' of type '%s' successfully uploaded!", fileName, contentType)));
    }  	

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
