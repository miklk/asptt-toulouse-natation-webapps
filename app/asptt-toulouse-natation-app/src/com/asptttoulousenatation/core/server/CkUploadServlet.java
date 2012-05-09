package com.asptttoulousenatation.core.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.FilesApiFileItemFactory;
import gwtupload.server.gae.FilesApiFileItemFactory.FilesAPIFileItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;

public class CkUploadServlet extends UploadAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3501833742412368003L;

	private static final Logger LOG = Logger.getLogger(CkUploadServlet.class
			.getName());

	private ContentDao dao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();

	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {

		String lTitle = request.getParameter("fileTitle");
		String lSummary = request.getParameter("fileSummary");
		String lMenuId = request.getParameter("menuId");
		FileItem lFile = sessionFiles.get(0);
		ContentEntity lEntity = new ContentEntity(lTitle,
				new Blob(lFile.get()), ContentDataKindEnum.DOCUMENT.toString(),
				Long.valueOf(lMenuId));
		ContentEntity lSavedEntity = dao.save(lEntity);
		DocumentEntity lDocumentEntity = new DocumentEntity(lTitle, lSummary,
				lFile.getContentType(), lFile.getName(), new Date(),
				lSavedEntity.getId(), Long.valueOf(lMenuId));
		documentDao.save(lDocumentEntity);
		return Long.toString(lSavedEntity.getId());
	}

	@Override
	protected FileItemFactory getFileItemFactory(int requestSize) {
		return new FilesApiFileItemFactory();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String bkey = request.getParameter("blob-key");
		if (bkey != null) {
			LOG.info("Serving a blobstore file with the key:" + bkey);
			FilesAPIFileItem.getBlobstoreService().serve(
					new BlobKey(request.getParameter("blob-key")), response);
		} else {
			super.doGet(request, response);
		}
	}
}
