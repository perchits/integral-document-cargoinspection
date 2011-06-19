package com.docum.util;

import java.util.Collection;
import java.util.List;

public class ListHandler {
	public static String join(List<?> list, String delim) {
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
}