package org.angcms.service.rs;

import org.angcms.model.banner.Banner;
import org.angcms.repository.banner.BannerRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.BANNER_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BannerRepositoryRs extends RsRepositoryService<Banner>
{

   private static final long serialVersionUID = 1L;

   @Inject
   BannerRepository bannerRepository;

   public BannerRepositoryRs()
   {
   }

   @Inject
   public BannerRepositoryRs(BannerRepository bannerRepository)
   {
      super(bannerRepository);
   }

}
