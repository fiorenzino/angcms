package org.giavacms.api.model;

import java.io.Serializable;

/**
 * Created by fiorenzo on 30/04/15.
 */
public class Group<T> implements Serializable
{

   private static final long serialVersionUID = 1L;

   private T obj;

   private Long count;
   private Long max;

   public Group(Long count, T obj)
   {
      this.obj = obj;
      this.count = count;
   }

   public Group(Long count, T obj, Long max)
   {
      this(count, obj);
      this.max = max;
   }

   public T getObj()
   {
      return obj;
   }

   public void setObj(T obj)
   {
      this.obj = obj;
   }

   public Long getCount()
   {
      return count;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public Long getMax()
   {
      return max;
   }

   public void setMax(Long max)
   {
      this.max = max;
   }

   public Double getRatio()
   {
      return count == null ? 0D : max == null ? 0D : max == 0 ? 0D : new Double(count) / new Double(max);
   }

}