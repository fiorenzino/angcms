package org.giavacms.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

public class FacesMessageUtils
{

   static Logger logger = Logger.getLogger(FacesMessageUtils.class.getName());

   /**
    * Verifica se l'elemento di cui è stato passato l'id come parametro ha degli errori di validazione.
    * 
    * @param clientId Id dell'elemento di cui verificare se ha degli errori di validazione.
    * 
    * @return Ritorne vero se e solo se l'elemento ha degli errori di validazione.
    * 
    */
   public static Boolean hasErrors(String clientId)
   {
      try
      {
         return FacesContext.getCurrentInstance().getMessages(clientId)
                  .hasNext();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return true;
      }
   }

   /**
    * Verifica se l'elemento il cui id è composto nel seguente modo: 'formId:elementId' ha degli errori di validazione.
    * 
    * @param elementId Id dell'elemento di cui verificare se ha degli errori di validazione.
    * 
    * @param formId Id del form che contiene l'elemento.
    * 
    * @return Ritorne vero se e solo se l'elemento ha degli errori di validazione.
    * 
    */
   public static Boolean hasErrors(String elementId, String formId)
   {
      return hasErrors(formId + ":" + elementId);
   }

   /**
    * Ritorna gli errori per un certo clientId
    * 
    * @param clientId
    * @return
    */
   public static List<FacesMessage> getErrors(String clientId)
   {
      List<FacesMessage> messages = new ArrayList<FacesMessage>();
      try
      {
         for (Iterator<FacesMessage> iterator = FacesContext
                  .getCurrentInstance().getMessages(clientId); iterator
                  .hasNext();)
         {
            messages.add(iterator.next());

         }
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
      }
      return messages;
   }

   public static String getErrorMessage(String clientId)
   {
      try
      {

         Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance()
                  .getMessages(clientId);

         if (!iterator.hasNext())
         {
            return "";
         }

         StringBuffer sb = new StringBuffer();

         for (; iterator.hasNext();)
         {
            sb.append(iterator.next().getDetail());
            if (iterator.hasNext())
            {
               sb.append(", ");
            }
         }

         return sb.toString();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         return "";
      }
   }

   public static void addFacesMessage(String summary, String message)
   {
      addFacesMessage(null, summary, message, "");
   }

   /**
    * dettaglio come il body; severity error; messaggio nei messaggi di pagina generali
    * 
    * @param summary
    */
   public static void addFacesMessage(String summary)
   {
      addFacesMessage(null, summary, summary, "");
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

   /**
    * @param clientId
    * @return
    */
   public static boolean renderErrorMessageForComponent(String clientId)
   {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (fc != null)
      {
         UIComponent target = fc.getViewRoot().findComponent(clientId);
         if (target != null)
         {
            for (Iterator<FacesMessage> msgs = fc.getMessages(target.getClientId(fc)); msgs.hasNext();)
            {
               FacesMessage msg = msgs.next();
               if (msg.getSeverity() == null || !msg.getSeverity().equals(FacesMessage.SEVERITY_ERROR))
               {
                  continue;
               }
               return true;
            }
         }
      }
      return false;
   }

   /**
    * @param clientId
    * @return
    */
   public static String getErrorMessageForComponent(String clientId)
   {
      FacesContext fc = FacesContext.getCurrentInstance();
      if (fc != null)
      {
         UIComponent target = fc.getViewRoot().findComponent(clientId);
         if (target != null)
         {
            for (Iterator<FacesMessage> msgs = fc.getMessages(target.getClientId(fc)); msgs.hasNext();)
            {
               FacesMessage msg = msgs.next();
               if (msg.getSeverity() == null || !msg.getSeverity().equals(FacesMessage.SEVERITY_ERROR))
               {
                  continue;
               }
               // if (msg.isRendered()) {
               // return null;
               // }
               // msg.rendered();
               return msg.getDetail();
            }
         }
      }
      return null;
   }

   // --------------------------------------------------------------------------
   // Varie
   // --------------------------------------------------------------------------

   /**
    * @param message
    * @param M_GROWL
    */
   public static void addGrowlErrorMessage(String message, String M_GROWL)
   {
      addFacesMessage(FacesMessage.SEVERITY_ERROR, message, message, M_GROWL);
   }

   /**
    * @param message
    * @param M_GROWL
    */
   public static void addGrowlWarnMessage(String message, String M_GROWL)
   {
      addFacesMessage(FacesMessage.SEVERITY_WARN, message, message, M_GROWL);
   }

   /**
    * @param message
    * @param M_GROWL
    */
   public static void addGrowlInfoMessage(String message, String M_GROWL)
   {
      addFacesMessage(FacesMessage.SEVERITY_INFO, message, message, M_GROWL);
   }

   /**
    * @param message
    * @param M_MESSAGES
    */
   public static void addMessagesErrorMessage(String message, String M_MESSAGES)
   {
      addFacesMessage(FacesMessage.SEVERITY_ERROR, message, message, M_MESSAGES);
   }

   /**
    * @param message
    * @param M_MESSAGES
    */
   public static void addMessagesWarnMessage(String message, String M_MESSAGES)
   {
      addFacesMessage(FacesMessage.SEVERITY_WARN, message, message, M_MESSAGES);
   }

   /**
    * @param message
    * @param M_MESSAGES
    */
   public static void addMessagesInfoMessage(String message, String M_MESSAGES)
   {
      addFacesMessage(FacesMessage.SEVERITY_INFO, message, message, M_MESSAGES);
   }

   /**
    * @param severity nulla = error
    * @param forComponentId nullo va nei global messages di pagina
    */
   public static void addFacesMessage(Severity severity, String summary,
            String message, String forComponentId)
   {
      FacesMessage fm = new FacesMessage(message);
      fm.setSummary(summary);
      if (severity != null)
      {
         fm.setSeverity(severity);
      }
      else
      {
         fm.setSeverity(FacesMessage.SEVERITY_ERROR);
      }
      FacesContext.getCurrentInstance().addMessage(forComponentId, fm);
   }

}
