package org.angcms.repository.rs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.angcms.model.base.attachment.Image;
import org.angcms.model.richcontent.RichContent;
import org.angcms.repository.richcontent.RichContentRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

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
}