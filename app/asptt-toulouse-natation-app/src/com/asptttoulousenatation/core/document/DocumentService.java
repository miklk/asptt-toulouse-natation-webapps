package com.asptttoulousenatation.core.document;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.asptttoulousenatation.core.adherent.AdherentBeanTransformer;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

@Path("/documents")
@Produces("application/json")
public class DocumentService {

	private DocumentDao documentDao = new DocumentDao();
	private DocumentTransformer documentTransformer = new DocumentTransformer();

	@GET
	@Path("list")
	@Consumes("application/json")
	public DocumentListResult getDocuments(
			@QueryParam("parameters") DocumentListAction action) {
		DocumentListResult result = new DocumentListResult();
		//TODO orderby
		List<DocumentEntity> entities = documentDao.getAll();
		result.setDocuments(documentTransformer.toUi(entities));
		return result;
	}
	
	@Path("{documentId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public DocumentUi getDocument(
			@PathParam("documentId") Long documentId) {
		return documentTransformer.toUi(documentDao.get(documentId));
	}
}
