package org.angcms.service.rs;

import org.angcms.model.richcontent.RichContent;
import org.angcms.repository.richcontent.RichContentRepository;
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
public class RichContentRepositoryRs extends RsRepositoryService<RichContent>
{

   private static final long serialVersionUID = 1L;

   @Inject
   RichContentRepository richContentRepository;

   public RichContentRepositoryRs()
   {
   }

   @Inject
   public RichContentRepositoryRs(RichContentRepository richContentRepository)
   {
      super(richContentRepository);
   }

   @Override
   protected void prePersist(RichContent richContent) throws Exception
   {

   }

}
