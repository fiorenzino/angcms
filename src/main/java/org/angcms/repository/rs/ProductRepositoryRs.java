package org.angcms.repository.rs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.angcms.model.base.attachment.Image;
import org.angcms.model.catalogue.Product;
import org.angcms.repository.catalogue.ProductRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

@Path(AppConstants.BASE_PATH + AppConstants.PRODUCT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductRepositoryRs extends RsRepositoryService<Product>
{

   private static final long serialVersionUID = 1L;

   @Inject
   public ProductRepositoryRs(ProductRepository productRepository)
   {
      super(productRepository);
   }

   @GET
   @Path("/{productId}/images")
   public Response getImages(@PathParam("productId") String productId)
   {
      try
      {
         List<Image> list = ((ProductRepository) getRepository()).getImages(productId);
         if (list == null || list.size() == 0)
         {
            return Response.status(Status.NO_CONTENT).build();
         }
         return Response.status(Status.OK).entity(list)
                  .header("Access-Control-Expose-Headers", "startRow, pageSize, listSize, startRow")
                  .header("startRow", 0)
                  .header("pageSize", list.size())
                  .header("listSize", list.size())
                  .build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      }
   }

   @GET
   @Path("/{productId}/documents")
   public Response getDocument(@PathParam("productId") String productId)
   {
      try
      {
         List<Image> list = ((ProductRepository) getRepository()).getDocuments(productId);
         if (list == null || list.size() == 0)
         {
            return Response.status(Status.NO_CONTENT).build();
         }
         return Response.status(Status.OK).entity(list)
                  .header("Access-Control-Expose-Headers", "startRow, pageSize, listSize, startRow")
                  .header("startRow", 0)
                  .header("pageSize", list.size())
                  .header("listSize", list.size())
                  .build();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
         return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      }
   }

}
