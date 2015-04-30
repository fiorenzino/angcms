package org.angcms.service.rs;

import org.angcms.model.banner.Banner;
import org.angcms.repository.banner.BannerRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by fiorenzo on 30/04/15.
 * IMPLEMENTARE TUTTI I METODI NEL CONTROLLER
 */
@Path(AppConstants.BASE_PATH + AppConstants.BANNER_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BannerServiceRs extends RsService
{

   @Inject
   BannerRepository bannerRepository;

   @GET
   @Path("/random")
   public List<Banner> getRandomByTypologyAndLimit(@QueryParam("typology") String typology,
            @QueryParam("limit") int limit)
   {
      try
      {
         List<Banner> typologyBanners = bannerRepository.getRandomByTypology(typology,
                  limit);
         return typologyBanners;
      }
      catch (Exception e)
      {

      }
      return null;

   }
}
