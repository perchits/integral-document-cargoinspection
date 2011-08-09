package com.docum.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface FileProcessingService extends Serializable {
	public String saveImage(String path, String fileName, InputStream istream)
			throws IOException;
	public boolean deleteImage(String fileName);
}
