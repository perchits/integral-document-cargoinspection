package com.docum.util;

public class EqualsHelper
{
    public static boolean equals(Object o1, Object o2)
    {
        if(o1 == null || o2 == null)
        	return false;
        else
        	return o1.equals(o2);
    }
}
