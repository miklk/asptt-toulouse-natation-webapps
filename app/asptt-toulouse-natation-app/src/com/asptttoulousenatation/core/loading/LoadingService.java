package com.asptttoulousenatation.core.loading;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;
import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

@Path("/loading")
@Produces("application/json")
public class LoadingService {

	private static final Logger LOG = Logger.getLogger(LoadingService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private MenuDao menuDao = new MenuDao();
	private AreaDao areaDao = new AreaDao();
	private ActuDao actuDao = new ActuDao();
	private DocumentDao documentDao = new DocumentDao();

	@GET
	public LoadingResult getLoading() {

		Long startTime = System.currentTimeMillis();
		LoadingResult result = new LoadingResult();
		// Structure
		List<CriterionDao<? extends Object>> lAreaSelectionCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<String> lAreaSelectionCriterion = new CriterionDao<String>(
				AreaEntityFields.PROFILE, ProfileEnum.PUBLIC.toString(),
				Operator.EQUAL);
		lAreaSelectionCriteria.add(lAreaSelectionCriterion);
		// Order
		OrderDao lOrderDao = new OrderDao(AreaEntityFields.ORDER,
				OrderDao.OrderOperator.ASC);
		List<AreaEntity> lAreaEntities = areaDao.find(lAreaSelectionCriteria,
				lOrderDao);
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);
		lCriteria.add(new CriterionDao<Boolean>(MenuEntityFields.DISPLAY,
				Boolean.TRUE, Operator.EQUAL));
		OrderDao lMenuOrder = new OrderDao(MenuEntityFields.ORDER,
				OrderDao.OrderOperator.ASC);

		for (AreaEntity lAreaEntity : lAreaEntities) {
			LoadingMenuUi lMenuLoadingUi = new LoadingMenuUi(
					lAreaEntity.getTitle());
			// Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao
					.find(lCriteria, lMenuOrder);
			if (lMenuEntities != null && lMenuEntities.size() == 1) {
				MenuEntity firstMenuEntity = lMenuEntities.iterator().next();
				if (firstMenuEntity.getParent() == null
						&& firstMenuEntity.getAlone() != null
						&& firstMenuEntity.getAlone()) {
					lMenuLoadingUi.setHasSubMenu(false);
				}
			} else {
				for (MenuEntity lMenuEntity : lMenuEntities) {
					LoadingMenuUi lMenuLoadingUi2 = new LoadingMenuUi(
							lMenuEntity.getTitle());
					if (lMenuEntity.getParent() == null) {
						// Retrieve sub menu
						for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
							MenuEntity lSubMenu = menuDao.get(lSubMenuId);
							lMenuLoadingUi2.addSubMenu(new LoadingMenuUi(
									lSubMenu.getTitle()));
						}
					}
					lMenuLoadingUi.addSubMenu(lMenuLoadingUi2);
				}
			}
			result.addMenu(lMenuLoadingUi);
		}

		// Actu
		List<ActuEntity> lEntities = actuDao.getAll(0, 7);
		ActuTransformer actuTransformer = new ActuTransformer();
		DocumentTransformer documentTransformer = new DocumentTransformer();
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = actuTransformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
			lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
			lDocumentCriterion.setOperator(Operator.EQUAL);
			lDocumentCriteria.add(lDocumentCriterion);
			lDocumentCriterion.setValue(entity.getId());
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			result.addActualite(lUi);
		}

		// Album Picasa
		// getAlbums(result);
		getAlbumsFake(result);
		Long endTime = System.currentTimeMillis();
		LOG.info("Loading duration: " + (endTime - startTime) + " ms");
		return result;
	}

	@Path("/albums")
	@GET
	public LoadingAlbumsUi getAlbums() {
		LoadingAlbumsUi result = new LoadingAlbumsUi();
		try {
			PicasawebService myService = new PicasawebService("asptt_test");
			URL feedUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/113747450706652808889?kind=album");

			Query myQuery = new Query(feedUrl);

			UserFeed searchResultsFeed = myService.query(myQuery,
					UserFeed.class);
			int maxAlbum = 5;
			ListIterator<AlbumEntry> lEntryIt = searchResultsFeed
					.getAlbumEntries().listIterator();
			Set<String> excludedAlbum = new HashSet<String>();
			excludedAlbum.add("Profile Photos");
			excludedAlbum.add("Scrapbook Photos");
			while (lEntryIt.hasNext() && maxAlbum > 0) {
				AlbumEntry adaptedEntry = lEntryIt.next();
				AlbumEntry lAlbum = (AlbumEntry) adaptedEntry;
				if (!excludedAlbum.contains(lAlbum.getTitle().getPlainText())) {
					String feedHref = getLinkByRel(lAlbum.getLinks(),
							Link.Rel.FEED);
					AlbumFeed lAlbumEntries = myService.query(new Query(
							new URL(feedHref)), AlbumFeed.class);
					LoadingAlbumUi lAlbumUi = new LoadingAlbumUi(
							lAlbum.getGphotoId(), lAlbum.getTitle()
									.getPlainText(), lAlbumEntries
									.getPhotoEntries().get(0)
									.getMediaContents().get(0).getUrl());
					for (PhotoEntry photo : lAlbumEntries.getPhotoEntries()) {
						lAlbumUi.addPhotos(photo.getMediaContents().get(0)
								.getUrl());
					}
					result.addAlbum(lAlbumUi);
					maxAlbum--;
				}
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private void getAlbumsFake(LoadingResult result) {
		result.addAlbum(new LoadingAlbumUi("1", "Compétition interne",
				"img/galerie/sitegpe.jpg"));
		result.addAlbum(new LoadingAlbumUi("2", "National 2",
				"img/galerie/sitewebmarius.jpg"));
		result.addAlbum(new LoadingAlbumUi("3", "Natathlon",
				"img/galerie/sitewebcyrilpastailleavant.jpg"));
	}

	/**
	 * Helper function to get a link by a rel value.
	 */
	public String getLinkByRel(List<Link> links, String relValue) {
		for (Link link : links) {
			if (relValue.equals(link.getRel())) {
				return link.getHref();
			}
		}
		throw new IllegalArgumentException("Missing " + relValue + " link.");
	}

	@Path("/actualites")
	@GET
	public LoadingResult getActualites() {
		LoadingResult result = new LoadingResult();
		// Actu
		List<ActuEntity> lEntities = actuDao.getAll(0, 7);
		ActuTransformer actuTransformer = new ActuTransformer();
		DocumentTransformer documentTransformer = new DocumentTransformer();
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = actuTransformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
			lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
			lDocumentCriterion.setOperator(Operator.EQUAL);
			lDocumentCriteria.add(lDocumentCriterion);
			lDocumentCriterion.setValue(entity.getId());
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			result.addActualite(lUi);
		}
		return result;
	}
}
