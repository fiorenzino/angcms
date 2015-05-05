package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.lang.Language;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import javax.ws.rs.core.GenericType;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@RunWith(OrderedRunner.class)
public class LanguageTests
{

   static final String TARGET_HOST = "http://localhost:8080/";
   static String[] languages = new String[] { "ITA", "ENG", "FRA" };

   static
   {
      try
      {
         TrustManager[] trustAllCerts = { new X509TrustManager()
         {
            public X509Certificate[] getAcceptedIssuers()
            {
               return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                     String authType)
            {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                     String authType)
            {
            }
         } };
         SSLContext sc = SSLContext.getInstance("SSL");

         HostnameVerifier hv = new HostnameVerifier()
         {
            public boolean verify(String arg0, SSLSession arg1)
            {
               return true;
            }
         };
         sc.init(null, trustAllCerts, new SecureRandom());

         HttpsURLConnection
                  .setDefaultSSLSocketFactory(sc.getSocketFactory());
         HttpsURLConnection.setDefaultHostnameVerifier(hv);
      }
      catch (Exception localException)
      {
      }
   }

   @Test
   @Order(order = 1)
   public void create()
   {
      try
      {
         for (String name : languages)
         {
            Language language = TestUtils.newLanguage(name);
            CrudTests crudTests = new CrudTests();
            language = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                     + AppConstants.LANGUAGE_PATH, language);
            Assert.assertNotNull(language.getName());
         }

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 2)
   public void exist()
   {
      try
      {
         for (String name : languages)
         {
            CrudTests crudTests = new CrudTests();
            crudTests.exists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                     + AppConstants.LANGUAGE_PATH, name);
         }

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 3)
   public void list()
   {
      try
      {
         CrudTests crudTests = new CrudTests();
         GenericType<List<Language>> genericType = new GenericType<List<Language>>()
         {
         };
         List<Language> langList = crudTests.list(TARGET_HOST,
                  AppConstants.API_PATH + AppConstants.BASE_PATH + AppConstants.LANGUAGE_PATH,
                  "name asc", genericType);
         if (langList == null)
         {
            Assert.fail("empty list");
            return;
         }
         Assert.assertEquals(languages.length, langList.size());

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 4)
   public void delete()
   {
      try
      {
         for (String name : languages)
         {
            CrudTests crudTests = new CrudTests();
            crudTests.delete(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                     + AppConstants.LANGUAGE_PATH, name);
            crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                     + AppConstants.LANGUAGE_PATH, name);
         }

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

}
