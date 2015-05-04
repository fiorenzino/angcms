package org.angcms.repository.richcontent;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.angcms.model.base.attachment.Document;
import org.angcms.model.base.attachment.Image;
import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.Tag;
import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.BaseRepository;
import org.angcms.util.StringUtils;
import org.angcms.util.TimeUtils;
import org.giavacms.api.model.Search;
import org.giavacms.api.util.IdUtils;
import org.giavacms.core.util.DateUtils;

@Named
@Stateless
@LocalBean
public class RichContentRepository extends BaseRepository<RichContent>
{

   private static final long serialVersionUID = 1L;

   @Override
   protected String getDefaultOrderBy()
   {
      return "date desc";
   }

   @SuppressWarnings("unchecked")
   public List<Image> getImages(String id)
   {
      return getEm()
               .createNativeQuery(
                        "select * from " + Image.TABLE_NAME + " where id in ( "
                                 + "    select " + RichContent.IMAGE_FK + " from "
                                 + RichContent.IMAGES_JOINTABLE_NAME
                                 + " where " + RichContent.TABLE_FK + " = ( "
                                 + "       select id from " + RichContent.TABLE_NAME
                                 + "    ) "
                                 + " ) ", Image.class)
               .getResultList();
   }

   @SuppressWarnings("unchecked")
   public List<Image> getDocuments(String id)
   {
      return getEm()
               .createNativeQuery(
                        "select * from " + Document.TABLE_NAME + " where id in ( "
                                 + "    select " + RichContent.DOCUMENT_FK + " from "
                                 + RichContent.DOCUMENTS_JOINTABLE_NAME
                                 + " where " + RichContent.TABLE_FK + " = ( "
                                 + "       select id from " + RichContent.TABLE_NAME
                                 + "    ) "
                                 + " ) ", Document.class)
               .getResultList();
   }

   @Override
   protected RichContent prePersist(RichContent n)
   {
      String idTitle = IdUtils.createPageId(n.getTitle());
      String idFinal = makeUniqueKey(idTitle, RichContent.TABLE_NAME);
      n.setId(idFinal);
      if (n.getDate() == null)
         n.setDate(new Date());
      if (n.getRichContentType() != null
               && n.getRichContentType().getId() != null)
         n.setRichContentType(getEm().find(RichContentType.class,
                  n.getRichContentType().getId()));
      if (n.getDocuments() != null && n.getDocuments().size() == 0)
      {
         n.setDocuments(null);
      }
      if (n.getImages() != null && n.getImages().size() == 0)
      {
         n.setImages(null);
      }
      n.setDate(TimeUtils.adjustHourAndMinute(n.getDate()));
      n.setContent(n.getContent());
      return n;
   }

   @Override
   protected RichContent preUpdate(RichContent n)
   {
      if (n.getDate() == null)
         n.setDate(new Date());
      if (n.getRichContentType() != null
               && n.getRichContentType().getId() != null)
         n.setRichContentType(getEm().find(RichContentType.class,
                  n.getRichContentType().getId()));
      if (n.getDocuments() != null && n.getDocuments().size() == 0)
      {
         n.setDocuments(null);
      }
      if (n.getImages() != null && n.getImages().size() == 0)
      {
         n.setImages(null);
      }
      n.setDate(TimeUtils.adjustHourAndMinute(n.getDate()));
      n.setContent(n.getContent());
      return n;
   }

   public RichContent getLast() throws Exception
   {
      return getLast(null);
   }

   public RichContent getLast(String category) throws Exception
   {
      Search<RichContent> r = new Search<RichContent>(RichContent.class);
      r.getObj().getRichContentType().setName(category);
      List<RichContent> list = super.getList(r, 0, 1);
      return list.size() > 0 ? list.get(0) : new RichContent();
   }

   public RichContent getHighlight(String category) throws Exception
   {
      Search<RichContent> r = new Search<RichContent>(RichContent.class);
      r.getObj().getRichContentType().setName(category);
      r.getObj().setHighlight(true);
      List<RichContent> list = super.getList(r, 0, 1);
      return list.size() > 0 ? list.get(0) : new RichContent();
   }

   public void refreshHighlight(String id, RichContentType type)
   {
      getEm()

               .createNativeQuery(
                        "update "
                                 + RichContent.TABLE_NAME
                                 + " set highlight = :FALSE where id <> :NOTID AND richContentType_id = :TYPEID ")
               .setParameter("NOTID", id).setParameter("FALSE", false).setParameter("TYPEID", type.getId())
               .executeUpdate();
   }

   public Image getHighlightImage(String type) throws Exception
   {
      try
      {
         Image i = (Image) getEm()
                  .createNativeQuery(
                           "select * from " + Image.TABLE_NAME + " where id = ( "
                                    + "    select " + RichContent.IMAGE_FK + " from "
                                    + RichContent.IMAGES_JOINTABLE_NAME
                                    + " where " + RichContent.TABLE_FK + " = ( "
                                    + "       select id from " + RichContent.TABLE_NAME
                                    + " where highlight = :TRUE and richContentType_id = ("
                                    + "          select id from " + RichContentType.TABLE_NAME
                                    + " where name = :TYPENAME "
                                    + "       ) "
                                    + "    ) "
                                    + " ) ", Image.class)
                  .setParameter("TRUE", true).setParameter("TYPENAME", type).getSingleResult();
         return i;
      }
      catch (NoResultException e)
      {
         return null;
      }
   }

   @Override
   protected void applyRestrictions(Search<RichContent> search, String alias, String separator, StringBuffer sb,
            Map<String, Object> params) throws Exception
   {

      // ACTIVE TYPE
      if (true)
      {
         sb.append(separator).append(alias).append(".richContentType.active = :typeActive ");
         params.put("typeActive", true);
         separator = " and ";
      }

      // FROM
      if (search.getFrom() != null && search.getFrom().getDate() != null)
      {
         sb.append(separator).append(alias).append(".date >= :FROMDATE ");
         params.put("FROMDATE", DateUtils.toBeginOfDay(search.getFrom().getDate()));
         separator = " and ";
      }
      // TO
      if (search.getTo() != null && search.getTo().getDate() != null)
      {
         sb.append(separator).append(alias).append(".date <= :TODATE ");
         params.put("TODATE", DateUtils.toEndOfDay(search.getTo().getDate()));
         separator = " and ";
      }

      // HIGHLIGHT
      if (search.getObj().isHighlight())
      {
         sb.append(separator).append(alias).append(".highlight = :HIGHLIGHT ");
         params.put("HIGHLIGHT", search.getObj().isHighlight());
         separator = " and ";
      }

      // TYPE BY NAME
      if (search.getObj().getRichContentType() != null
               && search.getObj().getRichContentType().getName() != null
               && search.getObj().getRichContentType().getName().trim().length() > 0)
      {
         String[] names = search.getObj().getRichContentType().getName().split(",");
         Set<String> contentTypeNames = new HashSet<String>();
         for (String name : names)
         {
            contentTypeNames.add(name.trim());
         }
         sb.append(separator).append(alias).append(".richContentType.name in ( :typeNames ) ");
         params.put("typeNames", contentTypeNames);
         separator = " and ";
      }

      // AUTHOR
      if (search.getLike().getAuthor() != null && search.getLike().getAuthor().trim().length() > 0)
      {
         sb.append(separator).append(" upper ( ").append(alias).append(".author ) LIKE :AUTHOR ");
         params.put("AUTHOR", likeParam(search.getLike().getAuthor().trim().toUpperCase()));
         separator = " and ";
      }

      // TYPE BY ID
      if (search.getObj().getRichContentType() != null
               && search.getObj().getRichContentType().getId() != null)
      {
         sb.append(separator).append(alias).append(".richContentType.id = :typeId ");
         params.put("typeId", search.getObj().getRichContentType().getId());
         separator = " and ";
      }

      // TAG
      if (search.getObj().getTag() != null
               && search.getObj().getTag().trim().length() > 0)
      {
         String tagName = search.getObj().getTag().trim();

         // TODO better - this is a hack to overcome strange characters in the search form fields (i.e.: Forlì)
         String tagNameCleaned = StringUtils.clean(
                  search.getObj().getTag().trim()).replace('-', ' ');
         Boolean likeMatch = null;
         if (!tagName.equals(tagNameCleaned))
         {
            // if tagName and tagNameCleaned are not the same we perform like-match with tagNameCleaned, to prevent
            // no-results
            likeMatch = true;
         }
         else
         {
            // otherwise we can do perfect-match with the original tagName
            likeMatch = false;
         }

         sb.append(separator).append(alias).append(".id in ( ");
         sb.append(" select T1.richContentId from ")
                  .append(Tag.class.getSimpleName())
                  .append(" T1 where T1.tagName ")
                  .append(likeMatch ? "like" : "=").append(" :TAGNAME ");
         sb.append(" ) ");
         params.put("TAGNAME", likeMatch ? likeParam(tagNameCleaned.trim())
                  : tagName);
         separator = " and ";
      }

      // TAG LIKE
      if (search.getLike().getTagList().size() > 0)
      {
         sb.append(separator).append(" ( ");
         for (int i = 0; i < search.getLike().getTagList().size(); i++)
         {
            sb.append(i > 0 ? " or " : "");

            sb.append(alias).append(".id in ( ");
            sb.append(" select T2.richContentId from ")
                     .append(Tag.class.getSimpleName())
                     .append(" T2 where upper ( T2.tagName ) like :TAGNAME")
                     .append(i).append(" ");
            sb.append(" ) ");

            // params.put("TAGNAME" + i, likeParam(search.getObj().getTag()
            // .trim().toUpperCase()));
            params.put("TAGNAME" + i, likeParam(search.getObj().getTagList().get(i)
                     .trim().toUpperCase()));

         }
         sb.append(" ) ");
         separator = " and ";
      }

      // TITLE
      if (search.getLike().getTitle() != null
               && !search.getLike().getTitle().trim().isEmpty())
      {
         sb.append(separator).append(" upper ( ").append(alias).append(".title ) like :likeTitle ");
         params.put("likeTitle", likeParam(search.getLike().getTitle().trim().toUpperCase()));
         separator = " and ";
      }

      // CONTENT (ALSO SEARCHES IN TITLE)
      if (search.getLike().getContent() != null
               && !search.getLike().getContent().trim().isEmpty())
      {
         sb.append(separator);
         sb.append(" ( ");
         sb.append(" upper ( ").append(alias).append(".title ) like :likeContent ");
         sb.append(" or ");
         sb.append(" upper ( ").append(alias).append(".content ) like :likeContent ");
         params.put("likeContent", likeParam(search.getLike().getContent().trim().toUpperCase()));
         separator = " and ";
      }

      super.applyRestrictions(search, alias, separator, sb, params);

   }

   @Override
   public void delete(Object key) throws Exception
   {
      getEm().createNativeQuery("update " + RichContent.TABLE_NAME + " set active = :active where id = :id")
               .setParameter("active", false).setParameter("id", key).executeUpdate();
   }

}
