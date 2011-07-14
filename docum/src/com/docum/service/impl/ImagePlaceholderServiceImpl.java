package com.docum.service.impl;

import com.docum.service.ImagePlaceholderService;

public class ImagePlaceholderServiceImpl implements ImagePlaceholderService {

	private String location;
	
	@Override
	public void saveImage(String saveLocation, String fileName) {
		
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
