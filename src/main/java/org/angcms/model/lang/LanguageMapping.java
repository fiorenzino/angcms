package org.angcms.model.lang;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class LanguageMapping implements Serializable
{
   private String pageId;
   private String type;
   private String pageLangName;

   private String langName1;
   private String langId1;

   private String langName2;
   private String langId2;

   private String langName3;
   private String langId3;

   private String langName4;
   private String langId4;

   private String langName5;
   private String langId5;

   @Id
   public String getPageId()
   {
      return pageId;
   }

   public void setPageId(String pageId)
   {
      this.pageId = pageId;
   }

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }

   public String getPageLangName()
   {
      return pageLangName;
   }

   public void setPageLangName(String pageLangName)
   {
      this.pageLangName = pageLangName;
   }

   public String getLangName1()
   {
      return langName1;
   }

   public void setLangName1(String langName1)
   {
      this.langName1 = langName1;
   }

   public String getLangId1()
   {
      return langId1;
   }

   public void setLangId1(String langId1)
   {
      this.langId1 = langId1;
   }

   public String getLangName2()
   {
      return langName2;
   }

   public void setLangName2(String langName2)
   {
      this.langName2 = langName2;
   }

   public String getLangId2()
   {
      return langId2;
   }

   public void setLangId2(String langId2)
   {
      this.langId2 = langId2;
   }

   public String getLangName3()
   {
      return langName3;
   }

   public void setLangName3(String langName3)
   {
      this.langName3 = langName3;
   }

   public String getLangId3()
   {
      return langId3;
   }

   public void setLangId3(String langId3)
   {
      this.langId3 = langId3;
   }

   public String getLangName4()
   {
      return langName4;
   }

   public void setLangName4(String langName4)
   {
      this.langName4 = langName4;
   }

   public String getLangId4()
   {
      return langId4;
   }

   public void setLangId4(String langId4)
   {
      this.langId4 = langId4;
   }

   public String getLangName5()
   {
      return langName5;
   }

   public void setLangName5(String langName5)
   {
      this.langName5 = langName5;
   }

   public String getLangId5()
   {
      return langId5;
   }

   public void setLangId5(String langId5)
   {
      this.langId5 = langId5;
   }
}
