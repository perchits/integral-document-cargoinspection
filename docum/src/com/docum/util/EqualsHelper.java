package com.docum.util;

public class EqualsHelper
{
    public static boolean equals(Object o1, Object o2)
    {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
