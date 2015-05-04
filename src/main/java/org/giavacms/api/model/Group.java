package org.giavacms.api.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by fiorenzo on 30/04/15.
 */
@XmlRootElement
public class Group<T> implements Serializable
{

   private static final long serialVersionUID = 1L;

   private T obj;

   private Long count;
   private Long max;

   private String name;

   public Group()
   {
   }

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

   @JsonIgnore
   @XmlTransient
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

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Double getRatio()
   {
      return count == null ? 0D : max == null ? 0D : max == 0 ? 0D : new Double(count) / new Double(max);
   }

   @Override
   public String toString()
   {
      return "Group [count=" + count + ", max=" + max + ", name=" + name + "]";
   }

}