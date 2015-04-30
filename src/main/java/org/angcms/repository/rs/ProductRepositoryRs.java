package org.angcms.repository.rs;

import org.angcms.model.catalogue.Product;
import org.angcms.repository.catalogue.ProductRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.PRODUCT_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductRepositoryRs extends RsRepositoryService<Product>
{

   private static final long serialVersionUID = 1L;

   @Inject
   ProductRepository productRepository;

   public ProductRepositoryRs()
   {
   }

   @Inject
   public ProductRepositoryRs(ProductRepository productRepository)
   {
      super(productRepository);
   }

}
