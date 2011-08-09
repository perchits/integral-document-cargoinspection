package com.docum.util;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.docum.domain.po.OrderedEntity;

public class OrderedEntityUtil {
	public static <T extends OrderedEntity> void add(T entity, List<T> list) {
		entity.setOrd(list.size());
		list.add(entity);
	}
	public static <T extends OrderedEntity> boolean remove(T entity, List<T> list) {
		boolean result = false;
		int index = list.indexOf(entity);
		int ord = index;
		if(index >= 0) {
			list.remove(index);
			ListIterator<T> it = list.listIterator(index);
			while(it.hasNext()) {
				it.next().setOrd(ord++);
			}
			result = true;
		}
		return result;
	}
	public static <T extends OrderedEntity> void setOrd(T entity, List<T> list, int ord) {
		if(ord >= 0 && ord < list.size()) {
			if(list.remove(entity)) {
				list.add(ord, entity);
				ord = 0;
				for(T e : list) {
					e.setOrd(ord++);
				}
			}		
		}
	}
	public static <T extends OrderedEntity> void moveUp(T entity, List<T> list) {
		setOrd(entity, list, entity.getOrd()-1);
	}
	public static <T extends OrderedEntity> void moveDown(T entity, List<T> list) {
		setOrd(entity, list, entity.getOrd()+1);
	}

	public static <T> void moveUp(T entity, List<T> list) {
		int index = list.indexOf(entity);
		if(index > 0 && index < list.size()) {
			Collections.swap(list, index, index-1);
		}
	}
	public static <T> void moveDown(T entity, List<T> list) {
		int index = list.indexOf(entity);
		if(index >= 0 && index < list.size()-1) {
			Collections.swap(list, index, index+1);
		}
	}
}