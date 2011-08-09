package com.docum.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.stereotype.Service;

import com.docum.service.ApplicationConfigService;

@Service("applicationConfigService")
public class ApplicationConfigServiceImpl implements ApplicationConfigService {
	private static final long serialVersionUID = 4046732311830481330L;

	public static final String IMAGES_STORAGE_PATH = "images.storage.path";
	
	private static final Logger logger = Logger.getLogger(ApplicationConfigServiceImpl.class);

	private Properties props;

	@Autowired
	public ApplicationConfigServiceImpl(PropertiesFactoryBean factory) {
		try {
			this.props = factory.getObject();
		} catch (IOException e) {
			logger.fatal("Can't read application properties file!", e);
		}
	}
	
	protected String getProperty(String key) {
		return props.getProperty(key);
	}
	
	@Override
	public String getImagesStoragePath() {
		return getProperty(IMAGES_STORAGE_PATH);
	}
	
}
