package org.angcms.service.rs;

import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsService;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.TEST_PATH)
@Stateless
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class TestService extends RsService
{

   public TestService()
   {
      logger.info("test service");
   }
}
