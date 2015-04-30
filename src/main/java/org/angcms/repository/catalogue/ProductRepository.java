/*
 * Copyright 2013 GiavaCms.org.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.angcms.repository.catalogue;

import org.angcms.model.base.attachment.Document;
import org.angcms.model.base.attachment.Image;
import org.angcms.model.catalogue.Category;
import org.angcms.model.catalogue.Product;
import org.angcms.repository.BaseRepository;
import org.giavacms.api.repository.Search;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Map;

@Named
@Stateless
@LocalBean
public class ProductRepository extends BaseRepository<Product>
{

   private static final long serialVersionUID = 1L;

   @PersistenceContext
   EntityManager em;

   @Override
   protected String getDefaultOrderBy()
   {
      return "code asc";
   }

   /**
    * In case of a main table with one-to-many collections to fetch at once
    * <p/>
    * we need an external query to read results and an internal query to apply
    * parameters and paginate results
    * <p/>
    * we need just the external query to apply parameters and count the overall
    * distinct results
    */
   protected StringBuffer getListNative(Search<Product> search,
            Map<String, Object> params, boolean count, int startRow,
            int pageSize, boolean completeFetch)
   {

      // aliases to use in the external query
      String pageAlias = "P";
      String templateImplAlias = "TI";
      String productAlias = "T";
      String categoryAlias = "C";
      String categoryPageAlias = "CP";
      String imageAlias = "I";
      String documentAlias = "D";

      // query string buffer
      StringBuffer sb = new StringBuffer("SELECT ");

      if (count)
      {
         // we only count distinct results in case of count = true
         sb.append(" count( distinct ").append(pageAlias).append(".id ) ");
      }
      else
      {
         // we select a cartesian product of master/details rows in case of
         // count = false
         sb.append(pageAlias).append(".id, ");
         sb.append(pageAlias).append(".lang1id, ");
         sb.append(pageAlias).append(".lang2id, ");
         sb.append(pageAlias).append(".lang3id, ");
         sb.append(pageAlias).append(".lang4id, ");
         sb.append(pageAlias).append(".lang5id, ");
         sb.append(pageAlias).append(".clone, ");
         sb.append(pageAlias).append(".title, ");
         sb.append(pageAlias).append(".description, ");
         sb.append(templateImplAlias).append(".id as templateImpl_id, ");
         sb.append(templateImplAlias).append(".mainPageId, ");
         sb.append(templateImplAlias).append(".mainPageTitle, ");
         sb.append(productAlias).append(".preview, ");
         sb.append(productAlias).append(".dimensions, ");
         sb.append(productAlias).append(".price, ");
         sb.append(productAlias).append(".vat, ");
         sb.append(productAlias).append(".code, ");
         for (int v = 1; v <= 10; v++)
         {
            sb.append(productAlias).append(".val").append(v).append(", ");
         }
         for (int p = 1; p <= 10; p++)
         {
            sb.append(categoryAlias).append(".prop").append(p).append(", ");
         }
         for (int r = 1; r <= 10; r++)
         {
            sb.append(categoryAlias).append(".ref").append(r).append(", ");
         }
         sb.append(productAlias).append(".category_id, ");
         sb.append(categoryPageAlias).append(".title AS categoryTitle, ");
         sb.append(imageAlias).append(".id AS imageId, ");
         sb.append(imageAlias).append(".fileName AS image, ");
         sb.append(documentAlias).append(".id AS documentId, ");
         sb.append(documentAlias).append(".fileName AS document ");
         if (completeFetch)
         {
            // additional fields to retrieve only when fetching
         }
      }

      // master-from clause is the same in both count = true and count = false
      sb.append(" FROM ").append(Product.TABLE_NAME).append(" AS ")
               .append(productAlias);
      sb.append(" LEFT JOIN ").append(Category.TABLE_NAME).append(" AS ")
               .append(categoryAlias).append(" ON ( ").append(categoryAlias)
               .append(".id = ").append(productAlias)
               .append(".category_id ) ");

      if (count)
      {
         // we optimize the count query by avoiding useless left joins
      }
      else
      {
         // we need details-from clause in case of count = false
         if (Product.HAS_DETAILS)
         {
            sb.append(" LEFT JOIN ").append(Product.TABLE_NAME).append("_")
                     .append(Document.TABLE_NAME).append(" AS RD ON ( RD.")
                     .append(Product.TABLE_NAME).append("_id = ")
                     .append(productAlias).append(".id ) ");
            sb.append(" LEFT JOIN ").append(Document.TABLE_NAME)
                     .append(" AS ").append(documentAlias)
                     .append(" ON ( RD.documents_id = ")
                     .append(documentAlias).append(".id ) ");
            sb.append(" LEFT JOIN ").append(Product.TABLE_NAME).append("_")
                     .append(Image.TABLE_NAME).append(" AS RI ON ( RI.")
                     .append(Product.TABLE_NAME).append("_id = ")
                     .append(productAlias).append(".id ) ");
            sb.append(" LEFT JOIN ").append(Image.TABLE_NAME)
                     .append(" as ").append(imageAlias).append(" on ( ")
                     .append(imageAlias).append(".id = RI.images_id ) ");
         }
      }

      String innerPageAlias = null;
      String innerTemplateImplAlias = null;
      String innerProductAlias = null;
      String innerCategoryAlias = null;
      String innerCategoryPageAlias = null;
      if (count)
      {
         // we don't need an inner query in case of count = true (because we
         // only need distinct id to count,
         // disregarding result pagination) so aliases stay the same
         innerPageAlias = pageAlias;
         innerTemplateImplAlias = templateImplAlias;
         innerProductAlias = productAlias;
         innerCategoryAlias = categoryAlias;
         innerCategoryPageAlias = categoryPageAlias;
      }
      else if (Product.HAS_DETAILS)
      {
         // we need different aliases for the inner query in case of count =
         // false or multiple detail rows for each
         // master
         innerPageAlias = "P1";
         innerTemplateImplAlias = "TI1";
         innerProductAlias = "T1";
         innerCategoryAlias = "C1";
         innerCategoryPageAlias = "CP1";
         // inner query comes as an inner join, because mysql does not
         // support limit in subquerys
         sb.append(" INNER JOIN ");

         // this is the opening bracket for the internal query
         sb.append(" ( ");

         sb.append(" select distinct ").append(innerPageAlias)
                  .append(".id from ");
         sb.append(Product.TABLE_NAME).append(" AS ")
                  .append(innerProductAlias);
         sb.append(" LEFT JOIN ").append(Category.TABLE_NAME).append(" AS ")
                  .append(innerCategoryAlias).append(" ON ( ")
                  .append(innerCategoryAlias).append(".id = ")
                  .append(innerProductAlias).append(".category_id ) ");
      }
      else
      {
         // we also don't need an inner query in case of master-data that has
         // no multiple details
         // so aliases can stay the same
         innerPageAlias = pageAlias;
         innerTemplateImplAlias = templateImplAlias;
         innerProductAlias = productAlias;
         innerCategoryAlias = categoryAlias;
         innerCategoryPageAlias = categoryPageAlias;
      }

      // we append filters right after the latest query, so that they apply to
      // the external one in case count = true and
      // to the internal one in case count = false
      String separator = " where ";
      applyRestrictionsNative(search, innerPageAlias, innerTemplateImplAlias,
               innerProductAlias, innerCategoryAlias, innerCategoryPageAlias,
               separator, sb, params);

      if (count)
      {
         // if we only need to count distinct results, we are over!
      }
      else
      {
         // we need to sort internal results to apply pagination
         sb.append(" order by ").append(innerProductAlias)
                  .append(".code asc ");

         // we apply limit-clause only if we want pagination
         if (pageSize > 0)
         {
            sb.append(" limit ").append(startRow).append(", ")
                     .append(pageSize).toString();
         }

         if (Product.HAS_DETAILS)
         {
            // this is the closing bracket for the internal query
            sb.append(" ) ");
            // this is where external id selection applies
            sb.append(" as IN2 ON ").append(pageAlias)
                     .append(".ID = IN2.ID ");
            // we also need to sort external results to keep result order
            // within each results page
            sb.append(" order by ").append(productAlias)
                     .append(".code asc ");
            sb.append(", ").append(imageAlias).append(".id asc ");
            sb.append(", ").append(documentAlias).append(".id asc ");
         }

      }
      return sb;
   }

   protected void applyRestrictionsNative(Search<Product> search,
            String pageAlias, String innerTemplateImplAlias,
            String productAlias, String categoryAlias,
            String categoryPageAlias, String separator, StringBuffer sb,
            Map<String, Object> params)
   {

      if (true)
      {
         sb.append(separator).append(productAlias)
                  .append(".category_id is not null ");
         separator = " and ";
         sb.append(separator).append(categoryPageAlias)
                  .append(".active = :activeCategory");
         params.put("activeCategory", true);
         separator = " and ";
      }

      // CATEGORY NAME
      if (search.getObj().getCategory() != null
               && search.getObj().getCategory().getName() != null
               && search.getObj().getCategory().getName().trim().length() > 0)
      {
         sb.append(separator).append(categoryPageAlias)
                  .append(".name = :NAMECAT ");
         params.put("NAMECAT", search.getObj().getCategory().getName());
         separator = " and ";
      }

      // CATEGORY ID
      if (search.getObj().getCategory() != null
               && search.getObj().getCategory().getId() != null
               && search.getObj().getCategory().getId().trim().length() > 0)
      {
         sb.append(separator).append(categoryAlias).append(".id = :IDCAT ");
         params.put("IDCAT", search.getObj().getCategory().getId());
         separator = " and ";
      }

      // CODE
      if (search.getObj().getCode() != null
               && !search.getObj().getCode().isEmpty())
      {
         sb.append(separator).append(productAlias).append(".code = :CODE ");
         params.put("CODE", search.getObj().getCode());
         separator = " and ";
      }

      // VALS
      if (search.getObj().getVals() != null
               && search.getObj().getVals().size() > 0)
      {
         int valsCount = 0;
         for (String prop : search.getObj().getVals().keySet())
         {
            valsCount++;
            String[] vals = search.getObj().getVals().get(prop);
            sb.append(separator).append(" ( ");
            boolean first = true;
            for (int v = 1; v <= 10; v++)
            {

               if (!first)
               {
                  sb.append(" or ");
               }
               else
               {
                  first = false;
               }
               sb.append(" ( ");
               sb.append(productAlias).append(".val").append(v)
                        .append(" in ( :VAL").append(valsCount)
                        .append(" ) ");
               sb.append(" and ");
               sb.append(categoryAlias).append(".prop").append(v)
                        .append(" = :PROP").append(valsCount);
               sb.append(" ) ");
            }
            if (valsCount != search.getObj().getVals().keySet().size())
               sb.append(separator).append(" ) ");
            else
               sb.append(" ) ");
            params.put("PROP" + valsCount, prop);
            params.put("VAL" + valsCount, Arrays.asList(vals));
            separator = " and ";
         }
      }

      // NAME OR DESCRIPTION
      // TITLE --> ALSO SEARCH IN CUSTOM FIELDS
      String customLike = null;
      if (search.getObj().getName() != null
               && !search.getObj().getName().trim().isEmpty())
      {
         customLike = " upper ( " + pageAlias
                  + ".description ) like :LIKETEXTCUSTOM ";
         params.put("LIKETEXTCUSTOM", likeParam(search.getObj().getName()
                  .trim().toUpperCase()));
      }

   }

   @Override
   protected Product prePersist(Product n) throws Exception
   {
      n.setDescription(n.getDescription());
      n = super.prePersist(n);
      return n;
   }

   @Override
   protected Product preUpdate(Product n) throws Exception
   {
      n.setDescription(n.getDescription());
      n = super.preUpdate(n);
      return n;
   }

}
