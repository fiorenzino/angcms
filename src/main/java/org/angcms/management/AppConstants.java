package org.angcms.management;

public class AppConstants extends org.giavacms.api.management.AppConstants
{
   public static final String APP_NAME = "gestione-pdf";

   public static String API_VERSION = "1.0.0";

   public static final String INVITI_PATH = "/v1/inviti";
   public static final String FILE_PATH = "/v1/file";

   public static final String ADMIN_ROLE = "Administrator";

   public static final String QUEUE_PDF_STRIPPER = "java:/jms/queue/gestionepdf.pdfstripper";
   public static final String QUEUE_INDEXER = "java:/jms/queue/gestionepdf.indexer";

   public static final String EMAIL_SEPARATOR = ",|;";

   public static final String DOCUMENTO_UUID = "documentoUuid";
   public static final String DOCUMENTO_SEARCH = "search";
   public static final String SHARED_PATH_SERVLET = "/static";
   public static final String TOKEN_PARAMETER_NAME = "token";
   public static final String FORBIDDEN_PAGE = "/403.html";

   // public static final String START_PAGE = "index.html";
   public static final String START_PAGE = "frame.jsp";

}
