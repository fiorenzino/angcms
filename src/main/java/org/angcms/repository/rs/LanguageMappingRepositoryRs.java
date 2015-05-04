package org.angcms.repository.rs;

import org.angcms.model.lang.LanguageMapping;
import org.angcms.repository.language.LanguageMappingRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.LANGUAGE_MAPPING_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LanguageMappingRepositoryRs extends RsRepositoryService<LanguageMapping>
{

   private static final long serialVersionUID = 1L;

   @Inject
   LanguageMappingRepository languageMappingRepository;

   public LanguageMappingRepositoryRs()
   {
   }

   @Inject
   public LanguageMappingRepositoryRs(LanguageMappingRepository languageMappingRepository)
   {
      super(languageMappingRepository);
   }

}
