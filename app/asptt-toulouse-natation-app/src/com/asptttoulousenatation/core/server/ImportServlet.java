package com.asptttoulousenatation.core.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.FilesApiFileItemFactory;
import gwtupload.server.gae.FilesApiFileItemFactory.FilesAPIFileItem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;

import com.asptttoulousenatation.ImportInscriptionBean;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ImportServlet extends UploadAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3501833742412368003L;

	private static final Logger LOG = Logger.getLogger(ImportServlet.class
			.getName());

	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		try {
			FileItem lFile = sessionFiles.get(0);
			String json = "";
			json = new String(lFile.get(), "UTF-8");

			Gson gson = new Gson();

			Type listType = new TypeToken<ArrayList<ImportInscriptionBean>>() {
			}.getType();
			List<ImportInscriptionBean> data = gson.fromJson(json, listType);
			LOG.warning("Import #" + data.size());

			InscriptionDao dao = new InscriptionDao();
			GroupDao lGroupDao = new GroupDao();
			for (ImportInscriptionBean bean : data) {
				InscriptionEntity entity = new InscriptionEntity();
				entity.setNom(bean.getNom().trim().toUpperCase());
				entity.setPrenom(bean.getPrenom().trim().toUpperCase());
				String[] naissance = bean.getNaissance().split("/");
				entity.setDatenaissance(naissance[2] + "-" + naissance[1] + "-"
						+ naissance[0]);

				// Exists ?
				InscriptionDao inscriptionDao = new InscriptionDao();
				List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
						3);
				lCriteria.add(new CriterionDao<String>(
						InscriptionEntityFields.NOM, entity.getNom(),
						Operator.EQUAL));
				lCriteria.add(new CriterionDao<String>(
						InscriptionEntityFields.PRENOM, entity.getPrenom(),
						Operator.EQUAL));
				lCriteria.add(new CriterionDao<String>(
						InscriptionEntityFields.DATENAISSANCE, entity
								.getDatenaissance(), Operator.EQUAL));
				List<InscriptionEntity> adherents = inscriptionDao
						.find(lCriteria);
				if (CollectionUtils.isNotEmpty(adherents)) {
					entity = adherents.get(0);
				}

				List<CriterionDao<? extends Object>> groupCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				groupCriteria.add(new CriterionDao<String>(
						GroupEntityFields.TITLE, bean.getGroupe(),
						Operator.EQUAL));
				List<GroupEntity> groups = lGroupDao.find(groupCriteria);
				if (CollectionUtils.isNotEmpty(groups)) {
					entity.setNouveauGroupe(groups.get(0).getId());
				}
				InscriptionEntity entitySaved = dao.save(entity);
				LOG.warning("Imported: " + entitySaved.getId());
			}
		} catch (UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
		sessionFiles.remove(0);
		return "";
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
