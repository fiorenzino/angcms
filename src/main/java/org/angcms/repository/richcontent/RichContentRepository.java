package org.angcms.repository.richcontent;

import org.angcms.model.base.attachment.Image;
import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.Tag;
import org.angcms.model.richcontent.type.RichContentType;
import org.angcms.repository.BaseRepository;
import org.angcms.util.StringUtils;
import org.angcms.util.TimeUtils;
import org.giavacms.api.repository.Search;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named
@Stateless
@LocalBean
public class RichContentRepository extends BaseRepository<RichContent>
{

   private static final long serialVersionUID = 1L;

   @Override
   protected RichContent prePersist(RichContent n)
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

   public RichContent findLast()
   {
      RichContent ret = new RichContent();
      try
      {
         ret = (RichContent) getEm()
                  .createQuery(
                           "select p from "
                                    + RichContent.class.getSimpleName()
                                    + " p order by p.date desc ")
                  .setMaxResults(1).getSingleResult();
         if (ret == null)
         {
            return null;
         }
         else
         {
            return this.fetch(ret.getId());
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return ret;
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
      try
      {
         getEm()

                  .createNativeQuery(
                           "update "
                                    + RichContent.TABLE_NAME
                                    + " set highlight = :FALSE where id <> :NOTID AND richContentType_id = :TYPEID ")
                  .setParameter("NOTID", id).setParameter("FALSE", false).setParameter("TYPEID", type.getId())
                  .executeUpdate();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage(), e);
      }
   }

   @SuppressWarnings("unchecked")
   public Image getHighlightImage(String type)
   {
      try
      {
         List<RichContent> nl = getEm()
                  .createQuery(
                           "select p from "
                                    + RichContent.class.getSimpleName()
                                    + " p where p.highlight = :STATUS and p.richContentType.name = :TYPE ")
                  .setParameter("STATUS", true).setParameter("TYPE", type).setMaxResults(1).getResultList();
         if (nl == null || nl.size() == 0 || nl.get(0).getImages() == null
                  || nl.get(0).getImages().size() == 0)
         {
            return null;
         }
         return nl.get(0).getImages().get(0);

      }
      catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   protected String getDefaultOrderBy()
   {
      return "date desc";
   }

   protected void applyRestrictionsNative(Search<RichContent> search, String pageAlias, String templateImplAlias,
            String richContentAlias,
            String richContentTypeAlias, String separator, StringBuffer sb,
            Map<String, Object> params)
   {

      // ACTIVE TYPE
      if (true)
      {
         sb.append(separator).append(richContentTypeAlias).append(".active = :activeContentType ");
         params.put("activeContentType", true);
         separator = " and ";
      }

      // FROM
      if (search.getFrom() != null && search.getFrom().getDate() != null)
      {
         sb.append(separator).append(richContentTypeAlias).append(".date >= :FROMDATE ");
         params.put("FROMDATE", search.getFrom().getDate());
         separator = " and ";
      }
      // TO
      if (search.getTo() != null && search.getTo().getDate() != null)
      {
         sb.append(separator).append(richContentTypeAlias).append(".date <= :TODATE ");
         params.put("TODATE", search.getTo().getDate());
         separator = " and ";
      }

      // HIGHLIGHT
      if (search.getObj().isHighlight())
      {
         sb.append(separator).append(richContentAlias).append(".highlight = :HIGHLIGHT ");
         params.put("HIGHLIGHT", search.getObj().isHighlight());
         separator = " and ";
      }

      // TYPE BY NAME
      if (search.getObj().getRichContentType() != null
               && search.getObj().getRichContentType().getName() != null
               && search.getObj().getRichContentType().getName().length() > 0)
      {
         if (search.getObj().getRichContentType().getName().contains(","))
         {
            String[] names = search.getObj().getRichContentType().getName().split(",");
            StringBuffer orBuffer = new StringBuffer();
            String orSeparator = "";
            for (int i = 0; i < names.length; i++)
            {
               if (names[i].trim().length() > 0)
               {
                  orBuffer.append(orSeparator).append(richContentTypeAlias).append(".name = :NAMETYPE" + i + " ");
                  params.put("NAMETYPE" + i, names[i].trim());
                  orSeparator = " or ";
               }
            }
            if (orBuffer.length() > 0)
            {
               sb.append(separator).append(" ( ").append(orBuffer).append(" ) ");
               separator = " and ";
            }
         }
         else
         {
            sb.append(separator).append(richContentTypeAlias).append(".name = :NAMETYPE ");
            params.put("NAMETYPE", search.getObj().getRichContentType()
                     .getName());
            separator = " and ";
         }

      }

      // AUTHOR
      if (search.getObj().getAuthor() != null && search.getObj().getAuthor().trim().length() > 0)
      {
         sb.append(separator).append(" upper ( ").append(richContentAlias).append(".author ) LIKE :AUTHOR ");
         params.put("AUTHOR", likeParam(search.getObj().getAuthor().trim().toUpperCase()));
         separator = " and ";
      }

      // TYPE BY ID
      if (search.getObj().getRichContentType() != null
               && search.getObj().getRichContentType().getId() != null)
      {
         sb.append(separator).append(richContentTypeAlias).append(".id = :IDTYPE ");
         params.put("IDTYPE", search.getObj().getRichContentType().getId());
         separator = " and ";
      }

      // TAG
      if (search.getObj().getTag() != null
               && search.getObj().getTag().trim().length() > 0)
      {
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

            sb.append(separator).append(richContentAlias).append(".id in ( ");
            sb.append(" select distinct T1.richContent_id from ")
                     .append(Tag.TABLE_NAME)
                     .append(" T1 where T1.tagName ")
                     .append(likeMatch ? "like" : "=").append(" :TAGNAME ");
            sb.append(" ) ");
            params.put("TAGNAME", likeMatch ? likeParam(tagNameCleaned)
                     : tagName);
            separator = " and ";
         }
      }

      // TAG LIKE
      if (search.getObj().getTagList().size() > 0)
      {
         sb.append(separator).append(" ( ");
         for (int i = 0; i < search.getObj().getTagList().size(); i++)
         {
            sb.append(i > 0 ? " or " : "");

            // TODO benchmark - try which version runs faster
            boolean useJoin = false;
            if (useJoin)
            {
               sb.append(richContentAlias).append(".id in ( ");
               sb.append(" select distinct T2.richContent_id from ")
                        .append(Tag.TABLE_NAME)
                        .append(" T2 where upper ( T2.tagName ) like :TAGNAME")
                        .append(i).append(" ");
               sb.append(" ) ");
            }
            else
            {
               sb.append(" upper ( ").append(richContentAlias).append(".tags ) like :TAGNAME").append(i)
                        .append(" ");
            }

            // params.put("TAGNAME" + i, likeParam(search.getObj().getTag()
            // .trim().toUpperCase()));
            params.put("TAGNAME" + i, likeParam(search.getObj().getTagList().get(i)
                     .trim().toUpperCase()));

         }
         sb.append(" ) ");
         separator = " and ";
      }

      // TITLE --> ALSO SEARCH IN CUSTOM FIELDS
      String customLike = null;
      if (search.getObj().getTitle() != null
               && !search.getObj().getTitle().trim().isEmpty())
      {
         customLike = "upper ( " + richContentAlias + ".content ) like :LIKETEXTCUSTOM ";
         params.put("LIKETEXTCUSTOM", likeParam(search.getObj().getTitle().trim().toUpperCase()));
      }

   }

}
