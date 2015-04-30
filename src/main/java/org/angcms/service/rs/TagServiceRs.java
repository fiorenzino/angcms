package org.angcms.service.rs;

import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.Tag;
import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.richcontent.TagRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.repository.Group;
import org.giavacms.api.repository.Search;
import org.giavacms.api.service.RsService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by fiorenzo on 30/04/15.
 * IMPLEMENTARE TUTTI I METODI NEL CONTROLLER
 */
@Path(AppConstants.BASE_PATH + AppConstants.TAG_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagServiceRs extends RsService
{

   @Inject
   TagRepository tagRepository;

   @GET
   @Path("/groups/{richContentType}")
   public List<Group<Tag>> getRequestTags(@PathParam("richContentType") String richContentType)
   {
      Search<Tag> st = new Search<Tag>(Tag.class);
      st.setGrouping("tagName");
      st.getObj().setRichContent(new RichContent());
      st.getObj().getRichContent().setRichContentType(new RichContentType(richContentType));
      List<Group<Tag>> requestTags = tagRepository.getGroups(st, 0, 10);
      return requestTags;
   }
}
