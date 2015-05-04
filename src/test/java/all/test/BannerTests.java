package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.banner.Banner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@RunWith(OrderedRunner.class)
public class BannerTests
{

   static final String TARGET_HOST = "http://localhost:8080/";
   static String id = "1";

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
         Banner newBanner = TestUtils.newBanner();
         CrudTests crudTests = new CrudTests();
         newBanner = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNER_PATH, newBanner);
         Assert.assertNotNull(newBanner.getId());
         id = newBanner.getId().toString();
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
                  + AppConstants.BANNER_PATH, id);
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
         GenericType<List<Banner>> genericType = new GenericType<List<Banner>>()
         {
         };
         List<Banner> bannerList = crudTests.list(TARGET_HOST,
                  AppConstants.API_PATH + AppConstants.BASE_PATH + AppConstants.BANNER_PATH,
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
         Banner banner = crudTests.get(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNER_PATH, id, Banner.class);
         Assert.assertNotNull(banner);
         Assert.assertEquals(id, banner.getId().toString());

         String newName = "Nuovo Nome";
         banner.setName(newName);
         crudTests.update(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNER_PATH, banner);

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
                  + AppConstants.BANNER_PATH, id);
         crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNER_PATH, id);

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
                  + AppConstants.BANNER_PATH, id);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

   @Test
   @Order(order = 7)
   public void getRandomByTypologyAndLimit()
   {
      CrudTests crudTests = new CrudTests(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
               + AppConstants.BANNER_PATH);
      GenericType<List<Banner>> genericType = new GenericType<List<Banner>>()
      {
      };

      Invocation.Builder invocationBuilder =
               crudTests.getTarget().path("/random").queryParam("typology", "homepage").queryParam("limit", 1).request(
                        MediaType.APPLICATION_JSON);
      List<Banner> bannerList = invocationBuilder.get(genericType);
      Assert.assertNotNull(bannerList);
   }

}
