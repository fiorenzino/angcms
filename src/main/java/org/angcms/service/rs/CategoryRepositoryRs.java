package org.angcms.service.rs;

import org.angcms.model.catalogue.Category;
import org.angcms.repository.catalogue.CategoryRepository;
import org.giavacms.api.management.AppConstants;
import org.giavacms.api.service.RsRepositoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(AppConstants.BASE_PATH + AppConstants.CATEGORY_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryRepositoryRs extends RsRepositoryService<Category>
{

   private static final long serialVersionUID = 1L;

   @Inject
   CategoryRepository categoryRepository;

   public CategoryRepositoryRs()
   {
   }

   @Inject
   public CategoryRepositoryRs(CategoryRepository categoryRepository)
   {
      super(categoryRepository);
   }

}
