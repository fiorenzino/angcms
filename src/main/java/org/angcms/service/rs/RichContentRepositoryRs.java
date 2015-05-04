package org.angcms.service.rs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.angcms.model.base.attachment.Image;
import org.angcms.model.richcontent.RichContent;
import org.angcms.repository.base.ImageRepository;
import org.angcms.repository.richcontent.RichContentRepository;
import org.angcms.repository.richcontent.TagRepository;
import org.angcms.util.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;
import org.giavacms.core.util.FileUtils;
import org.giavacms.core.util.HttpUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path(AppConstants.BASE_PATH + AppConstants.RICHCONTENT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RichContentRepositoryRs extends RsRepositoryService<RichContent>
{

   private static final long serialVersionUID = 1L;

   public RichContentRepositoryRs()
   {
   }

   @Inject
   public RichContentRepositoryRs(RichContentRepository repository)
   {
      super(repository);
   }

   @Inject
   TagRepository tagRepository;
   @Inject
   ImageRepository imageRepository;

   @GET
   @Path("/last")
   public Response getLast(@QueryParam("category") String category)
   {
      try
      {
         RichContent lastContent = ((RichContentRepository) getRepository()).getLast(category);
         if (lastContent != null)
         {
            return Response.status(Status.OK).entity(lastContent)
                     .build();
         }
         else
         {
            return Response.status(Status.NO_CONTENT).build();
         }
      }
      catch (NoResultException e)
      {
         return Response.status(Status.NO_CONTENT).build();
      }
      catch (Exception e)
      {
         return Response.status(Status.INTERNAL_SERVER_ERROR)
                  .build();
      }
   }

   @GET
   @Path("/highlight")
   public Response getHighlight(@QueryParam("category") String category)
   {
      try
      {
         RichContent highlightContent = ((RichContentRepository) getRepository()).getHighlight(category);
         if (highlightContent != null)
         {
            return Response.status(Status.OK).entity(highlightContent)
                     .build();
         }
         else
         {
            return Response.status(Status.NO_CONTENT).build();
         }
      }
      catch (NoResultException e)
      {
         return Response.status(Status.NO_CONTENT).build();
      }
      catch (Exception e)
      {
         return Response.status(Status.INTERNAL_SERVER_ERROR)
                  .build();
      }
   }

   @POST
   @Path("/{richContentId}/image")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public Response addImage(@PathParam("richContentId") String richContentId, MultipartFormDataInput input)
            throws Exception
   {
      try
      {
         String fileName = "";
         Map<String, List<InputPart>> formParts = input.getFormDataMap();
         List<InputPart> inPart = formParts.get("file");
         for (InputPart inputPart : inPart)
         {
            // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            fileName = FileUtils.getLastPartOf(HttpUtils.parseFileName(headers));
            // Handle the body of that part with an InputStream
            InputStream istream = inputPart.getBody(InputStream.class, null);
            byte[] byteArray = IOUtils.toByteArray(istream);
            Image image = new Image();
            image.setFilename(FileUtils.getLastPartOf(fileName));
            image.setType(ResourceUtils.getType(fileName));
            fileName = ResourceUtils.createImage_(AppConstants.IMG_FOLDER, fileName, byteArray);
            image.setFilename(fileName);
            image.setData(byteArray);
            image = imageRepository.persist(image);
            if (image.getId() == null)
            {
               return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity("Error writing file: " + fileName).build();
            }
            ((RichContentRepository) getRepository()).addImage(richContentId, image.getId());
         }
         String output = "File saved to server location : " + fileName;
         return Response.status(200).entity(output).build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(Status.INTERNAL_SERVER_ERROR)
                  .entity("Error creating image").build();
      }
   }

   @GET
   @Path("/{richContentId}/images")
   public Response getImages(@PathParam("richContentId") String richContentId)
   {
      try
      {
         List<Image> list = ((RichContentRepository) getRepository()).getImages(richContentId);
         if (list == null || list.size() == 0)
         {
            return Response.status(Status.NO_CONTENT).build();
         }
         return Response.status(Status.OK).entity(list)
                  .header("Access-Control-Expose-Headers", "startRow, pageSize, listSize, startRow")
                  .header("startRow", 0)
                  .header("pageSize", list.size())
                  .header("listSize", list.size())
                  .build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      }
   }

   @GET
   @Path("/{richContentId}/documents")
   public Response getDocument(@PathParam("richContentId") String richContentId)
   {
      try
      {
         List<Image> list = ((RichContentRepository) getRepository()).getDocuments(richContentId);
         if (list == null || list.size() == 0)
         {
            return Response.status(Status.NO_CONTENT).build();
         }
         return Response.status(Status.OK).entity(list)
                  .header("Access-Control-Expose-Headers", "startRow, pageSize, listSize, startRow")
                  .header("startRow", 0)
                  .header("pageSize", list.size())
                  .header("listSize", list.size())
                  .build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      }
   }

   @Override
   protected void postPersist(RichContent object) throws Exception
   {
      postPersistOrUpdate(object);
   }

   @Override
   protected void postUpdate(RichContent object) throws Exception
   {
      postPersistOrUpdate(object);
   }

   private void postPersistOrUpdate(RichContent object)
   {
      tagRepository.set(object.getId(), object.getTagList(),
               object.getDate());
      if (object.isHighlight())
      {
         ((RichContentRepository) getRepository()).refreshHighlight(object.getId(), object.getRichContentType());
      }
   }

   @Override
   protected void postDelete(Object key) throws Exception
   {
      tagRepository.set(key.toString(), new ArrayList<String>(),
               new Date());
   }

}