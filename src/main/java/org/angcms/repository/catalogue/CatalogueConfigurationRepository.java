/*
 * Copyright 2013 GiavaCms.org.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.angcms.repository.catalogue;

import org.angcms.model.catalogue.CatalogueConfiguration;
import org.angcms.repository.BaseRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class CatalogueConfigurationRepository extends
         BaseRepository<CatalogueConfiguration>
{

   private static final long serialVersionUID = 1L;

   @Override
   protected String getDefaultOrderBy()
   {
      // TODO Auto-generated method stub
      return "id asc";
   }

   public CatalogueConfiguration load() throws Exception
   {
      CatalogueConfiguration c = null;
      try
      {
         c = find(1L);
      }
      catch (Exception e)
      {
      }
      if (c == null)
      {
         c = new CatalogueConfiguration();
         c.setResize(false);
         c.setMaxWidthOrHeight(0);
         persist(c);
      }
      return c;
   }

}
