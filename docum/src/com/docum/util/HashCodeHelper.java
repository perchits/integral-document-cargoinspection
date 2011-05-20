package com.docum.util;

public class HashCodeHelper
{
    public static int hashCode(Object obj)
    {
        return obj == null ? 0 : obj.hashCode();
    }
}
