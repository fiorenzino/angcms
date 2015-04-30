package org.angcms.service.rs;

import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.richcontent.RichContentTypeRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.RICHCONTENT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RichContentTypeRepositoryRs extends RsRepositoryService<RichContentType>
{

   private static final long serialVersionUID = 1L;

   @Inject
   RichContentTypeRepository richContentTypeRepository;

   public RichContentTypeRepositoryRs()
   {
   }

   @Inject
   public RichContentTypeRepositoryRs(RichContentTypeRepository richContentTypeRepository)
   {
      super(richContentTypeRepository);
   }

   @Override
   protected void prePersist(RichContentType richContentType) throws Exception
   {

   }

}
