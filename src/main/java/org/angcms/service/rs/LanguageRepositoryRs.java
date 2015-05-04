package org.angcms.service.rs;

import org.angcms.model.lang.Language;
import org.angcms.repository.language.LanguageRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.LANGUAGE_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LanguageRepositoryRs extends RsRepositoryService<Language>
{

   private static final long serialVersionUID = 1L;

   @Inject
   LanguageRepository languageRepository;

   public LanguageRepositoryRs()
   {
   }

   @Inject
   public LanguageRepositoryRs(LanguageRepository languageRepository)
   {
      super(languageRepository);
   }

}
