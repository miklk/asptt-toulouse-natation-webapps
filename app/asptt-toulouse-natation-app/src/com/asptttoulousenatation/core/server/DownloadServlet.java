package com.asptttoulousenatation.core.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;

public class DownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3501833742412368003L;

	private static final Logger LOG = Logger.getLogger(DownloadServlet.class
			.getName());

	private ContentDao dao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String lDocumentId = request.getParameter("documentId");
		String lFileId = request.getParameter("fileId");
		if (!lDocumentId.isEmpty()
				&& !lFileId.isEmpty()
				) {
			LOG.info("Document:" + lFileId);
			DocumentEntity lDocument = documentDao.get(Long
					.valueOf(lDocumentId));
			
			ContentEntity lContent = dao.get(lDocument.getDataId());

			// Build response
			response.setContentType(lDocument.getMimeType());
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ lDocument.getFileName() + "\"");
			ServletOutputStream lOut = response.getOutputStream();
			lOut.write(lContent.getData().getBytes());

		}
	}
}
