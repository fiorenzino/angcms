package org.angcms.service.rs;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.angcms.repository.richcontent.TagRepository;
import org.giavacms.api.management.AppConstants;

/**
 * Created by fiorenzo on 30/04/15. IMPLEMENTARE TUTTI I METODI NEL CONTROLLER
 */
@Path(AppConstants.BASE_PATH + AppConstants.TAG_PATH)
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagServiceRs implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Inject
   TagRepository tagRepository;

}
