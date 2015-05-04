package all.test.common;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Assert;

public class CrudTests
{

   Logger logger = Logger.getLogger(getClass());

   private String username;
   private String password;
   private String targetHost;
   private String targetPath;

   public CrudTests()
   {
   }

   public CrudTests(String targetHost, String targetPath, String username, String password)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      this.username = username;
      this.password = password;
   }

   public CrudTests(String targetHost, String targetPath)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
   }

   public WebTarget getTarget()
   {

      try
      {
         if (this.username != null && password != null)
         {
            return getTarget(targetHost, targetPath, username, password);
         }
         else
         {
            return getTarget(targetHost, targetPath);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return null;
   }

   public static WebTarget getTarget(String targetHost, String targetPath)
   {
      try
      {
         Client client = new ResteasyClientBuilder().disableTrustManager()
                  .build();
         WebTarget target = client.target(targetHost + targetPath);
         return target;
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return null;
   }

   public WebTarget getTarget(String targetHost, String targetPath, String username, String password)
   {

      try
      {

         Client client = new ResteasyClientBuilder().disableTrustManager()
                  .build().register(new Authenticator(username, password));
         WebTarget target = client.target(targetHost + targetPath);
         return target;

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return null;
   }

   @SuppressWarnings("unchecked")
   public <T> T create(String targetHost, String targetPath, T t)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      return create(t, null, null, null);
   }

   @SuppressWarnings("unchecked")
   public <T> T create(T t, Map<String, String> pathParams,
            Map<String, String> queryParams,
            Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget();
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Object result = invocationBuilder
                  .buildPost(Entity.json(t)).invoke(t.getClass());
         Assert.assertNotNull(result);
         return (T) result;
      }
      catch (Exception e)
      {
         logger.info("Obj:" + t.getClass());
         logger.error(e.getMessage());

         Assert.fail(e.getClass().getCanonicalName());
         return null;
      }
   }

   public void exists(String targetHost, String targetPath, String uuid)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      exists(uuid);
   }

   public void exists(String uuid)
   {
      exists(uuid, null, null, null);
   }

   public void exists(String uuid, Map<String, String> pathParams,
            Map<String, String> queryParams,
            Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget().path("/{id}/exist")
                  .resolveTemplate("id", uuid);
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Response result = invocationBuilder.get();
         Assert.assertNotNull(result);
         logger.info(result.getStatus());
         Assert.assertEquals(Status.FOUND.getStatusCode(),
                  result.getStatus());
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
      }
   }

   public void notExists(String targetHost, String targetPath, String uuid)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      notExists(uuid);
   }

   public void notExists(String uuid)
   {
      notExists(uuid, null, null, null);
   }

   public void notExists(String uuid, Map<String, String> pathParams,
            Map<String, String> queryParams,
            Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget().path("/{id}/exist")
                  .resolveTemplate("id", uuid);
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Response result = invocationBuilder.get();
         Assert.assertNotNull(result);
         logger.info(result.getStatus());
         Assert.assertEquals(Status.NOT_FOUND.getStatusCode(),
                  result.getStatus());
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
      }
   }

   public static int executeMultiPartRequest(String urlString, File file, String fileName, String fileDescription)
            throws Exception
   {
      HttpClient client = new DefaultHttpClient();
      HttpPost postRequest = new HttpPost(urlString);
      try
      {
         // Set various attributes
         MultipartEntity multiPartEntity = new MultipartEntity();
         multiPartEntity.addPart("fileDescription", new StringBody(fileDescription != null ? fileDescription : ""));
         multiPartEntity.addPart("fileName", new StringBody(fileName != null ? fileName : file.getName()));

         // Prepare payload
         FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, fileName);
         FormBodyPart formBodyPart = new FormBodyPart("file", fileBody);
         multiPartEntity.addPart(formBodyPart);

         // Set to request body
         postRequest.setEntity(multiPartEntity);

         // Send request
         HttpResponse response = client.execute(postRequest);

         // Verify response if any
         return response.getStatusLine().getStatusCode();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return 0;

      }
   }

   public <T> List<T> wrappedList(String targetHost, String targetPath,
            String orderBy, GenericType<List<T>> genericType)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      return wrappedList(genericType, null, null, null);
   }

   public <T> List<T> list(String targetHost, String targetPath,
            String orderBy, GenericType<List<T>> genericType)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      return list(genericType, null, null, null);
   }

   public <T> List<T> wrappedList(GenericType<List<T>> genericType,
            Map<String, String> pathParams,
            Map<String, String> queryParams,
            Map<String, String> headerParams)
   {
      List<T> result = null;
      try
      {
         WebTarget target = getTarget().queryParam(
                  "startRow", 0).queryParam("pageSize", Integer.MAX_VALUE);
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Response response = invocationBuilder.get();
         result = response.readEntity(
                  genericType);
         for (Object entity : result)
         {
            logger.info(entity);
         }
         String startRow = response.getHeaderString("startRow");
         String pageSize = response.getHeaderString("pageSize");
         String listSize = response.getHeaderString("listSize");
         logger.info("startRow: " + startRow);
         logger.info("pageSize: " + pageSize);
         logger.info("listSize: " + listSize);
         Assert.assertNotNull(result);
         Assert.assertTrue(Integer.valueOf(listSize) > 0);
         return result;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
         return null;
      }
   }

   public <T> List<T> list(
            GenericType<List<T>> genericType, Map<String, String> queryParams,
            Map<String, String> pathParams, Map<String, String> headerParams)
   {
      List<T> wrapper = null;
      try
      {
         WebTarget target = getTarget().queryParam(
                  "startRow", 0).queryParam("pageSize", Integer.MAX_VALUE);
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Response response = invocationBuilder.get();
         wrapper = response.readEntity(
                  genericType);
         for (Object entity : wrapper)
         {
            logger.info(entity);
         }
         String startRow = response.getHeaderString("startRow");
         String pageSize = response.getHeaderString("pageSize");
         String listSize = response.getHeaderString("listSize");
         logger.info("startRow: " + startRow);
         logger.info("pageSize: " + pageSize);
         logger.info("listSize: " + listSize);
         logger.info("getListSize: " + wrapper.size());
         Assert.assertNotNull(wrapper);
         Assert.assertTrue(wrapper.size() > 0);
         return wrapper;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
         return null;
      }
   }

   public <T> T get(String targetHost, String targetPath, String uuid,
            Class<T> t)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      return get(uuid, t, null, null, null);
   }

   public <T> T get(String uuid,
            Class<T> t, Map<String, String> queryParams,
            Map<String, String> pathParams, Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget().path("/{id}")
                  .resolveTemplate("id", uuid);
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         T entity = invocationBuilder.get(t);
         System.out.println(entity.toString());
         Assert.assertNotNull(entity);
         return entity;
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
         return null;
      }
   }

   public void update(String uuid, String targetHost, String targetPath, Object entity)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      update(uuid, entity, null, null, null);
   }

   public void update(String uuid, Object entity, Map<String, String> queryParams, Map<String, String> pathParams,
            Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget().path("/{id}")
                  .resolveTemplate("id", uuid);
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Object result = invocationBuilder.buildPut(Entity.json(entity)).invoke(entity.getClass());
         Assert.assertNotNull(result);

      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
      }
   }

   public void delete(String targetHost, String targetPath, String uuid)
   {
      this.targetHost = targetHost;
      this.targetPath = targetPath;
      delete(uuid, null, null, null);
   }

   public void delete(String uuid, Map<String, String> queryParams, Map<String, String> pathParams,
            Map<String, String> headerParams)
   {
      try
      {
         WebTarget target = getTarget().path("/{id}").resolveTemplate("id", uuid);
         if (pathParams != null && !pathParams.isEmpty())
         {
            for (String key : pathParams.keySet())
            {
               target = target.resolveTemplate(key, pathParams.get(key));
            }
         }
         if (queryParams != null && !queryParams.isEmpty())
         {
            for (Map.Entry<String, String> coppia : queryParams.entrySet())
            {
               target = target.queryParam(coppia.getKey(), coppia.getValue());
            }
         }
         Invocation.Builder invocationBuilder =
                  target.request(MediaType.APPLICATION_JSON);
         if (headerParams != null && !headerParams.isEmpty())
         {
            for (String key : headerParams.keySet())
            {
               invocationBuilder = invocationBuilder.header(key, headerParams.get(key));
            }
         }
         Response result = invocationBuilder.delete();
         Assert.assertNotNull(result);
         logger.info(result.getStatus());
         Assert.assertEquals(Status.NO_CONTENT.getStatusCode(),
                  result.getStatus());

      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());
      }
   }

   public void up()
   {
      try
      {
         Response result = getTarget()
                  .path("/up")
                  .request().get();
         Assert.assertNotNull(result);
         logger.info(result.getStatus());
         Assert.assertEquals(Status.OK.getStatusCode(),
                  result.getStatus());
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());
         Assert.fail(e.getClass().getCanonicalName());

      }
   }
}
