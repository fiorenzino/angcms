package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.lang.LanguageMapping;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@RunWith(OrderedRunner.class)
public class LanguageMappingTests
{

   static final String TARGET_HOST = "http://localhost:8080/";
   static String[] languages = new String[] { "ITA", "ENG", "FRA" };
   static String id = "";

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
         LanguageMapping languageMapping = TestUtils.newLanguageMapping(languages);
         CrudTests crudTests = new CrudTests();
         languageMapping = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.LANGUAGE_MAPPING_PATH, languageMapping);
         Assert.assertNotNull(languageMapping.getPageId());
         id = languageMapping.getPageId();

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 2)
   public void delete()
   {
      try
      {
         CrudTests crudTests = new CrudTests();
         crudTests.delete(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.LANGUAGE_MAPPING_PATH, id);
         crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.LANGUAGE_MAPPING_PATH, id);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

}
