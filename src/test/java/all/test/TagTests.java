package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.type.RichContentType;
import org.giavacms.api.model.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@RunWith(OrderedRunner.class)
public class TagTests
{

   static final String TARGET_HOST = "http://localhost:8080/";
   private static final String UNO = "UNO";
   private static final String DUE = "DUE";
   private static final String TRE = "TRE";
   private static String richContentTypeId = null;
   private static String richContentTypeName = "" + System.currentTimeMillis();

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
   @Order(order = 0)
   public void createRichContentType()
   {
      try
      {
         RichContentType richContentType = TestUtils.newRichContentType();
         richContentType.setName(TagTests.richContentTypeName);
         CrudTests crudTests = new CrudTests();
         richContentType = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_TYPE_PATH, richContentType);

         TagTests.richContentTypeId = richContentType.getId().toString();
         Assert.assertNotNull(richContentTypeId);
      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 1)
   public void create()
   {
      create(UNO);
   }

   @Order(order = 2)
   public void create2()
   {
      create(UNO + RichContent.TAG_SEPARATOR + DUE);
   }

   @Order(order = 3)
   public void create3()
   {
      create(UNO + RichContent.TAG_SEPARATOR + DUE + RichContent.TAG_SEPARATOR + TRE);
   }

   private void create(String tags)
   {
      try
      {
         RichContent richContent = TestUtils.newRichContent(richContentTypeId);

         richContent.setTags(tags);
         CrudTests crudTests = new CrudTests();
         richContent = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, richContent);
         Assert.assertNotNull(richContent.getId());
         String id = richContent.getId().toString();
         Assert.assertNotNull(id);
      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 4)
   public void count()
   {
      try
      {
         //, @QueryParam("pageSize") String pageSize
         WebTarget target = CrudTests.getTarget(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.TAG_PATH + "/groups").queryParam("richContentType", richContentTypeName)
                  .queryParam("pageSize", 0)
                  .queryParam("pageSize", 10);
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         GenericType<List<Group>> genericType = new GenericType<List<Group>>()
         {
         };
         Response response = invocationBuilder.get();
         List<Group> result = response.readEntity(genericType);
         System.out.println(result);
      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }
}
