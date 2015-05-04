package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.type.RichContentType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import javax.ws.rs.core.GenericType;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@RunWith(OrderedRunner.class)
public class RichContentTests
{

   static final String TARGET_HOST = "http://localhost:8080/";
   static String id = "1";
   static String richContentTypeId = "1";

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
         CrudTests crudTests = new CrudTests();
         richContentType = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_TYPE_PATH, richContentType);

         richContentTypeId = richContentType.getId().toString();
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
      try
      {
         RichContent RichContent = TestUtils.newRichContent(richContentTypeId);
         CrudTests crudTests = new CrudTests();
         RichContent = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, RichContent);
         Assert.assertNotNull(RichContent.getId());
         id = RichContent.getId().toString();
         Assert.assertNotNull(id);
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
         CrudTests crudTests = new CrudTests();
         crudTests.exists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, id);
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
         GenericType<List<RichContent>> genericType = new GenericType<List<RichContent>>()
         {
         };
         List<RichContent> bannerList = crudTests.list(TARGET_HOST,
                  AppConstants.API_PATH + AppConstants.BASE_PATH + AppConstants.RICHCONTENT_PATH,
                  "name asc", genericType);
         if (bannerList == null)
         {
            Assert.fail("empty list");
            return;
         }
      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 4)
   public void update()
   {
      try
      {
         CrudTests crudTests = new CrudTests();
         RichContent bannerTypology = crudTests.get(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, id, RichContent.class);
         Assert.assertNotNull(bannerTypology);
         Assert.assertEquals(id, bannerTypology.getId().toString());

         String newName = "Nuovo Nome";
         bannerTypology.setTitle(newName);
         crudTests.update(id, TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, bannerTypology);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 5)
   public void delete()
   {
      try
      {
         CrudTests crudTests = new CrudTests();
         crudTests.delete(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, id);
         crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, id);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 6)
   public void notExist()
   {
      try
      {
         CrudTests crudTests = new CrudTests();
         crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.RICHCONTENT_PATH, id);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

}
