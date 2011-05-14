package com.docum.util;

import org.apache.log4j.Logger;

public class DocumLogger {

	private static final Logger logger = Logger.getLogger(DocumLogger.class); 
	
	private DocumLogger() {
		
	}
	
	public static void log(Throwable throwable) {
		if (throwable == null) {
			logger.error("Empty exception was thrown!");
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(throwable.toString()).append("\n");
		StackTraceElement[] trace = throwable.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			sb.append(trace[i].toString()).append("\n");
		}
		logger.error(sb.toString());
	}
	
	public static void log(String message) {
		logger.info(message);
	}
}
