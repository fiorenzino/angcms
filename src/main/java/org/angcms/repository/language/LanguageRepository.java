package org.angcms.repository.language;

import org.angcms.model.lang.Language;
import org.angcms.repository.BaseRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class LanguageRepository extends BaseRepository<Language>
{

   private static final long serialVersionUID = 1L;

   @Override
   protected String getDefaultOrderBy()
   {
      return "name asc";
   }

}
