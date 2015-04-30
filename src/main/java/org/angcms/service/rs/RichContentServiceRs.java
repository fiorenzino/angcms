package org.angcms.service.rs;

import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.richcontent.RichContentRepository;
import org.angcms.repository.richcontent.RichContentTypeRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.repository.Search;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path(AppConstants.BASE_PATH + AppConstants.RICHCONTENT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RichContentServiceRs extends RsRepositoryService<RichContent>
{

   private static final long serialVersionUID = 1L;

   @Inject
   RichContentRepository richContentRepository;

   @Inject
   RichContentTypeRepository richContentTypeRepository;

   @GET
   @Path("/types")
   public List<String> getTipiRichContent()
   {
      try
      {
         Search<RichContentType> r = new Search<RichContentType>(RichContentType.class);
         List<RichContentType> rntl = richContentTypeRepository.getList(r, 0, 0);
         List<String> l = new ArrayList<String>();
         for (RichContentType rnt : rntl)
         {
            l.add(rnt.getName());
         }
         return l;
      }
      catch (Exception e)
      {

      }
      return null;
   }

   @GET
   @Path("/last")
   public RichContent getLast(@QueryParam("category") String category)
   {
      try
      {
         RichContent lastContent = richContentRepository.getLast(category);
         return lastContent;
      }
      catch (Exception e)
      {

      }
      return null;
   }

   @GET
   @Path("/highlight")
   public RichContent getHighlight(@QueryParam("category") String category)
   {
      try
      {
         RichContent highlightContent = richContentRepository.getHighlight(category);
         return highlightContent;
      }
      catch (Exception e)
      {

      }
      return null;
   }
}
