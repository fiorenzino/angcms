package org.giavacms.web.producer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.model.SelectItem;

import org.giavacms.api.model.Search;
import org.giavacms.api.repository.Repository;
import org.giavacms.web.event.ResetEvent;
import org.giavacms.web.util.JSFUtils;
import org.jboss.logging.Logger;


/**
 * 
 * @param <T>
 */
public abstract class AbstractProducer implements Serializable
{

   private static final long serialVersionUID = 1L;

   // ------------------------------------------------
   // --- Logger -------------------------------------
   // ------------------------------------------------

   protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());

   @SuppressWarnings("rawtypes")
   private Map<Class, SelectItem[]> items = new HashMap<Class, SelectItem[]>();

   @SuppressWarnings("rawtypes")
   @PostConstruct
   public void reset()
   {
      logger.info("reset");
      items = new HashMap<Class, SelectItem[]>();
   }

   @SuppressWarnings("rawtypes")
   public void resetItemsForClass(Class clazz)
   {
      if (items.containsKey(clazz))
      {
         items.remove(clazz);
      }
   }

   public void observeReset(@Observes ResetEvent resetEvent)
   {
      if (resetEvent != null && resetEvent.getObservedClass() != null)
      {
         resetItemsForClass(resetEvent.getObservedClass());
      }
      else
      {
         this.reset();
      }
   }

   // public void observeRole(@Observes RoleEvent roleEvent)
   // {
   // this.reset();
   // }

   @SuppressWarnings("rawtypes")
   protected SelectItem[] checkItems(Search ricerca, Repository ejb, String idField, String valueField,
            String emptyMessage,
            String labelMessage)
   {
      SelectItem[] selectItems = items.get(ricerca.getObj().getClass());
      if (selectItems == null)
      {
         selectItems = JSFUtils.setupItems(ricerca, ejb, idField, valueField, emptyMessage, labelMessage);
         items.put(ricerca.getObj().getClass(), selectItems);
      }
      return selectItems;
   }

   @SuppressWarnings("rawtypes")
   protected Map<Class, SelectItem[]> getItems()
   {
      return items;
   }
}