package com.asptttoulousenatation.core.document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentLibelleJDao;
import com.asptttoulousenatation.core.server.dao.document.LibelleDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentLibelleJEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.LibelleEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentLibelleJEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.LibelleEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Blob;

@Path("/documents")
@Produces("application/json")
public class DocumentService {

	private DocumentDao documentDao = new DocumentDao();
	private DocumentTransformer documentTransformer = new DocumentTransformer();

	private DocumentLibelleJDao documentLibelleJDao = new DocumentLibelleJDao();
	private LibelleDao libelleDao = new LibelleDao();

	private ContentDao contentDao = new ContentDao();

	@GET
	public DocumentListResult getDocuments() {
		DocumentListResult result = new DocumentListResult();
		List<DocumentEntity> entities = documentDao.getAll();

		result.setDocuments(documentTransformer.toUi(entities));
		Collections.sort(result.getDocuments(), new Comparator<DocumentUi>() {

			@Override
			public int compare(DocumentUi pO1, DocumentUi pO2) {
				return pO1.getCreationDate().compareTo(pO2.getCreationDate()) * -1;
			}
		});

		// Getting libelle
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> criterion = new CriterionDao<Long>();
		criterion.setEntityField(DocumentLibelleJEntityFields.DOCUMENT);
		criterion.setOperator(Operator.EQUAL);
		criteria.add(criterion);
		for (DocumentUi document : result.getDocuments()) {
			criterion.setValue(document.getId());
			List<DocumentLibelleJEntity> joins = documentLibelleJDao
					.find(criteria);
			if (CollectionUtils.isNotEmpty(joins)) {
				for (DocumentLibelleJEntity join : joins) {
					LibelleEntity libelle = libelleDao.get(join.getLibelle());
					document.addLibelle(libelle.getIntitule());
				}
			}
		}
		return result;
	}

	@Path("{documentId}")
	@GET
	public DocumentUi getDocument(@PathParam("documentId") Long documentId) {
		return documentTransformer.toUi(documentDao.get(documentId));
	}

	@PUT
	@Path("{documentId}")
	@Consumes("application/json")
	public DocumentUpdateResult update(
			@PathParam("documentId") Long documentId,
			DocumentUpdateAction pAction) {
		DocumentUpdateResult result = new DocumentUpdateResult();
		DocumentEntity entity = documentDao.get(documentId);
		if (entity != null) {
			entity.setTitle(pAction.getTitle());
			entity.setSummary(pAction.getSummary());
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					DocumentLibelleJEntityFields.DOCUMENT, entity.getId(),
					Operator.EQUAL));
			List<DocumentLibelleJEntity> joins = documentLibelleJDao
					.find(criteria);
			for (DocumentLibelleJEntity join : joins) {
				documentLibelleJDao.delete(join);
			}
			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			CriterionDao<String> criterion = new CriterionDao<String>();
			criterion.setEntityField(LibelleEntityFields.INTITULE);
			criterion.setOperator(Operator.EQUAL);
			criteria.add(criterion);
			if (CollectionUtils.isNotEmpty(pAction.getLibelles())) {
				for (String libelle : pAction.getLibelles()) {
					criterion.setValue(libelle);
					List<LibelleEntity> entities = libelleDao.find(criteria,
							new OrderDao(LibelleEntityFields.INTITULE,
									OrderOperator.ASC));
					if (CollectionUtils.isNotEmpty(entities)) {
						LibelleEntity libelleEntity = entities.get(0);
						DocumentLibelleJEntity join = new DocumentLibelleJEntity();
						join.setDocument(entity.getId());
						join.setLibelle(libelleEntity.getId());
						documentLibelleJDao.save(join);
					}
				}
			}
			documentDao.save(entity);
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}

	@DELETE
	@Path("{documentId}")
	public void remove(@PathParam("documentId") Long documentId) {
		List<CriterionDao<? extends Object>> criteriaJ = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteriaJ.add(new CriterionDao<Long>(
				DocumentLibelleJEntityFields.DOCUMENT, documentId, Operator.EQUAL));
		List<DocumentLibelleJEntity> joins = documentLibelleJDao
				.find(criteriaJ);
		for(DocumentLibelleJEntity join: joins) {
			documentLibelleJDao.delete(join);
		}
		documentDao.delete(documentId);
	}

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadDocument(
			@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
			@FormDataParam("action") String pAction,
			@FormDataParam("file") InputStream pFileInput,
			@FormDataParam("file") FormDataContentDisposition pFileDisposition,
			@FormDataParam("file") FormDataBodyPart pBodyPart)
			throws IOException {

		String unscape = URLDecoder.decode(pAction, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		DocumentUploadAction action = mapper.readValue(unscape,
				DocumentUploadAction.class);

		String fileName = pFileDisposition.getFileName();

		ContentEntity lEntity = new ContentEntity(fileName, new Blob(
				IOUtils.toByteArray(pFileInput)),
				ContentDataKindEnum.DOCUMENT.toString(), 0l);
		ContentEntity lSavedEntity = contentDao.save(lEntity);

		DocumentEntity lDocumentEntity = new DocumentEntity(action.getTitle(),
				action.getSummary(), pBodyPart.getMediaType().toString(),
				fileName, new Date(), lSavedEntity.getId(), 0l);
		DocumentEntity entity = documentDao.save(lDocumentEntity);

		if (CollectionUtils.isNotEmpty(action.getLibelles())) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<String> criterion = new CriterionDao<String>();
			criterion.setEntityField(LibelleEntityFields.INTITULE);
			criterion.setOperator(Operator.EQUAL);
			criteria.add(criterion);
			for (String libelle : action.getLibelles()) {
				criterion.setValue(libelle);
				List<LibelleEntity> entities = libelleDao.find(criteria,
						new OrderDao(LibelleEntityFields.INTITULE,
								OrderOperator.ASC));
				if (CollectionUtils.isNotEmpty(entities)) {
					LibelleEntity libelleEntity = entities.get(0);
					DocumentLibelleJEntity join = new DocumentLibelleJEntity();
					join.setDocument(entity.getId());
					join.setLibelle(libelleEntity.getId());
					documentLibelleJDao.save(join);
				}
			}
		}
	}

	@Path("/byLibelles")
	@GET
	public DocumentByLibelleResult getDocumentsByLibelle(@QueryParam("libelles") List<String> pLibelles) {
		DocumentByLibelleResult result = new DocumentByLibelleResult();

		List<LibelleEntity> libelleEntities = libelleDao.getAll();
		for (final String libelle : pLibelles) {
			@SuppressWarnings("unchecked")
			Collection<LibelleEntity> selectedLibelles = CollectionUtils.select(libelleEntities, new Predicate() {
				
				@Override
				public boolean evaluate(Object pArg0) {
					String label = ((LibelleEntity) pArg0).getIntitule();
					return label.contains(libelle);
				}
			});
			for(LibelleEntity selectedLibelle: selectedLibelles) {
				List<CriterionDao<? extends Object>> criteriaJ = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaJ.add(new CriterionDao<Long>(
						DocumentLibelleJEntityFields.LIBELLE, selectedLibelle
								.getId(), Operator.EQUAL));
				List<DocumentLibelleJEntity> joins = documentLibelleJDao
						.find(criteriaJ);
				for (DocumentLibelleJEntity libelleId : joins) {
					DocumentEntity documentEntity = documentDao.get(libelleId.getDocument());
					DocumentUi document = documentTransformer.toUi(documentEntity);
					String[] labels = selectedLibelle.getIntitule().split("/");
					result.add(labels[labels.length - 1], document);
				}
			}
		}
		return result;
	}
}