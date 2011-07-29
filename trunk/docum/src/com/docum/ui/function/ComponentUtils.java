package com.docum.ui.function;

public class ComponentUtils {
	public static String truncateString(String str, Integer maxLen, String ellipse) {
		int ellipseLen = ellipse == null ? 0 : ellipse.length();
		if(str == null || maxLen == null || str.length() <= maxLen || maxLen <= ellipseLen) {
			return str;
		}
		return str.substring(0, maxLen - ellipseLen) + (ellipse != null ? ellipse : "");
	}
}
