package com.asptt.core.actualite;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.asptt.core.adherent.ActuTransformer;
import com.asptt.core.document.DocumentTransformer;
import com.asptt.core.server.dao.ActuDao;
import com.asptt.core.server.dao.document.DocumentDao;
import com.asptt.core.server.dao.entity.ActuEntity;
import com.asptt.core.server.dao.entity.ActuStatutEnum;
import com.asptt.core.server.dao.entity.document.DocumentEntity;
import com.asptt.core.server.dao.entity.field.ContentEntityFields;
import com.asptt.core.server.dao.entity.field.DocumentEntityFields;
import com.asptt.core.server.dao.entity.structure.ContentDataKindEnum;
import com.asptt.core.server.dao.entity.structure.ContentEntity;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;
import com.asptt.core.server.dao.structure.ContentDao;
import com.asptt.core.shared.actu.ActuUi;
import com.asptt.core.shared.document.DocumentUi;
import com.asptt.core.util.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

@Path("/actualites")
@Produces("application/json")
public class ActualiteService {

	private static final Logger LOG = Logger.getLogger(ActualiteService.class
			.getName());
	private static final int ACTU_PAR_PAGE = 10;
	
	private ActuDao dao = new ActuDao();
	private DocumentDao documentDao = new DocumentDao();
	private ContentDao contentDao = new ContentDao();

	@GET
	@Path("list/{pageNumber}")
	public ActualitesResult getActualites(
			@PathParam("pageNumber") Integer pPageNumber) {
		// Calcul de l'index
		int maxNumber = ACTU_PAR_PAGE * pPageNumber;
		
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		List<ActuEntity> lEntities = dao.find(criteria, 0, maxNumber);
		ActuTransformer actuTransformer = new ActuTransformer();
		DocumentTransformer documentTransformer = new DocumentTransformer();
		List<ActuUi> actualites = new ArrayList<ActuUi>(lEntities.size());
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = actuTransformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			lDocumentCriteria.add(new CriterionDao<Long>(
					DocumentEntityFields.MENU, entity.getId(), Operator.EQUAL));
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			
			// Image
			List<CriterionDao<? extends Object>> imageCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
			imageCriteria.add(new CriterionDao<Long>(ContentEntityFields.MENU, entity.getId(), Operator.EQUAL));
			List<ContentEntity> contentEntities = contentDao.find(imageCriteria);
			if(CollectionUtils.isNotEmpty(contentEntities)) {
				lUi.setImage(contentEntities.get(0).getData().getBytes());
			}
			actualites.add(lUi);
		}
		ActualitesResult result = new ActualitesResult();
		result.setActualites(actualites);
		return result;
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void publish(
			@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
			@FormDataParam("data") String pData,
			@FormDataParam("file") InputStream pFileInput,
			@FormDataParam("file") FormDataContentDisposition pFileDisposition,
			@FormDataParam("file") FormDataBodyPart pBodyPart) {
		try {
			String unscape = URLDecoder.decode(pData, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			ActualitePublishParameters parameters = mapper.readValue(unscape, ActualitePublishParameters.class);
			ActuEntity entity = null;
			if (parameters.getId() != null && parameters.getId() > 0) {
				entity = dao.get(parameters.getId());
			} else {
				entity = new ActuEntity();
				entity.setCreatedBy(parameters.getUser());
			}
			entity.setUpdatedBy(parameters.getUser());

			entity.setTitle(parameters.getTitle());
			entity.setCreationDate(parameters.getBegin().toDate());
			entity.setExpiration(parameters.getEnd().toDate());
			if (StringUtils.isBlank(parameters.getImage())) {
				entity.setImageUrl("img/actu_defaut.jpg");
			} else {
				entity.setImageUrl(parameters.getImage());
			}
			entity.setContent(new Text(parameters.getContent()));
			if (parameters.isDraft()) {
				entity.setStatut(ActuStatutEnum.DRAFT.name());
			} else {
				entity.setStatut(ActuStatutEnum.PUBLIE.name());
			}
			
			ActuEntity actuCreated = dao.save(entity);
			
			if (pFileInput != null) {
				String fileName = pFileDisposition.getFileName();

				ContentEntity content = contentDao.findByMenu(actuCreated.getId());
				if (content == null) {
					content = new ContentEntity(fileName, new Blob(IOUtils.toByteArray(pFileInput)),
							ContentDataKindEnum.IMAGE.name(), actuCreated.getId());
					ContentEntity lSavedEntity = contentDao.save(content);
					if (!Utils.isImageMediaType(pBodyPart.getMediaType())) {
						content.setKind(ContentDataKindEnum.DOCUMENT.name());
						DocumentEntity lDocumentEntity = new DocumentEntity(parameters.getTitle(), StringUtils.EMPTY,
								pBodyPart.getMediaType().toString(), fileName, new Date(), lSavedEntity.getId(),
								actuCreated.getId());
						documentDao.save(lDocumentEntity);
					}
				} else {
					content.setData(new Blob(IOUtils.toByteArray(pFileInput)));
					contentDao.save(content);
					if (!Utils.isImageMediaType(pBodyPart.getMediaType())) {
						DocumentEntity document = documentDao.findByMenu(actuCreated.getId());
						document.setFileName(fileName);
						document.setMimeType(pBodyPart.getMediaType().toString());
						document.setTitle(parameters.getTitle());
						documentDao.save(document);
					}
				}
			}

			if (parameters.isFacebook()) {
				Document document = Jsoup.parse(parameters.getContent());
				String facebookText = document.text();
				FacebookClient facebookClient = new DefaultFacebookClient(
						"CAAKgVklrgZCoBAOngDFrI5X24JXMRFjyKMzCXRoZAz26KT7XHaenWxU5MLtkZCbu9vk09UNYkwZB6YivEUftisnO7i7FYDcgPo7VnW0fELT9gRNwucy3wtkG9ms0Cq4KaCxqIJ70Sj1QSJ9pdg17YVcRtaSoV52YbhzPlVNAfVGnDcuKyW5c");
				FacebookType publishMessageResponse = facebookClient.publish("710079642422594/feed", FacebookType.class,
						Parameter.with("message", facebookText));
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Cannot publish actualité", e);
		}
	}
	
	@DELETE
	@Path("{actualiteId}")
	public void remove(@PathParam("actualiteId") Long actualiteId) {
		dao.delete(actualiteId);
	}
	
	@GET
	@Path("all")
	public List<ActuEntity> findAll() {
		List<ActuEntity> actualites = dao.getAll();
		return actualites;
	}
}