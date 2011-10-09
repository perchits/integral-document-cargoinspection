package com.docum.view.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

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
	
	public FileListDlgView(List<FileUrl> fileUrls, String title, 
			FileProcessingService fileService, Container container) {
		this.fileUrls = fileUrls;
		this.title = title;
		this.fileService = fileService;
		this.container = container;
	}
	
	public List<FileUrl> getFileUrls() {
		return fileUrls;
	}
	
	public void setFileUrls(List<FileUrl> fileUrls) {
		this.fileUrls = fileUrls;		
	}
	
	public void moveUp() {
		OrderedEntityUtil.moveUp(fileUrl, fileUrls);
	}
	
	public void moveDown() {
		OrderedEntityUtil.moveDown(fileUrl, fileUrls);
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
		
	}

}
