package org.giavacms.web.util;

import org.giavacms.api.model.Search;
import org.giavacms.api.repository.Repository;
import org.jboss.logging.Logger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.*;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class JSFUtils
{

   static Logger logger = Logger.getLogger(JSFUtils.class.getName());

   public static String getCurrentPage()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         return httpRequest.getRequestURI();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getContextPath()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         String cp = fc.getExternalContext().getRequestContextPath();
         return cp;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getAbsolutePath()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpServletRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         String scheme = httpServletRequest.getScheme();
         String hostName = httpServletRequest.getServerName();
         int port = httpServletRequest.getServerPort();
         // Because this is rendered in a <div> layer, portlets for some
         // reason
         // need the scheme://hostname:port part of the URL prepended.
         return scheme + "://" + hostName + ":" + port + getContextPath();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static int count(Collection collection)
   {
      return collection == null ? 0 : collection.size();
   }

   /**
    * @param ricerca
    * @param ejb
    * @param idField il nome del campo del par il cui valore è da usare come selectItem.value
    * @param valueField il nome del campo del par il cui valore è da usare selectItem.label
    * @param emptyMessage messaggio da mettere in caso di no risultati: selectItem(null,"nessun entity trovato...")
    * @param labelMessage messaggio da mettere nel primo selectitem in caso di no-selezione:
    *           select(null,"scegli l'entity....")
    * @return
    */
   public static SelectItem[] setupItems(Search ricerca, Repository ejb,
            String idField, String valueField, String emptyMessage,
            String labelMessage)
   {
      Class clazz = ricerca.getObj().getClass();
      SelectItem[] selectItems = new SelectItem[1];
      selectItems[0] = new SelectItem(null, emptyMessage);
      List entities = null;
      try
      {
         entities = ejb.getList(ricerca, 0, 0);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      if (entities != null && entities.size() > 0)
      {
         int i = 0;
         if (labelMessage != null)
         {
            selectItems = new SelectItem[entities.size() + 1];
            selectItems[0] = new SelectItem(null, labelMessage);
            i++;
         }
         else
         {
            selectItems = new SelectItem[entities.size()];
         }
         for (Object o : entities)
         {
            try
            {
               Field ID_Field = clazz.getDeclaredField(idField);
               ID_Field.setAccessible(true);
               Field VALUE_Field = clazz.getDeclaredField(valueField);
               VALUE_Field.setAccessible(true);
               selectItems[i] = new SelectItem(ID_Field.get(o), ""
                        + VALUE_Field.get(o));
               i++;
            }
            catch (IllegalArgumentException e)
            {
               logger.info(e.getMessage());
            }
            catch (IllegalAccessException e)
            {
               logger.info(e.getMessage());
            }
            catch (SecurityException e)
            {
               logger.info(e.getMessage());
            }
            catch (NoSuchFieldException e)
            {
               logger.info(e.getMessage());
            }
         }
      }
      return selectItems;
   }

   public static Object getManagedBean(String name)
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         if (fc == null)
         {
            logger.info("Faces Context Application NULLO");
            return null;
         }
         return fc.getApplication().getELResolver()
                  .getValue(fc.getELContext(), null, name);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static void redirect(String nameUrl) throws IOException
   {
      try
      {
         String url = getAbsolutePath() + nameUrl;
         FacesContext context = FacesContext.getCurrentInstance();
         try
         {
            context.getExternalContext().redirect(url);
            context.responseComplete();
         }
         catch (Exception e)
         {
            logger.info(e.getMessage());
         }
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
      }
   }

   public static Map getMap(String mapName, FacesContext fc)
   {
      // return (HashMap) fc.getApplication().getVariableResolver()
      // .resolveVariable(fc, mapName);
      try
      {
         return (HashMap) fc.getApplication().getELResolver()
                  .getValue(fc.getELContext(), null, mapName);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static List getArray(String name, FacesContext fc)
   {
      // return (java.util.ArrayList)
      // fc.getApplication().getVariableResolver()
      // .resolveVariable(fc, name);
      try
      {
         return (ArrayList) fc.getApplication().getELResolver()
                  .getValue(fc.getELContext(), null, name);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static Object getParameter(String name)
   {
      try
      {
         FacesContext context = FacesContext.getCurrentInstance();
         return context.getExternalContext().getRequestParameterMap()
                  .get(name);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getRemoteAddr()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         return httpRequest.getRemoteAddr();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   /**
    * Return the username from the context principal
    *
    * @return the username or null if the principal is null
    */
   public static String getUserName()
   {
      try
      {
         FacesContext context = FacesContext.getCurrentInstance();
         HttpServletRequest req = (HttpServletRequest) context
                  .getExternalContext().getRequest();
         // String rem = req.getRemoteUser();
         // System.out.println("******************************");
         // System.out.println("REM USER: " + rem);
         Principal pr = req.getUserPrincipal();
         // System.out.println("PRINC USER: " + pr.getName());
         // System.out.println("******************************");

         if (pr == null)
         {
            return null;
         }
         return pr.getName();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getHostPort()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpServletRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         String scheme = httpServletRequest.getScheme();
         String hostName = httpServletRequest.getServerName();
         int port = httpServletRequest.getServerPort();

         return port == 80 ? (scheme + "://" + hostName + "/") : (scheme + "://" + hostName + ":" + port + "/");
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getHostPortPath()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpServletRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         String scheme = httpServletRequest.getScheme();
         String hostName = httpServletRequest.getServerName();
         int port = httpServletRequest.getServerPort();

         return (port == 80 ? (scheme + "://" + hostName + "/") : (scheme + "://" + hostName + ":" + port + "/"))
                  + (httpServletRequest.getContextPath() == null ? ""
                           : httpServletRequest.getContextPath().length() == 0 ? "" : httpServletRequest
                                    .getContextPath().substring(1));
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String breadcrumbs()
   {
      try
      {
         HttpServletRequest hsr = (HttpServletRequest) FacesContext
                  .getCurrentInstance().getExternalContext().getRequest();
         String url = hsr.getRequestURL().toString();
         url = url.substring("http://".length());
         if (url.indexOf("/") >= 0)
            url = url.substring(url.indexOf("/") + 1);
         String[] crumbs = url.split("/");

         String base = "/";
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < crumbs.length; i++)
         {
            base += crumbs[i];
            String label = i == 0 ? "home" : crumbs[i];
            if (label.contains("."))
            {
               label = label.substring(0, label.indexOf("."));
               sb.append("<b>" + label + "</b>");
            }
            else
            {
               sb.append("<a href=\"" + base + "\" title=\"" + crumbs[i]
                        + "\">" + label + "</a> ");
               sb.append("<span style=\"color: black;\">&gt;</span> ");
            }
            base += "/";
         }
         return sb.toString();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String shorten(String in, int max)
   {
      try
      {
         if (in == null)
            return "";
         if (in.length() < max)
            return in;
         return in.substring(0, max) + "...";
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return "";
      }
   }

   /**
    * @param role
    * @return
    */
   public static boolean isUserInRole(String role)
   {
      try
      {
         FacesContext context = FacesContext.getCurrentInstance();
         HttpServletRequest req = (HttpServletRequest) context
                  .getExternalContext().getRequest();
         return req.isUserInRole(role);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return false;
      }
   }

   public static String getWebRootPath(Class clazz)
   {
      try
      {
         String webRoot_WEBINF = getWebInfPath(clazz);
         String webRoot = webRoot_WEBINF.substring(0,
                  webRoot_WEBINF.lastIndexOf("/"));
         return webRoot;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getWebInfLibPath(Class clazz)
   {
      try
      {
         String webRoot_WEBINF = getWebInfPath(clazz);
         return webRoot_WEBINF + "/lib";
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getWebInfPath(Class clazz)
   {
      try
      {
         String webRoot_WEBINF_classes_it_slash = clazz.getClassLoader()
                  .getResource("it").getPath()
                  .replaceAll("%5c", File.separator);
         String webRoot_WEBINF_classes_it = webRoot_WEBINF_classes_it_slash
                  .substring(0,
                           webRoot_WEBINF_classes_it_slash.lastIndexOf("/"));
         String webRoot_WEBINF_classes = webRoot_WEBINF_classes_it
                  .substring(0, webRoot_WEBINF_classes_it.lastIndexOf("/"));
         String webRoot_WEBINF = webRoot_WEBINF_classes.substring(0,
                  webRoot_WEBINF_classes.lastIndexOf("/"));
         return webRoot_WEBINF;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getContextParam(String name)
   {
      try
      {
         return ((ServletContext) FacesContext.getCurrentInstance()
                  .getExternalContext().getContext()).getInitParameter(name);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return null;
      }
   }

   public static String getSessionId()
   {
      try
      {
         FacesContext fc = FacesContext.getCurrentInstance();
         HttpServletRequest httpRequest = (HttpServletRequest) fc
                  .getExternalContext().getRequest();
         return httpRequest.getSession().getId();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return "";
      }
   }

   /**
    * Identifica se una stringa contiene una espressione EL
    *
    * @param elExpression
    * @return
    */
   public static boolean isElExpression(String elExpression)
   {
      if (elExpression != null && elExpression.trim().startsWith("#{") && elExpression.trim().endsWith("}"))
         return true;
      return false;
   }

   /**
    * Valuta e risolve una espressione EL
    *
    * @param elExpression l'espressione el da valutare
    * @return il risultato della valutazione
    * @throws in caso di errori
    */
   public static String resolveElExpression(String elExpression, FacesContext facesContext) throws Exception
   {
      Application app = facesContext.getApplication();
      ELContext elContext = facesContext.getELContext();
      logger.info("BEFORE: " + elExpression);
      ExpressionFactory exprFactory = app.getExpressionFactory();
      ValueExpression valExpr = exprFactory.createValueExpression(
               elContext, elExpression, Object.class);
      String resolved = (String) valExpr.getValue(elContext);
      logger.info("AFTER: " + resolved);
      return resolved;
   }


}
