package all.test.util;

import org.angcms.model.banner.Banner;
import org.angcms.model.banner.BannerTypology;
import org.angcms.model.base.attachment.Image;
import org.angcms.model.richcontent.RichContent;
import org.angcms.model.richcontent.Tag;
import org.angcms.model.richcontent.type.RichContentType;

import java.util.Date;

public class TestUtils
{

   public static final String TARGET_HOST = "http://localhost:8080";

   public static Banner newBanner()
   {
      Image image = new Image();
      image.setName("banner.jpg");
      image.setActive(true);
      image.setDescription("banner di prova");

      BannerTypology bannerTypology = new BannerTypology();
      bannerTypology.setId(1L);
      Banner newBanner = new Banner();
      newBanner.setImage(image);
      newBanner.setBannerTypology(bannerTypology);
      newBanner.setName("" + System.currentTimeMillis());
      newBanner.setActive(true);
      newBanner.setOnline(true);
      return newBanner;
   }

   public static BannerTypology newBannerTypology()
   {
      BannerTypology bannerTypology = new BannerTypology();
      bannerTypology.setDescription("news");
      bannerTypology.setName("news");
      return bannerTypology;
   }

   public static RichContentType newRichContentType()
   {
      RichContentType richContentType = new RichContentType();
      richContentType.setName("blog");
      richContentType.setActive(true);
      return richContentType;
   }

   public static RichContent newRichContent(String richContentTypeId)
   {
      RichContentType richContentType = newRichContentType();
      richContentType.setId(Long.valueOf(richContentTypeId));

      Image image = new Image();
      image.setName("post.jpg");
      image.setActive(true);
      image.setDescription("post di prova");

      Tag tag = new Tag();
      tag.setTagName("paura");
      tag.setDay(4);
      tag.setMonth(5);
      tag.setYear(2015);

      RichContent richContent = new RichContent();
      richContent.getTags();
      richContent.setRichContentType(richContentType);
      richContent.setActive(true);
      richContent.setAuthor("fiorenzo");
      richContent.setDate(new Date());
      richContent.setContent("<h1>primo post della stagione</h1>");
      richContent.setTitle("primo post");
      richContent.setPreview("primo post della stagione");
      richContent.addImage(image);
      richContent.setTags("primo" + RichContent.TAG_SEPARATOR + "post" + RichContent.TAG_SEPARATOR + "stagione");
      return richContent;
   }

}
