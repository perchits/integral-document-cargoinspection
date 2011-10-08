package com.docum.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListHandler {
	private final static String NO_DATA = "No data/Нет данных";
	
	public static String join(Collection<?> list, String delim) {
		if (!list.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			String loopDelim = "";
			for (Object s : list) {
				if (sb.indexOf(s.toString()) == -1) {
					sb.append(loopDelim);
					sb.append(s.toString());
					loopDelim = delim;
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	public static <T> void sublist(Collection<T> src, Collection<T> sub) {
		if (sub != null && src != null) {
			for (T o : sub) {
				src.remove(o);
			}
		}
	}
	
	public static <T extends Object>  String getUniqueResult(List<T> data) {
		if (data == null | data.size() == 0) {
			return NO_DATA;
		} else if (data.size() == 1) {
			return data.get(0) != null ? data.get(0).toString() : NO_DATA;
		} else {
			StringBuffer result = new StringBuffer();
			Set<T> uniqueObjects = new HashSet<T>();
			for (T object: data) {
				uniqueObjects.add(object);
			}
			if (uniqueObjects.size() == 1) {
				return uniqueObjects.iterator().next().toString();
			} else {
				for (Object object: uniqueObjects) {
					result.append(object).append(", ");
				}
				int length = result.length();
				result.replace(length - 2, length - 1 , "");
				
				return result.toString();
			}
		}
	}
}