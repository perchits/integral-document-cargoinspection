package com.docum.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.service.ApplicationConfigService;
import com.docum.service.FileProcessingService;

@Service
@Transactional
public class FileProcessingServiceImpl implements FileProcessingService {
	private static final long serialVersionUID = -5937179572456645739L;	
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

//TODO Придумать механизм выбора опции сохранения расширения в нижнем регистре.
// Сейчас это необходимо, иначе не будет определен mime тип при загрузке файла.
// Также нужно иметь в виду, что в Windows регистр в имени файла не имеет значения.
	protected String fixFileName(String path, String fileName) {
		String base = FilenameUtils.getBaseName(fileName);
		String ext = FilenameUtils.getExtension(fileName).toLowerCase();
		String result = base + EXT_SEP + ext;
		File file = new File(path + SEP + result);
		Integer i = 0;
		while(file.exists()) {
			result = base + "(" + i + ")" + EXT_SEP + ext;
			file = new File(path + SEP + result);
			i++;
		}
		return result;
	}

	@Override
	public String saveImage(String path, String fileName, InputStream istream)
			throws IOException {
		path = config.getImagesStoragePath() + SEP + path;
		createDirectory(path);
		fileName = fixFileName(path, fileName);
		String absolutFileName = path + SEP + fileName;
		saveFile(absolutFileName, istream);
		return fileName;
	}

	@Override
	public boolean deleteImage(String fileName) {
		return new File(config.getImagesStoragePath() + SEP + fileName).delete();
	}
}
