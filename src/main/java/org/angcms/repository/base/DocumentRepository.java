/*
 * Copyright 2013 GiavaCms.org.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.angcms.repository.base;

import org.angcms.model.base.attachment.Document;
import org.angcms.repository.BaseRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@Stateless
@LocalBean
public class DocumentRepository extends BaseRepository<Document>
{

   private static final long serialVersionUID = 1L;

   @PersistenceContext
   EntityManager em;

   @Override
   protected String getDefaultOrderBy()
   {
      return "id asc";
   }

}
