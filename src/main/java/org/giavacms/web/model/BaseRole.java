package org.giavacms.web.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class BaseRole implements Serializable
{

   private static final long serialVersionUID = 1L;

   private Long id;

   private String alias;
   private String group;
   private String description;
   private String type;

   // --------------------------------------------

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getAlias()
   {
      return alias;
   }

   public void setAlias(String alias)
   {
      this.alias = alias;
   }

   public String getGroup()
   {
      return group;
   }

   public void setGroup(String group)
   {
      this.group = group;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }

   @Transient
   public Object getCustomerId()
   {
      return null;
   }

}
