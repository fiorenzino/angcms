package org.angcms.service.rs;

import org.angcms.model.richcontent.Tag;
import org.angcms.repository.richcontent.TagRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.RICHCONTENT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagRepositoryRs extends RsRepositoryService<Tag>
{

   private static final long serialVersionUID = 1L;

   @Inject
   TagRepository tagRepository;

   public TagRepositoryRs()
   {
   }

   @Inject
   public TagRepositoryRs(TagRepository tagRepository)
   {
      super(tagRepository);
   }

   @Override
   protected void prePersist(Tag tag) throws Exception
   {

   }

}
