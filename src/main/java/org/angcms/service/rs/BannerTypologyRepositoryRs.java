package org.angcms.service.rs;

import org.angcms.model.banner.BannerTypology;
import org.angcms.repository.banner.BannerTypologyRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.BANNERTYPOLOGY_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BannerTypologyRepositoryRs extends RsRepositoryService<BannerTypology>
{

   private static final long serialVersionUID = 1L;

   @Inject
   public BannerTypologyRepositoryRs(BannerTypologyRepository bannerTypologyRepository)
   {
      super(bannerTypologyRepository);
   }

   public BannerTypologyRepositoryRs()
   {
   }

}
