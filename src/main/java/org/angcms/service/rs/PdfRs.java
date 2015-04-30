package org.angcms.service.rs;

import org.giavacms.api.service.RsService;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/pdf")
@Stateless
public class PdfRs extends RsService
{

   private static final long serialVersionUID = 1L;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/inviti/{token}")
   public Response getInvito(@PathParam("token") String token)
   {
      try
      {

         return Response.status(404).build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(500).build();
      }
   }

}
