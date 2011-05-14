package com.docum.util;

import org.apache.log4j.Logger;

public class DocumLogger {

	private static final Logger logger = Logger.getLogger(DocumLogger.class); 
	
	private DocumLogger() {
		
	}
	
	public static void log(Exception exception) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(exception.toString()).append("\n");
		StackTraceElement[] trace = exception.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			sb.append(trace[i].toString()).append("\n");
		}
		logger.error(exception);
		throw new Exception (sb.toString());
	}
	
	public static void log(String message) {
		logger.info(message);
	}
}
