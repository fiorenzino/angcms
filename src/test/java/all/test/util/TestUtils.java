package all.test.util;

import org.angcms.model.banner.Banner;
import org.angcms.model.banner.BannerTypology;
import org.angcms.model.base.attachment.Image;

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

}
