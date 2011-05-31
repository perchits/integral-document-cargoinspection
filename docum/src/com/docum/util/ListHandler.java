package com.docum.util;

import java.util.List;

public class ListHandler {
	public static String join(List<?> list, String delim) {
		if (!list.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			String loopDelim = "";
			for (Object s : list) {
				sb.append(loopDelim);
				sb.append(s.toString());
				loopDelim = delim;
			}
			return sb.toString();
		} else {
			return null;
		}
	}
}