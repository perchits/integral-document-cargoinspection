package com.docum.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.service.ApplicationConfigService;
import com.docum.service.FileProcessingService;

@Service
@Transactional
public class FileProcessingServiceImpl implements FileProcessingService {
	private static final long serialVersionUID = -5937179572456645739L;
	private static final Logger logger = Logger.getLogger(FileProcessingServiceImpl.class);
	private static final String SEP = File.separator;
	private static final String EXT_SEP = FilenameUtils.EXTENSION_SEPARATOR_STR;

	@Autowired
	ApplicationConfigService config;

	protected void saveFile(String fileName, InputStream istream) throws IOException {
		FileUtils.copyInputStreamToFile(istream, new File(fileName));
//		OutputStream ostream = null;
//		try {
//			File destFile = new File(fileName);
//			ostream = new FileOutputStream(destFile);
//			byte[] buf = new byte[65536];
//			int len;
//			while ((len = istream.read(buf)) > 0) {
//				ostream.write(buf, 0, len);
//			}
//		} finally {
//			istream.close();
//			ostream.close();
//		}
	}
	
	protected boolean createDirectory(String path) {
		File dir = new File(path);
		if(!dir.exists()) {
			return dir.mkdirs();
		}
		return false;
	}

	protected String ensureUniqueFileName(String path, String fileName) {
		File file = new File(path + SEP + fileName);
		Integer i = 0;
		String result = fileName;
		while(file.exists()) {
			result = FilenameUtils.getBaseName(fileName) + "(" + i + ")" +
				EXT_SEP + FilenameUtils.getExtension(fileName);
			file = new File(path + SEP + result);
			i++;
		}
		return result;
	}

	@Override
	public String saveImage(String path, String fileName, InputStream istream)
			throws IOException {
		path = config.getImagesStoragePath() + SEP + path;
		if(!createDirectory(path)) {
			fileName = ensureUniqueFileName(path, fileName);
		}
		String absolutFileName = path + SEP + fileName;
		saveFile(absolutFileName, istream);
		return fileName;
	}
}
