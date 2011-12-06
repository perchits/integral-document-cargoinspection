package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.SelectableDataModel;

import com.docum.domain.po.common.Container;
import com.docum.domain.po.common.FileUrl;
import com.docum.service.FileProcessingService;
import com.docum.util.AlgoUtil;
import com.docum.util.OrderedEntityUtil;
import com.docum.view.AbstractDlgView;
import com.docum.view.DialogActionEnum;
import com.docum.view.FileUploadUtil;

public class FileListDlgView extends AbstractDlgView implements Serializable {
	private static final long serialVersionUID = -2922776590785148911L;
	private String title;
	private List<FileUrl> fileUrls;
	private FileUrl fileUrl;
	private FileProcessingService fileService;
	private Container container;
	private List<UrlWrapper> urlsWrapper= new ArrayList<UrlWrapper>();
	private UrlWrapper fileUrlWrapped;
	private String allowTypes = "/(\\.|\\/)(gif|jpe?g|png)$/";
	private String uploadBtnLabel = "Добавить фотографии";
		
	public UrlWrapper getFileUrlWrapped() {
		return fileUrlWrapped;
	}

	public void setFileUrlWrapped(UrlWrapper fileUrlWrapped) {
		this.fileUrlWrapped = fileUrlWrapped;
	}

	public FileListDlgView(List<FileUrl> fileUrls, String title, 
			FileProcessingService fileService, Container container) {
		this.fileUrls = fileUrls;
		this.title = title;
		this.fileService = fileService;
		this.container = container;		
	}
	
	public FileListDlgView(List<FileUrl> fileUrls, String title, 
			FileProcessingService fileService, Container container,
			String allowTypes, String uploadBtnLabel) {
		this(fileUrls, title, fileService, container);		
		this.allowTypes = allowTypes;
		this.uploadBtnLabel = uploadBtnLabel; 
	}
	
	
	public String getAllowTypes() {
		return allowTypes;
	}

	public String getUploadBtnLabel() {
		return uploadBtnLabel;
	}

	public List<FileUrl> getFileUrls() {
		return fileUrls;
	}
	
	public void setFileUrls(List<FileUrl> fileUrls) {
		this.fileUrls = fileUrls;		
	}
	
	public void moveUp() {
		if (fileUrlWrapped != null) {
			OrderedEntityUtil.moveUp(fileUrlWrapped.fileUrl, fileUrls);
		}
	}
	
	public void moveDown() {
		if (fileUrlWrapped != null) {
			OrderedEntityUtil.moveDown(fileUrlWrapped.fileUrl, fileUrls);
		}
	}
	
	public String getTitle() {						
		return title;
	}
	
	public FileUrl getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(FileUrl fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public void uploadImages(FileUploadEvent event) {
		fileUrls.add(new FileUrl(FileUploadUtil.handleUploadedFile(fileService, container , event)));
	}
	
	public UrlWrapperDataModel getUrlWrapperDataModel(){
		return new UrlWrapperDataModel(getUrlsWrapper());
	}
		
	public List<UrlWrapper> getUrlsWrapper() {
		if (fileUrls == null) return null;
		urlsWrapper.clear();
		for (FileUrl url : fileUrls){
			urlsWrapper.add(new UrlWrapper(url));
		}
		return urlsWrapper;
	}

	public void setUrlsWrapper(List<UrlWrapper> urlsWrapper) {
		this.urlsWrapper = urlsWrapper;
	}

	public void removeImage(){
		AlgoUtil.removeAll(fileUrls, new AlgoUtil.FindPredicate<FileUrl>(){
			  public boolean isIt(FileUrl c) {
			    return c.getValue().equals(fileUrl.getValue());
			 }
			});		
	}

	public void save() {
		fireAction(this, DialogActionEnum.ACCEPT);
	}
	
	public class UrlWrapper implements Serializable {
		private static final long serialVersionUID = 1542287184483688431L;
		private FileUrl fileUrl;
		
		public UrlWrapper(FileUrl fileUrl){
			this.fileUrl = fileUrl;
		}
		
		public String getThumbName(){			
			if (getValue()== null) return null;
			String fullName = fileUrl.getValue(); 
			int mid = fullName.lastIndexOf(".");
			String fname = fullName.substring(0,mid);
			String ext = fullName.substring(mid,fullName.length());
			return  fname + "_t" + ext;
		}

		public String getValue(){
			return fileUrl != null ? fileUrl.getValue() : null; 
		}
		
		public FileUrl getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(FileUrl fileUrl) {
			this.fileUrl = fileUrl;
		}
		
		public Long getId(){
			return fileUrl != null ? fileUrl.getId() : null;  
		}		
		
	}
	
	class UrlWrapperDataModel extends ListDataModel<UrlWrapper> implements SelectableDataModel<UrlWrapper> {
		
		public UrlWrapperDataModel() {  
	    }  
	  
	    public UrlWrapperDataModel(List<UrlWrapper> data) {  
	        super(data);  
	    }  
		
		@Override
		public Object getRowKey(UrlWrapper object) {
			return object.fileUrl;
		}

		@Override
		public UrlWrapper getRowData(String rowKey) {
			if (rowKey == null) return null;
			@SuppressWarnings("unchecked")
			List<UrlWrapper> urls = (List<UrlWrapper>) getWrappedData();	          
	        for(UrlWrapper url : urls) {  
	        	if(String.valueOf(url.fileUrl).equals(rowKey)) {
                    return url;
                }  
	        }  	          
	        return null;  	
		}
		
	}
}
