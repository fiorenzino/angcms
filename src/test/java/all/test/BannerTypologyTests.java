package all.test;

import all.helper.junit.Order;
import all.helper.junit.OrderedRunner;
import all.test.common.CrudTests;
import all.test.util.TestUtils;
import org.angcms.management.AppConstants;
import org.angcms.model.banner.BannerTypology;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.net.ssl.*;
import javax.ws.rs.core.GenericType;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@RunWith(OrderedRunner.class)
public class BannerTypologyTests
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
         BannerTypology newBannerTypology = TestUtils.newBannerTypology();
         CrudTests crudTests = new CrudTests();
         newBannerTypology = crudTests.create(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNERTYPOLOGY_PATH, newBannerTypology);
         Assert.assertNotNull(newBannerTypology.getId());
         id = newBannerTypology.getId().toString();
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
                  + AppConstants.BANNERTYPOLOGY_PATH, id);
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
         GenericType<List<BannerTypology>> genericType = new GenericType<List<BannerTypology>>()
         {
         };
         List<BannerTypology> bannerList = crudTests.list(TARGET_HOST,
                  AppConstants.API_PATH + AppConstants.BASE_PATH + AppConstants.BANNERTYPOLOGY_PATH,
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
         BannerTypology bannerTypology = crudTests.get(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNERTYPOLOGY_PATH, id, BannerTypology.class);
         Assert.assertNotNull(bannerTypology);
         Assert.assertEquals(id, bannerTypology.getId().toString());

         String newName = "Nuovo Nome";
         bannerTypology.setName(newName);
         crudTests.update(id, TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNERTYPOLOGY_PATH, bannerTypology);

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
                  + AppConstants.BANNERTYPOLOGY_PATH, id);
         crudTests.notExists(TARGET_HOST, AppConstants.API_PATH + AppConstants.BASE_PATH
                  + AppConstants.BANNERTYPOLOGY_PATH, id);

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
                  + AppConstants.BANNERTYPOLOGY_PATH, id);

      }
      catch (Exception ex)
      {
         System.err.println(ex);
         Assert.fail(ex.getClass().getCanonicalName());
      }
   }

}
