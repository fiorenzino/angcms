package org.angcms.repository.rs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.Tag;
import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.richcontent.TagRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.repository.Group;
import org.giavacms.api.repository.Search;
import org.giavacms.api.service.RsRepositoryService;

@Path(AppConstants.BASE_PATH + AppConstants.TAG_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagRepositoryRs extends RsRepositoryService<Tag>
{

   private static final long serialVersionUID = 1L;

   public TagRepositoryRs()
   {
   }

   @Inject
   public TagRepositoryRs(TagRepository tagRepository)
   {
      super(tagRepository);
   }

   @GET
   @Path("/groups/{richContentType}")
   public Response getRequestTags(@PathParam("richContentType") String richContentType)
   {
      try
      {
         Search<Tag> st = new Search<Tag>(Tag.class);
         st.setGrouping("tagName");
         st.getObj().setRichContent(new RichContent());
         st.getObj().getRichContent().setRichContentType(new RichContentType(richContentType));
         List<Group<Tag>> list = ((TagRepository) getRepository()).getGroups(st, 0, 10);
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
