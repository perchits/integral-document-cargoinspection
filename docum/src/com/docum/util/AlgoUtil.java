/**
 * 
 */
package com.docum.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author pepper
 * 
 */
public class AlgoUtil
{
    public static abstract class CopyFunctor<T> implements TransformFunctor<T, T>
    {
        @Override
        public T transform(T from)
        {
            return copy(from);
        }
        public abstract T copy(T from);
    }
    public static <T> Collection<T> copy(Collection<T> dst,
            Collection<T> src, CopyFunctor<T> functor)
    {
        return transform(dst, src, functor);
    }

    public static interface TransformFunctor<ToT, FromT>
    {
        public ToT transform(FromT from);
    }

    public static <ToT, FromT> Collection<ToT> transform(Collection<ToT> dst,
            Collection<FromT> src, TransformFunctor<ToT, FromT> functor)
    {
        dst.clear();
        if(dst instanceof ArrayList)
            ((ArrayList<ToT>) dst).ensureCapacity(src.size());
        for(FromT from : src)
            dst.add(functor.transform(from));
        return dst;
    }

    public static <ToKeyT, ToValueT, FromT> Map<ToKeyT, ToValueT> transform(
            Map<ToKeyT, ToValueT> dst, Collection<FromT> src,
            TransformFunctor<Map.Entry<ToKeyT, ToValueT>, FromT> functor)
    {
        dst.clear();
        for(FromT from : src)
        {
            Map.Entry<ToKeyT, ToValueT> entry = functor.transform(from);
            dst.put(entry.getKey(), entry.getValue());
        }
        return dst;
    }

    public static <ToT, FromKeyT, FromValueT> Collection<ToT> transform(Collection<ToT> dst,
            Map<FromKeyT, FromValueT> src,
            TransformFunctor<ToT, Map.Entry<FromKeyT, FromValueT>> functor)
    {
        dst.clear();
        if(dst instanceof ArrayList)
            ((ArrayList<ToT>) dst).ensureCapacity(src.size());
        for(Map.Entry<FromKeyT, FromValueT> from : src.entrySet())
            dst.add(functor.transform(from));
        return dst;
    }

    @SuppressWarnings("unchecked")
    public static <ToT, FromT> ToT[] transform(FromT[] src, TransformFunctor<ToT, FromT> functor)
    {
      ArrayList<ToT> dst = new ArrayList<ToT>(src.length);
      return (ToT[])transform(dst, Arrays.asList(src), functor).toArray();
    }

    public static class MapEntry<KeyT, ValueT> implements Map.Entry<KeyT, ValueT>
    {
        public MapEntry(KeyT key, ValueT value)
        {
            this.key = key;
            this.value = value;
        }

        public KeyT getKey()
        {
            return key;
        }

        public ValueT getValue()
        {
            return value;
        }

        public ValueT setValue(ValueT value)
        {
            ValueT oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        private KeyT key;
        private ValueT value;
    }

    public static interface ApplyFunctor<T>
    {
        public void apply(T o);
    }

    public static <T> void forEach(Collection<T> c, ApplyFunctor<T> f)
    {
        for(T o : c)
        {
            f.apply(o);
        }
    }

    public static <T> void forEach(T[] c, ApplyFunctor<T> f)
    {
        for(T o : c)
        {
            f.apply(o);
        }
    }

    public static <T> int indexOf(Collection<T> c, T o)
    {
        if(o == null)
        {
            return -1;
        }
        else if(c instanceof List)
        {
            return ((List<T>) c).indexOf(o);
        }
        else
        {
            int i = 0;
            for(T obj : c)
            {
                if(o.equals(obj))
                {
                    return i;
                }
                i++;
            }
            return -1;
        }
    }

    public static <T> int indexOf(T[] c, T o)
    {
        if(o == null)
        {
            return -1;
        }
        else
        {
            int i = 0;
            for(T obj : c)
            {
                if(o.equals(obj))
                {
                    return i;
                }
                i++;
            }
            return -1;
        }
    }

    public static interface FindPredicate<T>
    {
        public boolean isIt(T o);
    }

    public static <T> T find(Collection<T> c, FindPredicate<T> f)
    {
        for(T o : c)
        {
            if(f.isIt(o))
            {
                return o;
            }
        }
        return null;
    }

    public static <T> T find(T[] c, FindPredicate<T> f)
    {
        for(T o : c)
        {
            if(f.isIt(o))
            {
                return o;
            }
        }
        return null;
    }

    public static <T> Collection<T> collect(Collection<T> dst, Collection<T> src, FindPredicate<T> f)
    {
        if(dst instanceof ArrayList)
            ((ArrayList<T>) dst).ensureCapacity(src.size());
        for(T o : src)
        {
            if(f.isIt(o))
            {
                dst.add(o);
            }
        }
        return dst;
    }

    public static <T> Collection<T> collect(Collection<T> dst, T[] src, FindPredicate<T> f)
    {
        if(dst instanceof ArrayList)
            ((ArrayList<T>) dst).ensureCapacity(src.length);
        for(T o : src)
        {
            if(f.isIt(o))
            {
                dst.add(o);
            }
        }
        return dst;
    }
    
    public static interface AggregateFunctor<AggregatorT, AggregateeT, AggregationMarkerT>
    {
        public AggregationMarkerT getAggregationMarker(AggregateeT from);
        public AggregatorT createAggregator(AggregationMarkerT marker);
        public void addAggregation(AggregatorT aggregator, AggregateeT aggregatee);
    }

    public static <AggregatorT, AggregateeT, AggregationMarkerT>
    Collection<AggregatorT> aggregate(Collection<AggregatorT> dst,
            Collection<AggregateeT> src,
            AggregateFunctor<AggregatorT, AggregateeT, AggregationMarkerT> functor)
    {
        dst.clear();
        Set<AggregationMarkerT> markers = new HashSet<AggregationMarkerT>(); 
        for(AggregateeT from : src)
            markers.add(functor.getAggregationMarker(from));
        if(dst instanceof ArrayList)
            ((ArrayList<AggregatorT>) dst).ensureCapacity(markers.size());
        for(AggregationMarkerT marker : markers)
        {
            AggregatorT aggregator = functor.createAggregator(marker);
            for(AggregateeT aggregatee : src)
            {
                if(functor.getAggregationMarker(aggregatee).equals(marker))
                    functor.addAggregation(aggregator, aggregatee);
            }
            dst.add(aggregator);
        }
        return dst;
    }
    public static <T> Collection<T> filter(Collection<T> dst, Collection<T> src, FindPredicate<T> f)
    {
        dst.clear();
        for(T s : src)
            if(f.isIt(s))
                dst.add(s);
        return dst;
    }
}
