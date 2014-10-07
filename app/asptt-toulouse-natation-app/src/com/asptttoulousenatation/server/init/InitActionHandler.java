package com.asptttoulousenatation.server.init;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDayDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.server.dao.swimmer.SwimmerDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateAction;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateResult;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.ApplicationLoader;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionDayTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.server.util.Utils;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class InitActionHandler implements ActionHandler<InitAction, InitResult> {

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();

	private AreaTransformer areaTransformer = new AreaTransformer();
	private MenuTransformer menuTransformer = new MenuTransformer();

	private ActuDao actuDao = new ActuDao();
	private ActuTransformer actuTransformer = new ActuTransformer();

	private CompetitionDao competitionDao = new CompetitionDao();
	private CompetitionDayDao competitionDayDao = new CompetitionDayDao();

	private CompetitionTransformer competitionTransformer = new CompetitionTransformer();
	private CompetitionDayTransformer competitionDayTransformer = new CompetitionDayTransformer();

	private ApplicationLoader applicationLoader = ApplicationLoader
			.getInstance();

	private static final Logger LOG = Logger.getLogger(InitActionHandler.class
			.getName());

	public InitResult execute(InitAction pAction, ExecutionContext pContext)
			throws DispatchException {
		LOG.info("Init action");
		Long startTime = System.currentTimeMillis();

		//createData();
		// createUsers();
		// createCompetition();
		// createActu();
		InitResult lInitResult = new InitResult();

		IsDataUpdateResult lAreaUpdateResult = pContext
				.execute(new IsDataUpdateAction(AreaEntity.class));
		IsDataUpdateResult lMenuUpdateResult = pContext
				.execute(new IsDataUpdateAction(MenuEntity.class));
		if (lAreaUpdateResult.isDataUpdated()
				|| lMenuUpdateResult.isDataUpdated()
				|| applicationLoader.getArea() == null) {
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
			List<AreaEntity> lAreaEntities = areaDao.find(
					lAreaSelectionCriteria, lOrderDao);
			Map<String, AreaUi> lAreaUis = new LinkedHashMap<String, AreaUi>(
					lAreaEntities.size());
			List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
			lAreaCriterion.setEntityField(MenuEntityFields.AREA);
			lAreaCriterion.setOperator(Operator.EQUAL);
			lCriteria.add(lAreaCriterion);
			OrderDao lMenuOrder = new OrderDao(MenuEntityFields.ORDER,
					OrderDao.OrderOperator.ASC);

			List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
			lContentCriterion.setEntityField(ContentEntityFields.MENU);
			lContentCriterion.setOperator(Operator.EQUAL);
			lMenuCriteria.add(lContentCriterion);
			LOG.info("retrieving actu #" + lAreaEntities.size());
			for (AreaEntity lAreaEntity : lAreaEntities) {
				// Get menu
				lAreaCriterion.setValue(lAreaEntity.getId());
				List<MenuEntity> lMenuEntities = menuDao.find(lCriteria,
						lMenuOrder);
				LOG.info("retrieving menu #" + lMenuEntities.size());
				Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(
						lMenuEntities.size());
				for (MenuEntity lMenuEntity : lMenuEntities) {
					if (lMenuEntity.getParent() == null) {
						// Retrieve sub menu
						List<MenuUi> lSubMenuUis = new ArrayList<MenuUi>();
						if(CollectionUtils.isNotEmpty(lMenuEntity.getSubMenu())) {
						lSubMenuUis = new ArrayList<MenuUi>(
								lMenuEntity.getSubMenu().size());
						for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
							MenuEntity lSubMenu = menuDao.get(lSubMenuId);
							lSubMenuUis.add(menuTransformer.toUi(lSubMenu));
						}}
						MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
						Collections.sort(lSubMenuUis, new Comparator<MenuUi>() {

							public int compare(MenuUi pO1, MenuUi pO2) {
								final int result;
								if (pO1.getOrder() == pO2.getOrder()) {
									result = 0;
								} else if (pO1.getOrder() > pO2.getOrder()) {
									result = 1;
								} else {
									result = -1;
								}
								return result;
							}

						});
						lMenu.setSubMenus(lSubMenuUis);
						lMenuUis.put(lMenu.getTitle(), lMenu);
					}
				}
				AreaUi lArea = areaTransformer.toUi(lAreaEntity);
				lArea.setMenuSet(lMenuUis);
				lAreaUis.put(lArea.getTitle(), lArea);
			}
			pContext.execute(new SetDataUpdateAction(AreaEntity.class, false));
			pContext.execute(new SetDataUpdateAction(MenuEntity.class, false));
			applicationLoader.setArea(lAreaUis);
		}

		IsDataUpdateResult lActuUpdateResult = pContext
				.execute(new IsDataUpdateAction(ActuEntity.class));
		if (lActuUpdateResult.isDataUpdated()
				|| applicationLoader.getActu() == null) {
		}

		Long endTime = System.currentTimeMillis();
		LOG.info("Loading duration: " + (endTime - startTime) + " ms");
		return lInitResult;
	}

	public Class<InitAction> getActionType() {
		return InitAction.class;
	}

	public void rollback(InitAction pArg0, InitResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

	private void createData() {
		deleteAll();

		AreaDao lAreaDao = new AreaDao();

		AreaEntity lAreaEntity = new AreaEntity(null, "Le Club",
				ProfileEnum.PUBLIC, (short) 1);
		AreaEntity lAreaEntity2 = lAreaDao.save(lAreaEntity);

		// Historique
		createMenu(MenuItems.VIDE.toString(), "Historique du club",
				lAreaEntity2.getId(), "contenu de l'historique du club",
				"contenu de l'historique", false, true, 1, false, false);
		createMenu(MenuItems.VIDE.toString(), "Vie du club",
				lAreaEntity2.getId(), "contenu de vie du club",
				"contenu de l'organisation du club", false, true, 2, false, false);
		createMenu(MenuItems.VIDE.toString(), "Piscine",
				lAreaEntity2.getId(), "contenu de Piscine",
				"contenu de Lieux d'entrainements", false, true, 3, false, false);
		createMenu(MenuItems.VIDE.toString(), "Organisation",
				lAreaEntity2.getId(), "contenu de officiels",
				"contenu de officiels", false, true, 4, true, true);
		createMenu(MenuItems.VIDE.toString(), "Comité de section",
				lAreaEntity2.getId(), "contenu de comité de section",
				"contenu de la vie du club", false, true, 5, false, false);
		createMenu(MenuItems.VIDE.toString(), "Les officiels",
				lAreaEntity2.getId(), "contenu de les officiels",
				"contenu de la vie du club", false, true, 5, false, false);
		createMenu(MenuItems.VIDE.toString(), "Les entraineurs",
				lAreaEntity2.getId(), "contenu de les entraineurs",
				"contenu de la vie du club", false, true, 5, false, false);
		createMenu(MenuItems.VIDE.toString(), "Les éducateurs",
				lAreaEntity2.getId(), "contenu de les éducateurs",
				"contenu de la vie du club", false, true, 5, false, false);

		lAreaEntity = new AreaEntity(null, "Nos activités", ProfileEnum.PUBLIC,
				(short) 2);
		AreaEntity lAreaGroupes = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.VIDE.toString(), "Ecole de natation",
				lAreaGroupes.getId(), "contenu de Ecole de natation",
				"contenu de Ecole de natation", false, true, 1, false, false);
		createMenu(MenuItems.VIDE.toString(), "Loisirs",
				lAreaGroupes.getId(), "contenu de Loisirs",
				"contenu de Loisirs", false, true, 2, false, false);
		createMenu(MenuItems.VIDE.toString(), "Aquagym", lAreaGroupes.getId(),
				"contenu de Aquagym", "contenu de Aquagym", false, true, 3, false, false);
		createMenu(MenuItems.VIDE.toString(), "Compétition",
				lAreaGroupes.getId(), "contenu de Compétition",
				"contenu de perfrectionnement", false, true, 4, false, false);
		
		lAreaEntity = new AreaEntity(null, "Compétitions", ProfileEnum.PUBLIC,
				(short) 3);
		AreaEntity lAreaCompetition = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.VIDE.toString(), "Actualités",
				lAreaCompetition.getId(), "contenu de Actualités",
				"contenu de Calendrier", false, true, 1, false, false);
		createMenu(MenuItems.VIDE.toString(), "Résultats",
				lAreaCompetition.getId(), "contenu de Résultats",
				"contenu de Résultats", false, true, 2, false, false);
		createMenu(MenuItems.VIDE.toString(), "Records du club",
				lAreaCompetition.getId(), "contenu de Records du club",
				"contenu de Records du club", false, true, 3, false, false);
		

		lAreaEntity = new AreaEntity(null, "Santé", ProfileEnum.PUBLIC,
				(short) 4);
		lAreaDao.save(lAreaEntity);
		
		lAreaEntity = new AreaEntity(null, "Boutique", ProfileEnum.PUBLIC,
				(short) 5);
		lAreaDao.save(lAreaEntity);
		

		lAreaEntity = new AreaEntity(null, "Inscription", ProfileEnum.PUBLIC,
				(short) 6);
		lAreaDao.save(lAreaEntity);
		
		lAreaEntity = new AreaEntity(null, "Partenaires", ProfileEnum.PUBLIC,
				(short) 7);
		lAreaDao.save(lAreaEntity);

		lAreaEntity = new AreaEntity(null, "Contact", ProfileEnum.PUBLIC,
				(short) 8);
		lAreaDao.save(lAreaEntity);
		

		// Admin
		lAreaEntity = new AreaEntity(null, "Actualités", ProfileEnum.ADMIN,
				false, (short) 1);
		AreaEntity lAreaAdmActu = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.NEWS_PUBLICATION.toString(), "Publier",
				lAreaAdmActu.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 1, false, false);
		createMenu(MenuItems.NEWS_EDITION.toString(), "Modification",
				lAreaAdmActu.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2, false, false);

		lAreaEntity = new AreaEntity(null, "Utilisateur", ProfileEnum.ADMIN,
				false, (short) 5);
		AreaEntity lAreaAdmUser = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.USER_CREATION.toString(), "Création",
				lAreaAdmUser.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 1, false, false);
		createMenu(MenuItems.USER_EDITION.toString(), "Modification",
				lAreaAdmUser.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2, false, false);

		// Organisation du club
		lAreaEntity = new AreaEntity(null, "Organisation du club",
				ProfileEnum.ADMIN, false, (short) 3);
		AreaEntity lAreaClub = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.CLUB_GROUP_EDITION.toString(), "Groupes",
				lAreaClub.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 1, false, false);
		createMenu(MenuItems.CLUB_SLOT_EDITION.toString(), "Créneaux",
				lAreaClub.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 2, false, false);

		// Officiels
		lAreaEntity = new AreaEntity(null, "Officier", ProfileEnum.OFFICIEL,
				false, (short) 6);
		AreaEntity lAreaOfficier = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.OFFICIEL_VIEW.toString(),
				"Consulter le calendrier", lAreaOfficier.getId(),
				StringUtils.EMPTY, StringUtils.EMPTY, true, true, 1, false, false);
		createMenu(MenuItems.OFFICIEL_SUBSCRIPTION.toString(), "Inscription",
				lAreaOfficier.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2, false, false);

		// Compétition
		lAreaEntity = new AreaEntity(null, "Compétitions saison",
				ProfileEnum.ADMIN, true, (short) 4);
		AreaEntity lAreaCompetitionSaison = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.COMPETITION_EDITION.toString(), "Modification",
				lAreaCompetitionSaison.getId(), StringUtils.EMPTY,
				StringUtils.EMPTY, true, true, 1, false, false);

		lAreaEntity = new AreaEntity(null, "Structure du site",
				ProfileEnum.ADMIN, (short) 2);
		lAreaDao.save(lAreaEntity);

		// Swimmer stat
		lAreaEntity = new AreaEntity(null, "Suivi des nageurs",
				ProfileEnum.ADMIN, false, (short) 5);
		AreaEntity lAreaStat = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.SWIMMER_STAT_DAY.toString(), "Par jour",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 1, false, false);
		createMenu(MenuItems.SWIMMER_STAT_WEEK.toString(), "Par semaine",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 2, false, false);
		createMenu(MenuItems.SWIMMER_STAT_MONTH.toString(), "Par mois",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 3, false, false);
		createMenu(MenuItems.SWIMMER_STAT_YEAR.toString(), "Par année",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 4, false, false);

		// Profil
		lAreaEntity = new AreaEntity(null, "Mon profil", ProfileEnum.NAGEUR,
				false, (short) 5);
		AreaEntity lAreaProfil = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.PROFILE_PASSWORD.toString(),
				"Changer de mot de passe", lAreaProfil.getId(),
				StringUtils.EMPTY, StringUtils.EMPTY, true, false, 1, false, false);

		createUserAdmin();
		createUserRoot();
		createUserOfficiel();
	}

	private void createMenu(String pMenuKey, String pTitle, Long pArea,
			String pSummary, String pContent, boolean pShortcut,
			boolean pDisplay, int pOrder, boolean pDivider, boolean pAlone) {
		MenuEntity lMenuHisto = new MenuEntity(pMenuKey, pTitle, pArea,
				pShortcut, pDisplay, pOrder, null, null);
		lMenuHisto.setDivider(pDivider);
		lMenuHisto.setAlone(pAlone);
		MenuEntity lMenuHisto2 = menuDao.save(lMenuHisto);
		ContentEntity lHistoContentEntity = new ContentEntity(pSummary,
				new Blob((HTMLUtils.escapeHTML(pContent)).getBytes()),
				ContentDataKindEnum.TEXT.toString(), lMenuHisto2.getId());
		contentDao.save(lHistoContentEntity);
	}

	private void createUserAdmin() {
		try {
			UserEntity lUserEntity = new UserEntity();
			lUserEntity.setEmailaddress("admin@admin.com");
			lUserEntity.setProfiles(Collections.singleton(ProfileEnum.ADMIN
					.toString()));
			lUserEntity.setValidated(true);
			UserDataEntity lUserDataEntity = new UserDataEntity();
			UserDataDao lUserDataDao = new UserDataDao();
			lUserEntity.setUserData(lUserDataDao.save(lUserDataEntity).getId());

			MessageDigest lMessageDigest = Utils.getMD5();

			String lCode = "0123";
			System.out.println(lCode);
			String lEncryptedPassword = new String(lMessageDigest.digest(lCode
					.getBytes()));
			lUserEntity.setPassword(lEncryptedPassword);

			UserDao lUserDao = new UserDao();
			lUserDao.save(lUserEntity);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createUserRoot() {
		try {
			UserEntity lUserEntity = new UserEntity();
			lUserEntity.setEmailaddress("root@root.com");

			Set<String> lProfiles = new HashSet<String>(3);
			lProfiles.add(ProfileEnum.ADMIN.toString());
			lProfiles.add(ProfileEnum.OFFICIEL.toString());
			lProfiles.add(ProfileEnum.NAGEUR.toString());
			lUserEntity.setProfiles(lProfiles);
			lUserEntity.setValidated(true);
			UserDataEntity lUserDataEntity = new UserDataEntity();
			UserDataDao lUserDataDao = new UserDataDao();
			lUserEntity.setUserData(lUserDataDao.save(lUserDataEntity).getId());

			MessageDigest lMessageDigest = Utils.getMD5();

			String lCode = "0123";
			System.out.println(lCode);
			String lEncryptedPassword = new String(lMessageDigest.digest(lCode
					.getBytes()));
			lUserEntity.setPassword(lEncryptedPassword);

			UserDao lUserDao = new UserDao();
			lUserDao.save(lUserEntity);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createUserOfficiel() {
		try {
			UserEntity lUserEntity = new UserEntity();
			lUserEntity.setEmailaddress("officiel@officiel.com");

			Set<String> lProfiles = new HashSet<String>(3);
			lProfiles.add(ProfileEnum.OFFICIEL.toString());
			lUserEntity.setProfiles(lProfiles);
			lUserEntity.setValidated(true);
			UserDataEntity lUserDataEntity = new UserDataEntity();
			UserDataDao lUserDataDao = new UserDataDao();
			lUserEntity.setUserData(lUserDataDao.save(lUserDataEntity).getId());

			MessageDigest lMessageDigest = Utils.getMD5();

			String lCode = "0123";
			System.out.println(lCode);
			String lEncryptedPassword = new String(lMessageDigest.digest(lCode
					.getBytes()));
			lUserEntity.setPassword(lEncryptedPassword);

			UserDao lUserDao = new UserDao();
			lUserDao.save(lUserEntity);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteAll() {
		// Actu
		List<ActuEntity> lActus = actuDao.getAll();
		for (ActuEntity lActu : lActus) {
			actuDao.delete(lActu);
		}
		// Area
		List<AreaEntity> lAreas = areaDao.getAll();
		for (AreaEntity lAreaEntity : lAreas) {
			areaDao.delete(lAreaEntity);
		}
		// Menu
		List<MenuEntity> lMenus = menuDao.getAll();
		for (MenuEntity lMenuEntity : lMenus) {
			menuDao.delete(lMenuEntity);
		}
		// Content
		List<ContentEntity> lContents = contentDao.getAll();
		for (ContentEntity lContentEntity : lContents) {
			contentDao.delete(lContentEntity);
		}
		// User data
		UserDataDao lUserDataDao = new UserDataDao();
		List<UserDataEntity> lUserData = lUserDataDao.getAll();
		for (UserDataEntity lUserDataEntity : lUserData) {
			lUserDataDao.delete(lUserDataEntity);
		}
		// User
		UserDao lUserDao = new UserDao();
		List<UserEntity> lUser = lUserDao.getAll();
		for (UserEntity lUserEntity : lUser) {
			lUserDao.delete(lUserEntity);
		}
	}

	private String[] getPicture() {
		String[] result = new String[0];
		try {
			PicasawebService myService = new PicasawebService("asptt_test");
			myService.setUserCredentials(
					"webmaster@asptt-toulouse-natation.com", "31000_asptt");
			URL feedUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/webmaster@asptt-toulouse-natation.com?kind=album");

			Query myQuery = new Query(feedUrl);

			UserFeed searchResultsFeed = myService.query(myQuery,
					UserFeed.class);
			// Get 'Banniere' album
			AlbumEntry lBanniereAlbum = null;
			boolean found = false;
			ListIterator<AlbumEntry> lEntryIt = searchResultsFeed
					.getAlbumEntries().listIterator();
			while (lEntryIt.hasNext() && !found) {
				AlbumEntry adaptedEntry = lEntryIt.next();
				AlbumEntry lAlbum = (AlbumEntry) adaptedEntry;
				if ("Banniere".equals(lAlbum.getName())) {
					lBanniereAlbum = lAlbum;
					found = true;
				}
			}
			if (lBanniereAlbum != null) {
				String feedHref = getLinkByRel(lBanniereAlbum.getLinks(),
						Link.Rel.FEED);
				AlbumFeed lAlbumEntries = myService.query(new Query(new URL(
						feedHref)), AlbumFeed.class);

				result = new String[lAlbumEntries.getPhotoEntries().size()];
				int i = 0;
				for (PhotoEntry photo : lAlbumEntries.getPhotoEntries()) {
					result[i] = photo.getMediaContents().get(0).getUrl();
					i++;
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

	private void createUsers() {
		String[][] lUsers = new String[][] { { "Achotegui", "Nicolas" },
				{ "Audouy", "Clément" }, { "Barboteau", "Elodie" },
				{ "Borderas", "Sébastien" }, { "Bulit", "Florian" },
				{ "Cassan-Ferrier", "Alexandra" }, { "Cavagna", "Cyril" },
				{ "Cerisier", "Camille" }, { "Claverie", "Camille" },
				{ "Combanière", "Jérome" }, { "Costes", "Antony" },
				{ "Danho", "Thibault" }, { "Debeuckelaere", "Alain" },
				{ "Delbos", "Lucie" }, { "Devaud", "Louise" },
				{ "Enjalby", "Jérémy" }, { "Escalante", "Yacine" },
				{ "Fauconnier", "Lisa" }, { "Félix", "Mathieu" },
				{ "Féron", "Laura" }, { "Fourcade", "Annabel" },
				{ "Gauliard", "Aurélie" }, { "Grivel", "Quentin" },
				{ "Guermonprez", "Eve-Marie" }, { "Lehir", "Nicolas" },
				{ "Kargbo", "Michaël" }, { "Kieffer", "Charlotte" },
				{ "Lacomme", "David" }, { "Lehoux", "Edouard" },
				{ "Marrot", "Sophie" }, { "Martin", "Enzo" },
				{ "Migeon", "Typhanie" }, { "Migeon", "Victor" },
				{ "Nivoix", "Julie" }, { "Ramonjiarivony", "Maëva" },
				{ "Royo", "Martin" }, { "Savé", "Jérome" },
				{ "Schwarz", "Mickaël" }, { "Starzec", "Bruno" },
				{ "Tranchard", "Guillaume" }, { "Vignard", "Thomas" },
				{ "Vives", "Adrien" } };
		for (String[] lUser : lUsers) {
			createUser(lUser[0], lUser[1]);
		}
	}

	private void createUser(String pLastName, String pFirstName) {
		try {
			UserEntity lUserEntity = new UserEntity();
			lUserEntity.setEmailaddress(pLastName + "." + pFirstName
					+ "@asptt-toulouse-natation.com");

			Set<String> lProfiles = new HashSet<String>(3);
			lProfiles.add(ProfileEnum.NAGEUR.toString());
			lUserEntity.setProfiles(lProfiles);
			lUserEntity.setValidated(true);
			UserDataEntity lUserDataEntity = new UserDataEntity();
			lUserDataEntity.setFirstName(pFirstName);
			lUserDataEntity.setLastName(pLastName);
			UserDataDao lUserDataDao = new UserDataDao();
			lUserEntity.setUserData(lUserDataDao.save(lUserDataEntity).getId());

			MessageDigest lMessageDigest = Utils.getMD5();

			String lCode = "0123";
			System.out.println(lCode);
			String lEncryptedPassword = new String(lMessageDigest.digest(lCode
					.getBytes()));
			lUserEntity.setPassword(lEncryptedPassword);

			UserDao lUserDao = new UserDao();
			UserEntity lUserCreated = lUserDao.save(lUserEntity);

			SwimmerEntity lSwimmerEntity = new SwimmerEntity();
			lSwimmerEntity.setStat(true);
			lSwimmerEntity.setUser(lUserCreated.getId());
			SwimmerDao lSwimmerDao = new SwimmerDao();
			lSwimmerDao.save(lSwimmerEntity);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createActu() {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy",
				Locale.FRANCE);
		SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy",
				Locale.FRANCE);
		ActuDao dao = new ActuDao();
		try {
			ActuEntity actu = new ActuEntity();
			actu.setCreationDate(format1.parse("21/08/2012"));
			actu.setTitle("Reprise en fanfare	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"R&eacute;-ouverture des bureaux &agrave; partir de ce Mardi 21/08/2012.an style=\"font-family: Comic Sans MS;\">Notez que nous sommes <strong>ouverts en continue de 10H &agrave; 17H,&nbsp;</strong></span></span><span style=\"font-size: small;\"><span style=\"font-family: Comic Sans MS;\">du lundi au vendredi cette semaine et la semaine prochaine (du 27 au 31/08).</span></span></div><div><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note.</span></span></div>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("28/08/2012"));
			actu.setTitle("Nouvelles inscriptions 2012-2013	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention des personnes souhaitant nous rejoindre pour la nouvelle saison sportive 2012-2013.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Vous trouverez <a href=\"http://asptt-toulouse-natation.com/inscriptions.php\">ici</a>, tous les documents n&eacute;cessaires et la proc&eacute;dure &agrave; suivre.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Si besoin est, n'h&eacute;sitez pas &agrave; nous contacter par t&eacute;l&eacute;phone au 05.61.62.68.45.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">NB : pour les &nbsp;enfants non-d&eacute;butants, il est imp&eacute;ratif de nous contacter par t&eacute;l&eacute;phone, pour vous pr&eacute;-inscrire aux &eacute;valuations de la semaine du 10/09 sur les diff&eacute;rentes piscines.</span></span></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("04/09/2012"));
			actu.setTitle("Planning de reprise des activités	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention de tous nos adh&eacute;rents de la saison sportive 2012-2013, nous vous pr&eacute;cisons &agrave; nouveau le timing de reprise :</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-</span></span></strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"> <u><strong>semaine du Lundi 10/09 au Jeudi 13/09 </strong></u><strong>:</strong> &eacute;valuations suivie des inscriptions pour les nouveaux adh&eacute;rents, pour les cours sur Toulouse-Lautrec (Lundi 10/09), sur Alex Jany (Mardi 11/09), sur L&eacute;o Lagrange (Mercredi 12/09, pour les cours du Jeudi et Jeudi 13/09, pour les cours du Vendredi). N'oubliez pas de vous pr&eacute;inscrire par t&eacute;l&eacute;phone au pr&eacute;alable.</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <u>semaine du Lundi 17/09 au Vendredi 21/09 </u>:</span></span></strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"> reprise des cours &agrave; compter du Lundi 17/09 (19h) et Mercredi 19/09 (19h) sur Toulouse-Lautrec, des Mardi 18/09 (19h) et Jeudi 20/09 (19h) sur Alex Jany, des Jeudi 20/09 (18h) et Vendredi 21/09 (18h) sur L&eacute;o Lagrange.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note, A bient&ocirc;t</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("04/09/2012"));
			actu.setTitle("Etape de Coupe de France d'Eau Libre Toulouse	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention de nos comp&eacute;titeurs (des Benjamins aux Masters) ainsi que des b&eacute;n&eacute;voles, et officiels du club, ce petit mot pour vous rappeler que se d&eacute;roule, ce Dimanche 09 Septembre 2012, &nbsp;&agrave; la base verte de La Ram&eacute;e, la 33&egrave;me &eacute;tape de Coupe de France d'Eau Libre. </span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Ainsi 3 &eacute;preuves seront propos&eacute;es sur site, au cours de cette journ&eacute;e : </span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <u><strong>le 7.5 km :</strong></u> RDV : 10h pour un d&eacute;part &agrave; 11h, ouvert aux Minimes et plus.<br /></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <u><strong>le 500m (Pass'comp&eacute;tition Eau Libre) :</strong></u> RDV : 13h30, d&eacute;part 13h45, ouvert aux Poussins, Benjamins et plus.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <u><strong>le 1.5 km :</strong></u> RDV : 14h, pour un d&eacute;part &agrave; 15h, ouvert aux Benjamins et plus.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Pour les nageurs du club, merci de vous rapprocher de Lo&iuml;c ou Cl&eacute;ment pour vous inscrire et pour les officiels et b&eacute;n&eacute;voles, veuillez vous rapprocher de Philippe qui assure la coordination en tant qu'organisateur.&nbsp;</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">N'h&eacute;sitez pas &agrave; venir nombreux &agrave; cette manifestation faisant office de r&eacute;union de reprise en ce d&eacute;but de saison.</span></span></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("07/09/2012"));
			actu.setTitle("Reprise groupes compétitions Poussins-Benjamins et Minimes	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p>&nbsp;<span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention des nageurs des groupes Poussins, Benjamins et Minimes &agrave; jour de leur inscription, possibilit&eacute; de reprendre les entra&icirc;nements d&egrave;s la semaine prochaine :</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- </span></span></strong><u><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">groupe des Poussins&nbsp;</span></span></strong></u><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">: Mercredi : 19h &agrave; 20h45 sur Alex Jany et Vendredi : 19h &agrave; 20h30 sur Toulouse-Lautrec, encadr&eacute; par Clo&eacute;.</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- </span></span></strong><u><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">groupe des Benjamins </span></span></strong></u><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">:</span></span></strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"> Lundi : 19h &agrave; 20h45 sur Alex Jany puis Mercredi et Vendredi : 19h &agrave; 20h30 sur Toulouse-Lautrec, coach&eacute; par S&eacute;bastien.</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- </span></span></strong><u><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">groupe des Minimes </span></span></strong></u><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">:</span></span></strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"> Lundi : 19h &agrave; 20h45 sur Alex Jany puis Mercredi et Vendredi : 19h &agrave; 20h30 sur Toulouse-Lautrec, entra&icirc;n&eacute; par J&eacute;r&eacute;my.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note et de vous rendre disponible,</span></span></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("10/09/2012"));
			actu.setTitle("Résultats Coupe de France d'Eau Libre 09/09/2012	");
			actu.setSummary("");
			actu.setContent(new Text(
					"Veuillez trouver ci-joint, les r&eacute;sultats des 3 &eacute;preuves qui se sont d&eacute;roul&eacute;es, dans ce cadre, hier au Lac de La Ram&eacute;e. Epreuves couronn&eacute;es de succ&egrave;s, puisque ce sont pas moins de <strong>51 participants (20 dames et 31 messieurs) issus de 19 clubs&nbsp;</strong>qui se sont align&eacute;s au d&eacute;part du 7.5 km le matin ainsi que <strong>110 nageurs (53 filles et 57 gar&ccedil;ons) provenant de 20 clubs</strong> qui ont nag&eacute; le 1.5 km l'apr&egrave;s-midi. Encore une belle organisation r&eacute;ussite de la part du club, le tout sous une m&eacute;t&eacute;o radieuse, de quoi aborder cette nouvelle saison dans les meilleures dispositions...</span></span></p><p>&nbsp;<span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_scratch.pdf\">classement scratch &eacute;preuve 7.5 km</a></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_par%20cat%C3%A9gories.pdf\">classement par cat&eacute;gorie &eacute;preuve 7.5 km</a></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_scratch(1).pdf\">classement scratch &eacute;preuve 1.5 km</a></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_par%20cat%C3%A9gories(1).pdf\">classement par cat&eacute;gorie &eacute;preuve 1.5 km</a></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_scratch(2).pdf\">classement Pass'comp&eacute;tition (500m)</a></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- <a href=\"/entrepot/transfert/file/2012-2013/Eau%20Libre%2009-09-2012/resultats_par%20cat%C3%A9gories(2).pdf\">classement par cat&eacute;gorie Pass'comp&eacute;tition (500m)</a></span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("14/09/2012"));
			actu.setTitle("Reprise des activités	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention des adh&eacute;rents de tout bord, ce mot pour vous rappeler que les cours ou entra&icirc;nements (selon) reprennent &agrave; compter de ce <strong>Lundi 17 Septembre 2012 &agrave; 19h</strong>, <strong>sur Toulouse-Lautrec </strong>(pour l'Ecole de Natation, le Perfectionnement et les Loisirs) ou <strong>Alex Jany</strong> (pour les comp&eacute;titeurs : Minimes, Cadets-Juniors-Seniors D&eacute;partementaux).</span></span></p><p><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Ensuite, ce Mardi 18/09 &agrave; 19h sur Alex Jany, ce Mercredi 19/09 &agrave; 19h sur Toulouse-Lautrec, ce Jeudi 20/09 &agrave; 18h sur L&eacute;o Lagrange et &agrave; 19h sur Alex Jany et enfin ce Vendredi 21/09 &agrave; 18h sur L&eacute;o Lagrange.</span></span></strong></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note et de r&eacute;pondre pr&eacute;sents, tous aux starting-blocks...</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("25/09/2012"));
			actu.setTitle("Annulation entraînement Samedi 29/09/2012	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention des comp&eacute;titeurs </span></span><strong><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">des groupes Benjamins, Minimes, de la section sportive de R.</span></span>&nbsp;Naves et&nbsp;<span style=\"font-family: 'Comic Sans MS'; font-size: small; \">Juniors-Seniors Interr&eacute;gional et +</span></strong><span style=\"font-family: 'Comic Sans MS'; font-size: small; \">, ce billet pour vous informer qu'en raison d'une comp&eacute;tition sur la piscine Alex Jany ce jour, et de l'indisponibilit&eacute; de l'&eacute;tablissement, il n'y aura donc pas d'entra&icirc;nements assur&eacute;s aux horaires habituels.</span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">N&eacute;anmoins, pour la section sportive et les Juniors-Seniors Interr&eacute;gional et +, possibilit&eacute; exceptionnelle sera fa&icirc;te de nager <strong>de 09h30 &agrave; 11h sur la piscine de SupA&eacute;ro (2 lignes)</strong>, seul cr&eacute;neau que nous avons pu obtenir en derni&egrave;re minute.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note et de vous adapter en cons&eacute;quence.&nbsp;</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("26/09/2012"));
			actu.setTitle("Assemblée Générale Vendredi 12/10/2012	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention de nos adh&eacute;rents de tout bord, de l'Ecole de Natation aux Loisirs en passant par les comp&eacute;titeurs, ce petit mot pour vous informer que <strong>le Vendredi 12 Octobre prochain, &agrave; 19h</strong>, &agrave; la salle de Tennis de Table de l'ASPTT, se tiendra donc l'Assembl&eacute;e G&eacute;n&eacute;rale de la section, de la saison 2011-2012.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"><b>Toutes les bonnes volont&eacute;s voulant s&rsquo;impliquer de mani&egrave;re active sur la vie du  club sont les bienvenues, pri&egrave;re de vous faire conna&icirc;tre par retour de mail dans  le d&eacute;lai imparti (soit le Vendredi 05 Octobre).</b></span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">En esp&eacute;rant vous compter nombreux pour l'occasion, &agrave; bient&ocirc;t !</span></span></p><p><b><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></b></p>“	<p>&nbsp;</p><p><a href=\"/entrepot/transfert/file/2012-2013/convoc%20AG%202012.doc\"><span style=\"font-size: medium; \"><span style=\"font-family: 'Comic Sans MS'; \">La convocation de l'&eacute;v&egrave;nement</span></span></a></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("08/10/2012"));
			actu.setTitle("Annulation entraînements Vendredi 12/10/2012	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Bonjour, en raison de la tenue, &agrave; la m&ecirc;me heure, de notre Assembl&eacute;e G&eacute;n&eacute;rale 2011-2012, ce Vendredi 12/10/2012 &agrave; 19h (salle de Tennis de Table ASPTT), <b style=\"font-family: Arial; font-size: 10pt; \">les entra&icirc;nements</b> <strong>des groupes Poussins, Benjamins, Minimes, Cadets-Juniors et Elite</strong> <b style=\"font-family: Arial; font-size: 10pt; \">sont annul&eacute;s</b>.&nbsp;</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note et de vous organiser en cons&eacute;quence, comptant sur votre pr&eacute;sence lors de l'AG, &agrave; bient&ocirc;t.</span></span>&nbsp;</p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">PS : les cours de l'Ecole de Natation sur L&eacute;o Lagrange (18h &agrave; 20h) sont bien maintenus ce m&ecirc;me soir.</span></span></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("16/10/2012"));
			actu.setTitle("Accès vestiaires Alex Jany	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Les usagers concern&eacute;s, nos adh&eacute;rents du Mardi et Jeudi soirs sur Alex Jany, merci de prendre connaissance de la petite note suivante.</span></span></p>“	<p>&nbsp;</p><p>&nbsp;<span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Etant inscrits les Mardi et Jeudi soirs sur Alex Jany, une petite pr&eacute;cision quant &agrave; la proc&eacute;dure d&rsquo;&rsquo;entr&eacute;e sur le bassin, depuis l&rsquo;obligation qui nous est faite de vous acheminer via les tourniquets:</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour les adh&eacute;rents du cours de 19h : merci de bien vouloir vous pr&eacute;senter devant le tourniquet entre 18h45 et 18h55 maximum afin de pouvoir d&eacute;buter le cours &agrave; 19h.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;- pour les adh&eacute;rents du cours de 19h55 : merci de bien vouloir vous pr&eacute;senter devant le tourniquet entre 19h40 et 19h50 maximum afin de pouvoir d&eacute;buter le cours &agrave; 19h55.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;&nbsp; &nbsp; Quelques explications : un nouveau mode de fonctionnement &agrave; &eacute;t&eacute; mis en place par la Mairie pour un syst&egrave;me de comptage des adh&eacute;rents de chaque association : d&eacute;sormais l&rsquo;acc&egrave;s n&rsquo;est rendu possible que par un badge qui nous est mis &agrave; disposition, nous membres de l&rsquo;ASPTT Toulouse Natation. De ce fait, les &eacute;ducateurs doivent attendre &agrave; l&rsquo;ext&eacute;rieur du bassin pour pouvoir passer ce badge et vous faire rentrer les uns apr&egrave;s les autres, ce qui occasionne une perte de temps certaine afin de pouvoir d&eacute;buter le cr&eacute;neau &agrave; l&rsquo;heure, ce qui n&rsquo;est pas pour permettre un fonctionnement optimal du cours (l&rsquo;enseignant devant revenir pour les &eacute;ventuels retardataires...).</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;&nbsp;&nbsp; &nbsp; C&rsquo;est pour cela que nous nous permettons de vous pr&eacute;senter la d&eacute;marche suivante : si certain(ne)s d&rsquo;entre vous ont la gentillesse de prendre en charge la proc&eacute;dure d&rsquo;acc&egrave;s aux vestiaires et ainsi soulager nos &eacute;ducateurs de cette contrainte, merci de nous le signaler, cela rendrait service &agrave; tout le monde et optimiserait notre fonctionnement.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;Exemple : celles ou ceux qui restent voir leurs enfants dans les gradins durant le cours peuvent se proposer de prendre le badge du moniteur au d&eacute;but du cours, faire rentrer les adh&eacute;rents ASPTT au tourniquet (18h45-19h15 ou 19h40-20h10), et le rendre ensuite au moniteur &agrave; la fin de la s&eacute;ance.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;&nbsp; &nbsp; Merci de votre compr&eacute;hension, et en esp&eacute;rant avoir des retours positifs,</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("16/10/2012"));
			actu.setTitle("Fermeture de Toulouse-Lautrec pour travaux	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;La piscine Toulouse Lautrec va fermer ses portes pour travaux <strong>du 22/10/2012 au 25/11/2012 inclus</strong>.&nbsp;</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Depuis que nous avons eu l&rsquo;information, nous avons fait au mieux pour trouver les solutions les plus convenables pour l&rsquo;ensemble de nos adh&eacute;rents et ne l&eacute;ser personne.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Voici donc, ci-joint, les solutions que nous vous proposons pour pallier &agrave; ce d&eacute;sagr&eacute;ment et pouvoir assurer les s&eacute;ances :&nbsp;</span></span><span style=\"font-family: Calibri; \"><o:p></o:p></span></p>“	<p>&nbsp;</p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- </span></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">pour les groupes <strong><u><em style=\"font-family: Calibri; \">Tritons, Dauphins</em></u></strong> et <em style=\"font-family: Calibri; \"><b><u>Marsouins</u></b></em> du <em style=\"font-family: Calibri; \"><b><u>lundi soir 19h</u></b></em> : entrainement sur la piscine <em style=\"font-family: Calibri; \"><b><u>Alex Jany</u></b></em> de <em style=\"font-family: Calibri; \"><b><u>19h &agrave; 19h50</u></b></em> le lundi soir &eacute;galement.&nbsp;</span></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">  </span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour les groupes&nbsp;<em><b><u>Licornes, Cachalots</u></b></em> et <em><b><u>Perfs ados</u></b></em> du <strong><i><u>lundi soir 19h45</u></i></strong> : entrainement sur la piscine <em><b><u>Alex Jany</u></b></em> de <em><b><u>19h55 &agrave; 20h45</u></b></em> le lundi soir &eacute;galement.&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour le groupe <strong><i><u>Loisir Adultes du lundi soir 19h</u></i></strong> : possibilit&eacute; de se joindre au cours adulte du <em><b><u>mardi soir</u></b></em> sur la piscine <em><b><u>Alex Jany</u></b></em> (19h-19h55).&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour le groupe <em><b><u>Loisir Adultes du lundi</u></b></em><u> <strong><i>soir 19h45</i></strong></u> : possibilit&eacute; de se joindre au cours adulte du <em><b><u>mardi soir</u></b></em> sur la piscine <em><b><u>Alex Jany</u></b></em> (19h55-20h45).&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour les groupes<strong><i><u> Tritons, Avenirs </u></i></strong>et<strong><i><u> Marsouins </u></i></strong>du<strong><i><u> mercredi soir 19h</u></i></strong> : entrainement sur la piscine <em><b><u>Alex Jany</u></b></em> de <em><b><u>19h &agrave; 19h50</u></b></em> le mercredi soir &eacute;galement.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour les groupes <em><b><u>Dauphins, Cachalots</u></b></em> et<strong><i><u> Perfs ados </u></i></strong>du<strong><i><u> mercredi soir 19h45</u></i></strong> : entrainement sur la piscine <em><b><u>Alex Jany</u></b></em> de <em><b><u>19h55 &agrave; 20h45</u></b></em> le mercredi soir &eacute;galement.&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour le groupe <strong><i><u>Loisir Adultes du mercredi soir 19h</u></i></strong> : possibilit&eacute; de se joindre au cours adulte du <em><b><u>jeudi soir</u></b></em> sur la piscine <em><b><u>Alex Jany</u></b></em> (19h-19h55).&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- pour le groupe <em><b><u>Loisir Adultes du mercredi</u></b></em><u> <strong><i>soir 19h45</i></strong></u> : possibilit&eacute; de se joindre au cours adulte du <em><b><u>jeudi soir</u></b></em> sur la piscine <em><b><u>Alex Jany</u></b></em> (19h55-20h45).&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">En esp&eacute;rant que vous puissiez prendre vos dispositions pour pouvoir assister aux s&eacute;ances, et que nos adh&eacute;rents de Toulouse-Lautrec puissent continuer &agrave; pratiquer dans les meilleures conditions.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de votre compr&eacute;hension,&nbsp;</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Sportivement,</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Le secr&eacute;tariat</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("17/10/2012"));
			actu.setTitle("Horaires entraînements vacances de Toussaint 2012	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p>&nbsp;ERRATUM BIS !!!!A l'attention des groupes comp&eacute;titions, Poussins, Benjamins, Minimes, CJS D&eacute;partementaux, section sportive R. Naves, Elite et Masters, veuillez trouver, ci-joint, les cr&eacute;neaux propos&eacute;es pendant les prochaines vacances. Bonne lecture !	</p><p><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">1) Voici les s&eacute;ances que nous proposons aux enfants <strong>du groupe Poussins</strong> <strong>encadr&eacute;s par Clo&eacute;</strong>,  lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\">&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <u><b>1<sup>&egrave;re</sup>  semaine&nbsp;</b></u>: du Lundi 29/10 au Mercredi  31/10/2012&nbsp;: <span style=\"color: rgb(255, 0, 0); \">les Lundi, Mardi et Mercredi, de 19h &agrave; 21h sur L&eacute;o Lagrange.</span>&nbsp;</p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"> </span></span><!--[if !supportLists]--><span style=\"font-size:10.0pt;font-family:\">-<span style=\"font-size: 7pt; font-family: 'Times New Roman'; \">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><!--[endif]--><b><u><span style=\"font-size:10.0pt;font-family:\">2<sup>&egrave;me</sup> semaine</span></u></b><span style=\"font-size:10.0pt;font-family:\">&nbsp;: <span style=\"color:red\">du Lundi 05/11 au Mercredi 07/11/2012&nbsp;: du Lundi au Mercredi, de 19h &agrave; 21h sur Nakache.</span></span></p><p><span style=\"font-size:10.0pt;font-family:\"><span style=\"color:red\"><o:p></o:p></span></span><span style=\"font-size:10.0pt;font-family:\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; les Jeudi 08/11 et Vendredi 09/11, de 19h &agrave; 21h sur L&eacute;o Lagrange.<o:p></o:p></span></p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">2) Voici&nbsp;les s&eacute;ances que nous proposons aux enfants <strong>du groupe Benjamins encadr&eacute;s par  S&eacute;bastien</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Mercredi  31/10/2012&nbsp;: </span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">les Lundi, Mardi et Mercredi, de 09h &agrave; 11h30 sur SupA&eacute;ro et de 19h &agrave; 21h sur  L&eacute;o Lagrange.&nbsp;</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></span><b style=\"text-indent: 35.4pt; \"><u><span style=\"font-size:10.0pt;font-family:\">2<sup>&egrave;me</sup> semaine</span></u></b><span style=\"text-indent: 35.4pt; font-size: 10pt; font-family: Arial, sans-serif; \">&nbsp;: &nbsp;<span style=\"color:red\">les Lundi 05/11 et Mercredi 07/11/2012&nbsp;: de 19h &agrave; 21h sur Nakache.</span></span></p><p class=\"MsoNormal\" style=\"text-indent:35.4pt\"><span style=\"font-size:10.0pt;font-family:\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style=\"color: rgb(255, 0, 0); \">Mardi 06/11&nbsp;: de 12h &agrave; 14h sur Bellevue et de 19h &agrave; 21h sur Nakache.</span><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"margin-left:106.2pt\"><span style=\"font-size:10.0pt;font-family:\">&nbsp; Jeudi 08/11&nbsp;: de 09h &agrave; 11h30 et de 19h &agrave; 20h45 sur Alex Jany.<o:p></o:p></span></p><p class=\"MsoNormal\" style=\"margin-left:106.2pt\"><span style=\"font-size:10.0pt;font-family:\">&nbsp; Vendredi 09/11&nbsp;: de 19h &agrave; 21h sur L&eacute;o Lagrange.<o:p></o:p></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">3) &nbsp;Voici&nbsp;les s&eacute;ances que nous proposons aux enfants <strong>du groupe Minimes encadr&eacute;s par  J&eacute;r&eacute;my</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Mercredi  31/10/2012&nbsp;: </span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">l</span></span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">es Lundi, Mardi, Mercredi et Vendredi : de 12h &agrave; 14h30 sur Bellevue et de 19h &agrave; 21h &agrave; chaque fois.</span></span></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p><span style=\"font-size: small; \"> </span></p><p class=\"MsoNormal\">- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<b style=\"text-indent: 35.4pt; \"><u>2<sup>&egrave;me</sup> semaine</u></b><span style=\"text-indent: 35.4pt; \">&nbsp;: &nbsp;<span style=\"color: red; \">du Lundi 05/11 au Mercredi 07/11/2012&nbsp;: de 12h &agrave; 14h sur Bellevue et de 19h &agrave; 21h sur Nakache (bassin 25m int&eacute;rieur).</span></span></p><p>&nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size: small; \"><span style=\"color: red; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>Jeudi 08/11&nbsp;: de 19h &agrave; 20h45 sur Alex Jany.</span></p><p><span style=\"font-size: small; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Vendredi 09/11&nbsp;: de 14h &agrave; 16h sur Alex Jany.</span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">4)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs <strong>d</strong><strong>u groupe CJS D&eacute;partementaux encadr&eacute;s  par St&eacute;phane</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Samedi 03/11/2012&nbsp;: </span></span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">les Lundi,  Mardi et Mercredi, de 12h &agrave; 14h30 sur Bellevue et de 19h &agrave; 20h30 sur SupA&eacute;ro (ISAE), le Vendredi  de 12h &agrave; 14h30 sur Bellevue et de 19h &agrave; 21h sur L&eacute;o Lagrange et le Samedi de 15h30 &agrave; 17h30 sur L&eacute;o</span></span>&nbsp;Lagrange<span style=\"font-family: 'Comic Sans MS'; font-size: small; \">.</span></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font><span style=\"font-family: 'Comic Sans MS'; \"><span style=\"font-size: small; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>2<sup>&egrave;me</sup>  semaine</u></b>&nbsp;:&nbsp;<span style=\"text-indent: 35.4pt; \"><span style=\"color: red; \">du Lundi 05/11 au Vendredi 09/11/2012&nbsp;: de 19h &agrave; 21h sur L&eacute;o Lagrange.</span></span></span></span></p><p class=\"MsoNormal\" style=\"text-indent:35.4pt\"><span style=\"font-size:10.0pt;font-family:\"><span style=\"color:red\"><o:p></o:p></span></span></p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">5)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs <strong>du groupe Raymond Naves encadr&eacute;s par  Cyril</strong>, lors des prochaines vacances :&nbsp;</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Samedi  03/11/2012&nbsp;: </span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">l</span></span></span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">es Lundi, Mardi et Mercredi, de 09h &agrave; 11h30 et de  19h &agrave; 20h30 sur SupA&eacute;ro (ISAE), le Vendredi de 12h &agrave; 14h et de 19h &agrave; 21h et le  Samedi de 15h30 &agrave; 17h30 sur L&eacute;o Lagrange, les 2 fois.</span></span></span><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"><b>- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<u>2<sup>&egrave;me</sup> semaine</u></b>&nbsp;: &nbsp;<span style=\"color: red; \">du Lundi 05/11 au Mercredi 07/11/2012&nbsp;: de 12h &agrave; 14h sur Bellevue et de 19h &agrave; 21h sur L&eacute;o Lagrange.</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Jeudi 08/11&nbsp;: de 09h &agrave; 11h30 et de 19h &agrave; 20h45 sur Alex Jany.</span></span></p><p class=\"MsoNormal\" style=\"margin-left:106.2pt\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp; &nbsp; &nbsp; &nbsp;Vendredi 09/11&nbsp;: de 09h &agrave; 11h30 et de 14h &agrave; 16h sur Alex Jany.</span></span>&nbsp;</p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">6)&nbsp;Voici les s&eacute;ances que nous proposons <strong>aux nageurs du groupe Elite encadr&eacute;s par Philippe  et Lo&iuml;c</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Samedi  03/11/2012&nbsp;: </span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">les Lundi, Mardi et Mercredi, de 09h &agrave; 11h30 et de  19h &agrave; 20h30 sur SupA&eacute;ro (Philippe), ainsi que de 12h &agrave; 14h sur L&eacute;o Lagrange (Lo&iuml;c), le Vendredi de 12h &agrave; 14h et de 19h &agrave; 21h et le  Samedi de 15h30 &agrave; 17h30 sur L&eacute;o Lagrange, les 2 fois.</span></span></span></p><p class=\"MsoNormal\"><!--[if !supportLists]--><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!--[endif]--><b><u>2<sup>&egrave;me</sup> semaine</u></b>&nbsp;: <span style=\"color: rgb(31, 73, 125); \">&nbsp;</span>Lundi 05/11&nbsp;: de 06h45 &agrave; 08h15 et de 12h &agrave; 14h sur L&eacute;o Lagrange et de 18h30 &agrave; 20h sur SupA&eacute;ro.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size:10.0pt;font-family:\"><o:p></o:p></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Mardi 06/ 11&nbsp;: de 06h45 &agrave; 08h15 et de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size:10.0pt;font-family:\"><o:p></o:p></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Mercredi 07/11&nbsp;: de 06h45 &agrave; 08h15 et de 12h &agrave; 14h sur L&eacute;o Lagrange et de 18h30 &agrave; 20h sur SupA&eacute;ro.</span></span></p><p class=\"MsoNormal\"><span style=\"font-size:10.0pt;font-family:\"><o:p></o:p></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Jeudi 08/11&nbsp;: de 06h45 &agrave; 08h15 et de 12h &agrave; 14h sur L&eacute;o Lagrange et de 19h &agrave; 20h30 sur SupA&eacute;ro.</span></span></p><p class=\"MsoNormal\"><span style=\"font-family: 'Comic Sans MS'; font-size: small; \">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Vendredi 09/11&nbsp;: de 06h45 &agrave; 08h15 et de 12h &agrave; 14h sur L&eacute;o Lagrange et de 18h30 &agrave; 20h sur SupA&eacute;ro.</span></p><p class=\"MsoNormal\"><span style=\"background-color: rgb(255, 255, 153); \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">7)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs <strong>du groupe Masters encadr&eacute;s par R&eacute;mi</strong>,  lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>1<sup>&egrave;re</sup>  semaine</u></b>&nbsp;: du Lundi 29/10 au Samedi  03/11/2012&nbsp;: </span><span style=\"color: rgb(255, 0, 0); \"><span style=\"font-family: 'Comic Sans MS'; \">les Lundi, Mardi, Mercredi et Vendredi, de 12h &agrave; 14h et le Samedi de 15h30 &agrave; 17h30 sur L&eacute;o  Lagrange &agrave; chaque fois.</span></span></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b><u>2<sup>&egrave;me</sup>  semaine</u></b>&nbsp;: du Lundi 05/11 au Vendredi  09/11/2012&nbsp;: du Lundi au Vendredi, de 12h &agrave; 14h sur L&eacute;o Lagrange &agrave; chaque  fois.</span></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"><br type=\"_moz\" /></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note et de r&eacute;pondre pr&eacute;sent, &nbsp;</span></span></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p><p class=\"MsoNormal\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt; \"><o:p></o:p></span></font></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("17/10/2012"));
			actu.setTitle("Fermeture de Toulouse-Lautrec ce Mercredi 17/10	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">ATTENTION : derni&egrave;re minute !!!! </span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Les services municipaux viennent de nous informer (&agrave; 16h) que <strong>la piscine Toulouse-Lautrec n'est pas ouverte cet apr&egrave;s-midi </strong>pour cause de probl&egrave;me technique. Par cons&eacute;quent, les cours de natation ne seront donc pas assur&eacute;s ce soir. Nous sommes navr&eacute;s du d&eacute;sagr&eacute;ment engendr&eacute;.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("22/10/2012"));
			actu.setTitle("Fermeture d'Alex Jany pour 15 jours...	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">L'h&eacute;catombe continue !!!! Apr&egrave;s la fermeture de <strong>Toulouse-Lautrec pour 1 mois, du 22/10 au 25/11</strong> pour r&eacute;fection des canalisations, c'est maintenant au tour <strong>d'Alex Jany d'&ecirc;tre ferm&eacute;e depuis ce Vendredi 19/10 jusqu'au Dimanche 04/11 inclus</strong>, suite &agrave; la casse d'une pompe &agrave; chlore.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Les adh&eacute;rents de Lautrec devant rattraper leurs cours sur Alex Jany, ainsi que les adh&eacute;rents de ce bassin des Mardi et Jeudi soirs n'auront donc pas de cours cette semaine, faute de bassins municipaux en &eacute;tat de marche...</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Reste ouverte &agrave; ce jour, L&eacute;o Lagrange, les Jeudi et Vendredi soirs et Samedi apr&egrave;s-midi, avant la pause traditionnelle des vacances de Toussaint.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Veuillez prendre note de cet &eacute;tat de fait ind&eacute;pendant de notre volont&eacute;, nous le subissons tout autant que vous, et ne pouvons pas vous proposer des solutions de remplacement d&eacute;centes, faute de cr&eacute;neaux disponibles sur les maigres piscines restantes ouvertes (3 sur un parc de 7).&nbsp;</span></span><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Toutefois, nous ferons le maximum pour r&eacute;cup&eacute;rer les s&eacute;ances manqu&eacute;es &agrave; l'occasion des vacances de F&eacute;vrier ou de P&acirc;ques 2013.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Nous vous tenons informer informer au plus t&ocirc;t du retour &agrave; une situation normale pour le retour des vacances, le Lundi 12/11/2012.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("24/10/2012"));
			actu.setTitle("Cours de substitution	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">A l'attention des groupes Avenirs, Licornes, Marsouins et Cachalots de Toulouse-Lautrec et Alex Jany, ce mot pour vous informer que possibilit&eacute; vous est donn&eacute;e, suite &agrave; la fermeture du bassin o&ugrave; vous &eacute;voluez d'ordinaire, de rattraper la s&eacute;ance annul&eacute;e en semaine, </span><strong><span style=\"font-family: 'Comic Sans MS'; \">par la s&eacute;ance du Samedi apr&egrave;s-midi sur L&eacute;o Lagrange de 15h30 &agrave; 16h30 ou de 16h30 &agrave; 17h30.</span></strong></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">N'h&eacute;sitez pas &agrave; vous y rendre de facto (veuillez avertir le secr&eacute;tariat au pr&eacute;alable), les &eacute;ducateurs vous accueilleront avec plaisir et cela permettra d'att&eacute;nuer la coupure de 15 jours inh&eacute;rente aux vacances de Toussaint. A Samedi !</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("31/10/2012"));
			actu.setTitle("Compositions équipes Interclubs 10 et 11 Novembre 2012	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p>&nbsp;A l'attention des <strong>120</strong> nageurs (6 &eacute;quipes Filles et 6 &eacute;quipes Gar&ccedil;ons de 10 nageurs) qui seront sur le pont le week-end prochain lors des Interclubs R&eacute;gionaux (Muret, 8 &eacute;quipes) et D&eacute;partementaux (Colomiers, 4 &eacute;quipes), voici la composition exacte de vos &eacute;quipes. Sous r&eacute;serve de forfait de derni&egrave;re minute bien entendu, en cas de probl&egrave;me merci de nous en faire part au plus vite afin de pouvoir agir en cons&eacute;quence. Bonne lecture !</span></span></p><p>&nbsp;</p>“	</p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Attention, &eacute;quipes actualis&eacute;es ce jour, Mardi 06/11/2012, suite &agrave; des forfaits et changements de derni&egrave;re minute, veuillez les consulter &agrave; nouveau...</span></span></p><p><a href=\"/entrepot/transfert/file/2012-2013/Interclubs%20Filles%20Novembre%202012(1).pdf\"><span style=\"font-size: large; \"><span style=\"font-family: 'Comic Sans MS'; \">Equipes Interclubs Filles, Muret et Colomiers.</span></span></a></p><p><span style=\"font-size: large; \"><span style=\"font-family: 'Comic Sans MS'; \"><a href=\"/entrepot/transfert/file/2012-2013/Interclubs%20Gar%C3%A7ons%20Novembre%202012(1).pdf\">Equipes Interclubs Gar&ccedil;ons, Muret et Colomiers.</a></span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("30/10/2012"));
			actu.setTitle("Fête des Sports ASPTT TOULOUSE Omnisports	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p>&nbsp;<b style=\"margin: 0px; padding: 0px; color: rgb(29, 29, 27); font-family: 'Helvetica Neue', Arial, Helvetica, sans-serif; line-height: 18px; \"><b style=\"margin: 0px; padding: 0px; \"><div style=\"margin: 0px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; display: inline !important; \"><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; display: inline !important; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">L&rsquo;ASPTT Grand Toulouse vous invite &agrave; participer avec votre section &agrave; la &laquo;&nbsp;F&ecirc;te des sports&nbsp;&raquo; qui se d&eacute;roulera le Samedi 24 novembre 2012 &agrave; partir de 20h dans la grande salle Omnisports situ&eacute;e au 47, rue de soupetard, 31500 Toulouse.</span></span></p></div></b><div style=\"margin: 0px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Cette soir&eacute;e, anim&eacute;e par le groupe de chanteuses &laquo;&nbsp;Duo Plume&nbsp;&raquo; ainsi que la Banda &laquo;&nbsp;Grapp&rsquo;s Del N&eacute;gret&nbsp;&raquo;, vous permettra de vous retrouver entre amis autour d&rsquo;un repas&nbsp; traiteur &laquo;&nbsp;Antillais&nbsp;&raquo; et de danser jusqu&rsquo;&agrave; l&rsquo;aube.</span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \"><span style=\"margin: 0px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; vertical-align: baseline; text-decoration: underline; \">Modalit&eacute;s d&rsquo;inscriptions :</span></span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Chaque section devra r&eacute;server un nombre de places et les revendre &agrave; ses adh&eacute;rents.</span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">&nbsp;<span style=\"margin: 0px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; vertical-align: baseline; text-decoration: underline; \">Tarif de la soir&eacute;e :&nbsp;&nbsp;</span>20 &euro;/Personne</span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">(Ap&eacute;ritif, repas traiteur, vins et prestations musicales).</span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Si vous &ecirc;tes donc int&eacute;ress&eacute;s en tant qu'adh&eacute;rent de la natation, merci de vous rapprocher au plus vite du secr&eacute;tariat de la section et de r&eacute;server le nombre de places souhait&eacute;es.</span></span></p><p style=\"margin: 0px 0px 10px; padding: 0px; border: 0px; font-weight: inherit; font-style: inherit; font-family: inherit; vertical-align: baseline; \"><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">ATTENTION : date limite d'inscription fix&eacute;e au Lundi 12 Novembre 2012.</span></span></p></div></b></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format1.parse("07/11/2012"));
			actu.setTitle("Réouverture piscine Alex Jany	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Enfin une bonne nouvelle dans ce contexte morose pour les piscines municipales, Alex Jany r&eacute;ouvre ses portes, demain matin, Jeudi 08 Novembre 2012, 09h, et sera donc &agrave; m&ecirc;me de vous recevoir d&egrave;s la reprise des activit&eacute;s, &agrave; l'issue des vacances de la Toussaint, <strong>ce Lundi 12/11</strong>.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">N'oubliez pas que les adh&eacute;rents des Lundi et Mercredi soirs de Toulouse-Lautrec (encore ferm&eacute;e jusqu'au 25/11 inclus) sont reclass&eacute;s les m&ecirc;mes soirs sur Alex Jany donc, de 19h &agrave; 19h50 ou de 19h55 &agrave; 20h45 selon le cr&eacute;neau d'inscription.</span></span></p><p><span style=\"font-size: small; \"><span style=\"font-family: 'Comic Sans MS'; \">Merci de prendre note, &agrave; la semaine prochaine.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/23/2012"));
			actu.setTitle("Ouverture Toulouse-Lautrec repoussée	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">La municipalit&eacute; vient de nous informer qu'elle ne serait pas en mesure de r&eacute;ouvrir le bassin de Toulouse-Lautrec, ce Lundi 26/11/2012, comme pr&eacute;vusuite aux travaux effectu&eacute;s. En effet, suite &agrave; une voie d'eau dans la salle des machines, la piscine est ferm&eacute;e jusqu'&agrave; la fin de semaine prochaine.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En cons&eacute;quence, nous maintenons le dispositif pr&eacute;c&eacute;demment &eacute;prouv&eacute; (depuis le 12/11) pour les adh&eacute;rents des Lundi et Mercredi soirs, en continuant &agrave; nager sur la piscine Alex Jany aux m&ecirc;mes horaires ; pour ce qui est de nos comp&eacute;titeurs, nous repartons &eacute;galement sur les cr&eacute;neaux de substitution qui ont &eacute;t&eacute; propos&eacute;s entre Raymond Naves (Benjamins et Minimes) et L&eacute;o Lagrange (CJS D&eacute;partementaux) ainsi que SupA&eacute;ro (CJS D&eacute;partementaux).</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Merci de prendre note et de nous excuser du d&eacute;sagr&eacute;ment engendr&eacute;.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/23/2012"));
			actu.setTitle("Cours d'aquagym	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A la demande de certaines de nos adh&eacute;rentes sur l'activit&eacute; Aquagym, voici les cr&eacute;neaux que nous avons cibl&eacute; pour vous proposer un deuxi&egrave;me cours par semaine (pour le m&ecirc;me tarif !) en tenant compte de la fr&eacute;quentation et dans la mesure o&ugrave; nous pr&eacute;f&egrave;rons avoir un bassin bien rempli, plut&ocirc;t que d&eacute;peupl&eacute;....</span></span></p><p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- Pour les adh&eacute;rentes d'Alex Jany (Mardi et Jeudi soirs) : possibilit&eacute; de venir pratiquer une seconde fois le Jeudi soir de 19h50 &agrave; 20h45.</span></span></strong></p><p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- Pour les adh&eacute;rentes de L&eacute;o Lagrange (Jeudi et Vendredi soirs) : possibilit&eacute; de venir pratiquer une seconde fois le Vendredi soir de 19h &agrave; 20h.</span></span></strong></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Veuillez prendre note, cette disposition &eacute;tant effective d&egrave;s la semaine prochaine (&agrave; compter du Jeudi 29/11).</span></span></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/21/2012"));
			actu.setTitle("Retour sur les Interclubs Toutes Catégories	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">Les 10 et 11 novembre 2012 se sont d&eacute;roul&eacute;s dans toute la France, les traditionnels Interclubs. Des &eacute;quipes de 10 nageurs ou 10 nageuses concourent les unes contre les autres pour d&eacute;terminer la hi&eacute;rarchie nationale. C&rsquo;est une des rares comp&eacute;titions collectives en natation et de plus, dans une m&ecirc;me &eacute;quipe, on peut trouver un nageur de 10 ans associ&eacute; &agrave; un nageur de 50 ans.</span></span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">Pour les nageurs de l&rsquo;ASPTT, cette comp&eacute;tition marquait la reprise de la saison sportive puisque c&rsquo;&eacute;tait la premi&egrave;re comp&eacute;tition de l&rsquo;ann&eacute;e. Nous avons pr&eacute;sent&eacute; cette ann&eacute;e 12 &eacute;quipes, soit 120 nageurs&nbsp;!!!</span></span></span><span style=\"font-size: 12pt; text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size:12.0pt\"><o:p></o:p></span></span><p class=\"MsoListParagraphCxSpFirst\" style=\"text-align: justify; text-indent: -18pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><!--[if !supportLists]-->&middot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!--[endif]-->4 &eacute;quipes concouraient en Poule R&eacute;gionale A &agrave; Muret (2 en Dames et 2 en Messieurs)</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoListParagraphCxSpMiddle\" style=\"text-align: justify; text-indent: -18pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><!--[if !supportLists]-->&middot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!--[endif]-->4 &eacute;quipes en Poule R&eacute;gionale B &agrave; Muret (2 en Dames et 2 en Messieurs)</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoListParagraphCxSpLast\" style=\"text-align: justify; text-indent: -18pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><!--[if !supportLists]-->&middot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!--[endif]-->4 &eacute;quipes en Poule D&eacute;partementale &agrave; Colomiers (2 en Dames et 2 en Messieurs)</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p></p><p class=\"MsoNormal\" style=\"text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size:12.0pt\"><o:p></o:p></span></p>“	<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b style=\"text-align: center; text-indent: 18pt;\"><u>En Poule A R&eacute;gionale &agrave; Muret&nbsp;:</u></b>  </span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">La comp&eacute;tition fut &acirc;pre entre les meilleurs clubs de la r&eacute;gion, avec notamment de beaux relais accroch&eacute;s.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Dames, l&rsquo;&eacute;quipe 1 occupe la 2<sup>&egrave;me</sup> place r&eacute;gionale et l&rsquo;&eacute;quipe 2 la 8<sup>&egrave;me</sup> place.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Messieurs, l&rsquo;&eacute;quipe 1 finit &agrave; la 3<sup>&egrave;me</sup> place r&eacute;gionale et l&rsquo;&eacute;quipe 2 la 8<sup>&egrave;me</sup> place.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p<p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l&rsquo;ASPTT, cette ann&eacute;e, c&rsquo;est l&rsquo;&eacute;galit&eacute; hommes/femmes puisque les &eacute;quipes premi&egrave;res sont toutes les deux 17&egrave;mes au niveau national. Pour info, il y a cette ann&eacute;e 1200 &eacute;quipes f&eacute;minines class&eacute;es au niveau national et 1400 &eacute;quipes masculines.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><i>Les&nbsp; &eacute;quipes premi&egrave;res Dames et Messieurs sont donc class&eacute;es en Nationale 1A (les 20 meilleures &eacute;quipes nationales). C&rsquo;est une belle performance pour notre club.</i></b></span></span><b><i><span style=\"font-size:12.0pt\"><o:p></o:p></span></i></b></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><u>En Poule B R&eacute;gionale &agrave; Muret&nbsp;:</u></b></span></span><b><u><span style=\"font-size:12.0pt\"><o:p></o:p></span></u></b></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Dames, l&rsquo;&eacute;quipe 3 termine &agrave; la 5<sup>&egrave;me</sup> place de la poule et l&rsquo;&eacute;quipe 4 &agrave; la 11<sup>&egrave;me</sup> place.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Messieurs, l&rsquo;&eacute;quipe 4 compos&eacute;e d&rsquo;entra&icirc;neurs et de nageurs ma&icirc;tres finit &agrave; la 7<sup>&egrave;me</sup> place de la poule et l&rsquo;&eacute;quipe 3 &agrave; la 10<sup>&egrave;me</sup> place.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><u>La poule D&eacute;partementale &agrave; Colomiers</u></b></span></span><b><u><span style=\"font-size:12.0pt\"><o:p></o:p></span></u></b></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Dames, 14 &eacute;quipes class&eacute;es. L&rsquo;&eacute;quipe 6 compos&eacute;e uniquement de nageuses benjamines (11-12 ans) termine 4<sup>&egrave;me</sup> et l&rsquo;&eacute;quipe 5 finit 8<sup>&egrave;me</sup>.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Messieurs, 18 &eacute;quipes class&eacute;es. L&rsquo;&eacute;quipe 5 occupe la 4<sup>&egrave;me</sup> place et l&rsquo;&eacute;quipe 6 la 10<sup>&egrave;me</sup>.</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: medium;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Au classement national des Interclubs, gr&acirc;ce aux 12 &eacute;quipes et donc aux 120 nageurs qui ont particip&eacute;, notre club termine &agrave; la 6<sup>&egrave;me</sup> place fran&ccedil;aise (sur 701 clubs class&eacute;s)&nbsp;!!!</b></span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><b><span style=\"font-size:12.0pt\"><o:p></o:p></span></b><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Encore un grand bravo &agrave; tous &hellip; nageurs, entra&icirc;neurs, parents supporters, officiels. Nous partageons ensemble cette bonne performance de notre club.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">La saison est belle et bien lanc&eacute;e.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/26/2012"));
			actu.setTitle("Championnats de France 25m Angers	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Un peu plus d'une semaine apr&egrave;s l'&eacute;v&egrave;nement, veuillez trouver, ci-joint, un petit compte-rendu des performances de nos 7 nageurs qualifi&eacute;s (2 Filles et 5 Gar&ccedil;ons) pour l'occasion.</span></span></p>“	<p>&nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">Du Jeudi 15 au Dimanche 18 Novembre ont eu lieu &agrave; Angers les Championnats de France en bassin de 25 m&egrave;tres.</span> </span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"> </span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Cette premi&egrave;re &eacute;ch&eacute;ance nationale apr&egrave;s la reprise fut d&rsquo;un tr&egrave;s bon niveau avec notamment deux records du monde battus, ainsi qu&rsquo;un record d&rsquo;Europe et de nombreux records de France.</span></span></span></p><p><span style=\"font-size: small;\"><span style=\"font-size:12.0pt\"><o:p></o:p></span></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Dans ce contexte tr&egrave;s relev&eacute;, les nageurs de l&rsquo;ASPTT Grand Toulouse ont profit&eacute; de la concurrence pour am&eacute;liorer leurs performances et glaner des places d&rsquo;honneur.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Chez les Dames,</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Eve-Marie GUERMONPREZ, </b>de retour sur le club apr&egrave;s une parenth&egrave;se de 2 ans sur Fr&eacute;jus, participe aux 50m et 100m brasse o&ugrave; elle r&eacute;alise ses meilleurs temps personnels (en 25m), ainsi qu&rsquo;au 50m nage libre. Sur le 50m brasse, elle termine &agrave; une encourageante 17<sup>&egrave;me</sup> place. Pers&eacute;v&eacute;reance Eve...</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Charlotte KIEFFER, </b>nouvelle venue sur le club,<b>&nbsp;</b>a particip&eacute; aux 100m, 200m, 400m et 800m nage libre. C&rsquo;est sur les 100m et 200 m&egrave;tres qu&rsquo;elle r&eacute;alise ses meilleures places (20<sup>&egrave;me)</sup>. Une sp&eacute;cialisation en vue ?</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Chez les Messieurs,</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Victor MIGEON</b> participe aux 100m, 200m et 400m 4 nages, ainsi qu&rsquo;au 200m brasse.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Sur 200m brasse, il bat le record du club en 2&rsquo;15&rsquo;&rsquo;88. En 4 Nages, il bat &eacute;galement le record du club sur le 200m. Il est finaliste sur les 200m et 400 m&egrave;tres 4 nages o&ugrave; il termine 5<sup>&egrave;me</sup> (4<sup>&egrave;me</sup>&nbsp; nageur fran&ccedil;ais) et 7<sup>&egrave;me</sup> (6<sup>&egrave;me</sup> nageur fran&ccedil;ais). Une tr&egrave;s belle moisson !!!!</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>S&eacute;bastien BORDERAS</b> participe aux 100m, 200m et 400m nage libre et 200m papillon. Il termine 17<sup>&egrave;me</sup> du 200m nage libre et 11<sup>&egrave;me</sup> du 200m papillon. Il actualise &eacute;galement 2 nouveaux records du club sur le 400m nage libre et le 200m papillon. Un bilan largement positif pour lancer une saison sous les meilleurs horizons...</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Edouard LEHOUX</b> participe aux 200m, 400m, 800m et 1500m nage libre. Il am&eacute;liore son temps des interclubs sur le 400 et fait une de ses meilleures performances en s&eacute;ries matinales du 200m nage libre. Sur le 1500m nage libre, il explose son meilleur temps de pr&egrave;s de 30 secondes et prend une belle 19<sup>&egrave;me</sup> place.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Micha&euml;l KARGBO</b> participe aux 50m, 100m et 200m brasse o&ugrave; il r&eacute;alise &agrave; chaque fois ses meilleurs temps personnels. Encourageant d&eacute;but de saison pour notre vice-champion du monde des ma&icirc;tres qui participait &agrave; Angers &agrave; ces premiers championnats de France en petit bassin, et ce &agrave; l'&acirc;ge de 27 ans !</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>Martin ROYO</b>, diminu&eacute; depuis plusieurs semaines par une blessure &agrave; l&rsquo;&eacute;paule, est parvenu &agrave; maintenir son niveau sur les 50m et 100 m&egrave;tres papillon (21<sup>&egrave;me</sup>). Il r&eacute;alise son meilleur temps sur 100 m&egrave;tres 4 nages (59&rsquo;&rsquo;06). Comme un vieux briscard.</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size:12.0pt\"><o:p></o:p></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Les nageurs &eacute;taient accompagn&eacute;s de leurs coachs, Lo&iuml;c PILORGET et Philippe MIGEON. Un grand bravo &agrave; tous pour ces championnats de France r&eacute;ussis.</span></span><span style=\"font-size:12.0pt\"><o:p></o:p></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/30/2012"));
			actu.setTitle("Annulation séance du 01/12/2012 Léo Lagrange	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l'attention des adh&eacute;rents du Samedi apr&egrave;s-midi de L&eacute;o Lagrange (Avenirs, Licornes, Marsouins, Cachalots), nous ne pourrons assurer les cours ce 01/12/2012.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En effet, la piscine est r&eacute;quisitionn&eacute;e pour l'organisation d'une comp&eacute;tition d&eacute;partementale, dont le d&eacute;roulement est pr&eacute;vu toute la journ&eacute;e sur ce site (09h &agrave; 19h).&nbsp;</span></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Merci de prendre note et de ne pas vous d&eacute;placer en vain.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/30/2012"));
			actu.setTitle("Fermeture de Lautrec prolongée	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Comme nous pouvions le craindre, la municipalit&eacute; vient de nous informer que la fermeture de la piscine Toulouse-Lautrec est prolong&eacute;e toute la semaine prochaine, le temps de finaliser, une fois pour toute, les travaux, afin de remettre en service l'installation.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Par cons&eacute;quent, nous prolongeons le dispostif de substitution sur Alex Jany, les Lundi et Mercredi pour les adh&eacute;rents de l'Ecole de Natation et Loisirs, ainsi que sur les autres bassins (Raymond Naves, L&eacute;o Lagrange, SupA&eacute;ro) pour les groupes comp&eacute;titions.</span></span></p><p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">R&eacute;ouverture programm&eacute;e le Lundi 10 D&eacute;cembre</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">, nous vous tenons informer d'ici l&agrave;. Veuillez nous excuser de ce nouveau contre-temps.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("11/30/2012"));
			actu.setTitle("1ère compétition interne Mercredi 12/12/12	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l'attention des groupes Dauphins, Avenirs, Licornes, Marsouins, Cachalots et Perf Ados, de l'Ecole de Natation, ce petit mot afin de vous informer de la tenue, <strong>ce Mercredi 12 D&eacute;cembre 2012, de 19h &agrave; 20h45 sur Alex Jany, de la 1&egrave;re comp&eacute;tition interne.</strong></span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Cet &eacute;v&egrave;nment, qui fera office de f&ecirc;te de No&euml;l et cl&ocirc;turera ce premier trimestre de travail est primordial pour mat&eacute;rialiser les progr&egrave;s effectu&eacute;s dans ce laps de temps. N'h&eacute;sitez pas &agrave; venir nombreux, une r&eacute;compense et un go&ucirc;ter vous attendent &agrave; l'issue. A bient&ocirc;t au bord des bassins.</span></span></p>“	<p>&nbsp;</p><p><a href=\"/entrepot/transfert/file/2012-2013/Convocation%20comp%C3%A9titions/1%C2%B0%20Interne.pdf\"><span style=\"font-size: large;\"><span style=\"font-family: 'Comic Sans MS';\">Convocation 1&egrave;re comp&eacute;tition interne, Mercredi 12 D&eacute;cembre 2012 &agrave; Alex Jany.</span></span></a></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/04/2012"));
			actu.setTitle("Interclubs Masters Colomiers	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<div style=\"text-align: start; text-indent: 0px;\"><strong><span style=\"font-size: medium;\"><span style=\"font-family: 'Comic Sans MS';\">L'ASPTT TOULOUSE Natation, 1er club Masters de Midi-Pyr&eacute;n&eacute;es !!!!!</span></span></strong></div><div style=\"text-align: start; text-indent: 0px;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">En effet, ce&nbsp;</span><span style=\"text-align: justify; text-indent: 35.4pt;\">Dimanche 2 d&eacute;cembre, 4 &eacute;quipes de l&rsquo;ASPTT, soit 40 nageurs, avaient rendez-vous &agrave; la piscine de Colomiers pour participer aux Interclubs R&eacute;gionaux des ma&icirc;tres (25 ans et +).</span></span></span></div><p class=\"MsoNormal\" style=\"text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p>“	<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">Cette comp&eacute;tition par &eacute;quipe (de 10 nageurs) est une &eacute;tape obligatoire puisque qualificative pour les Interclubs Nationaux du mois de janvier. </span></span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">De plus, l&rsquo;addition de toutes les &eacute;quipes entre en compte dans le classement National des Clubs Ma&icirc;tres.</span> </span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">26 &eacute;quipes de toute la r&eacute;gion ont concouru dans une ambiance bon enfant. La comp&eacute;tition fut tr&egrave;s disput&eacute;e et il fallut attendre le dernier relais pour conna&icirc;tre la hi&eacute;rarchie r&eacute;gionale.</span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><u>Les r&eacute;sultats des 4 &eacute;quipes engag&eacute;es</u>&nbsp;:</span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span lang=\"EN-US\">Equipe 4&nbsp;: 20<sup>&egrave;me</sup> </span></span></span><span lang=\"EN-US\" style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Equipe 3&nbsp;: 13&egrave;me </span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"margin-left: 35.4pt; text-align: justify; text-indent: 35.4pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Equipe 2&nbsp;: 7&egrave;me </span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"margin-left: 70.8pt; text-align: justify; text-indent: 35.4pt;\"><span style=\"color: rgb(255, 0, 0);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Equipe 1&nbsp;: 1&eacute;re !!!!!!!</span></span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Tous les nageurs ont donn&eacute; le meilleur d&rsquo;eux-m&ecirc;mes pour cette comp&eacute;tition par &eacute;quipe, merci &agrave; tous pour votre pr&eacute;sence et votre implication.</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span><span style=\"color: rgb(255, 0, 0);\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l&rsquo;addition de toutes les &eacute;quipes, notre club termine &eacute;galement 1<sup>er</sup>&nbsp; club de la r&eacute;gion !!!!</span></span></strong></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Donc un grand merci &agrave; tous les comp&eacute;titeurs, aux entraineurs (qui eux aussi ont mouill&eacute; le maillot), aux officiels et &agrave; nos supporters.</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Rendez-vous dans 8 semaines pour les Interclubs Nationaux des Ma&icirc;tres au Puy-en-Velay.</span></span><span style=\"font-size:14.0pt;font-family:\" bookman=\"\" old=\"\"><o:p></o:p></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/03/2012"));
			actu.setTitle("Calendrier club 2013	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l&rsquo;approche de la nouvelle ann&eacute;e, nous vous avons concoct&eacute; un joli calendrier, avec en illustration une photo de vos jeunes petits loups, pour le modeste co&ucirc;t</span><strong><span style=\"font-family: 'Comic Sans MS';\"> de 5 euros par unit&eacute; !!!</span></strong></span></p><p class=\"MsoNormal\"><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En pi&egrave;ce jointe, un exemple de calendrier ainsi que le bulletin de commande &agrave; remplir d&ucirc;ment.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Merci de nous r&eacute;pondre rapidement, soit par mail, soit par vos enfants au bord des bassins, &nbsp;plus vite les calendriers seront command&eacute;s, plus vite ils vous seront distribu&eacute;s !</span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Dernier d&eacute;lai pour passer commande,<strong> le Vendredi 20 D&eacute;cembre 2013 </strong>(apr&egrave;s, il sera trop tard) pour une distribution la premi&egrave;re quinzaine de Janvier, d&egrave;s la reprise de nos activit&eacute;s, apr&egrave;s les vacances de No&euml;l.</span></span></p><p class=\"MsoNormal\"><o:p></o:p></p>“	<p>&nbsp;</p><p><a href=\"/entrepot/transfert/file/2012-2013/Exemple%20calendrier.pdf\"><span style=\"font-size: medium;\"><span style=\"font-family: 'Comic Sans MS';\">Exemple de calendrier club 2013</span></span></a></p><p><span style=\"font-size: medium;\"><span style=\"font-family: 'Comic Sans MS';\"><a href=\"/entrepot/transfert/file/2012-2013/bulletin_de_commande_calendrier.pdf\">Bulletin de commande</a></span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/07/2012"));
			actu.setTitle("Bilan 1ère journée Médailles du TOAC 2013	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;La piscine L&eacute;o Lagrange accueillait en ce samedi 01 d&eacute;cembre 2012 la 1<sup>&egrave;re</sup> &eacute;tape des m&eacute;dailles du TOAC, cette comp&eacute;tition concerne&nbsp;:</span></span></p><p class=\"MsoNormal\"><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- POUSSINS 1 Gar&ccedil;ons et filles n&eacute;(e)s en 2003</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- POUSSINS 2&nbsp; Gar&ccedil;ons et filles n&eacute;(e)s en 2002</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- BENJAMINS 1 Gar&ccedil;ons et filles n&eacute;(e)s en 2001</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">- BENJAMINS 2 Gar&ccedil;ons et filles n&eacute;(e)s en 2000</span></span></p><p class=\"MsoNormal\">&nbsp;</p><p class=\"MsoNormal\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Au total ce ne sont pas moins de 34 nageurs, 19 filles et 15 gar&ccedil;ons, 17 benjamins et 17 poussins qui ont donn&eacute; le meilleur d'eux-m&ecirc;me afin de repr&eacute;senter au plus leur club pr&eacute;f&eacute;r&eacute;...</span></span></strong><o:p></o:p></p><p>&nbsp;</p><p class=\"MsoNormal\"><o:p></o:p></p>“	<p>&nbsp;</p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Cette ann&eacute;e le club du TOAC (organisateur de cette manifestation) nous propose son traditionnel challenge en 2 r&eacute;unions qualificatives et une Finale :</span></span><o:p></o:p></p><p>&nbsp;</p><p>&nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>- <u>1&egrave;re r&eacute;union qualificative le samedi 1er d&eacute;cembre 2012 &agrave; Leo Lagrange pour le d&eacute;partement 31.</u></b>  </span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><u>Avec les &eacute;preuves suivantes&nbsp;:</u></b></span></span><b><u><o:p></o:p></u></b></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">①</span></b> &ndash; 200 4N Benjamins : Filles et Gar&ccedil;ons&nbsp; </span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">② </span></b>&ndash; 200 NL Poussins : Filles et Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">① </span></b>&ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Poussins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">②</span></b> &ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Benjamins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">③</span></b> &ndash; 100 PAP / 100 NL / 100 DOS / 100 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Poussins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">④</span></b> &ndash; 100 PAP / 100 NL /100 DOS /100 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Benjamins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>- <u>2&egrave;me r&eacute;union qualificative le samedi 16 f&eacute;vrier 2013 &agrave; Leo Lagrange pour le d&eacute;partement 31 :</u></b></span></span></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">①</span></b> &ndash; 200 4N Poussins : Filles et Gar&ccedil;ons </span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">②</span></b> &ndash; 200 NL Benjamins : Filles et Gar&ccedil;ons </span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">①</span></b> &ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Poussins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">②</span></b> &ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Benjamins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">③</span></b> &ndash; 100 PAP / 100 NL / 100 DOS / 100 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Poussins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">④</span></b> &ndash; 100 PAP / 100 NL /100 DOS /100 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Benjamins: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>- <u>FINALE R&Eacute;GIONALE le samedi 1 juin 2013 &agrave; Leo Lagrange&nbsp;:</u></b></span></span><b><u><o:p></o:p></u></b></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">S&eacute;ries qualificatives sur les 50 m&egrave;tres, les 16 premiers de chaque ann&eacute;e d&rsquo;&acirc;ge seront qualifier pour les finales.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">① </span></b>&ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Poussin: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">②</span></b> &ndash; 50 PAP / 50 NL / 50 DOS / 50 BRASSE</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Benjamin: Filles / Gar&ccedil;ons</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><span lang=\"EN-US\">③ </span></b>&ndash; RELAIS 6 X 50 4N Mixte (3 Filles + 3 Gar&ccedil;ons) compos&eacute; suivant la r&egrave;gle suivante : 1 en DOS, 2 en Brasse, 1 en PAP, 2 en NL.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Pour cette premi&egrave;re manche des m&eacute;dailles du TOAC nos nageurs de l&rsquo;asptt ont parfaitement &eacute;t&eacute; &agrave; la hauteur&nbsp;!!!</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A commencer par les poussins et plus pr&eacute;cis&eacute;ment les poussines avec LEBRET Th&eacute;a 6<sup>&egrave;me</sup> du 200 nage libre en 3&rsquo;03&rsquo;&rsquo;77, 5<sup>&egrave;me</sup> du 50 papillon en 44&rsquo;&rsquo;91, et 4<sup>&egrave;me</sup> du 100 nage libre en 1&rsquo;28&rsquo;&rsquo;15. COTTON Camille se classe 3<sup>&egrave;me</sup> du 100 brasse en 1&rsquo;44&rsquo;&rsquo;00</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Chez les gar&ccedil;ons BEN ABDALLAH Yanis se hisse &agrave; la 3<sup>&egrave;me</sup> place du 100 brasse en 1&rsquo;55&rsquo;&rsquo;12.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En Benjamines, BETEILLE C&eacute;lia se classe 2<sup>&egrave;me</sup> en 31&rsquo;&rsquo;02 au 50 nage libre, toujours 2<sup>&egrave;me</sup> au 100 nage libre en 1&rsquo;08&rsquo;&rsquo;45, au 200 4 nages elle termine 4<sup>&egrave;me</sup> en 2&rsquo;54&rsquo;&rsquo;14.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">DOMINATI Noa 5<sup>&egrave;me</sup> au 200 4 nages en 2&rsquo;57&rsquo;&rsquo;97,3<sup>&egrave;me</sup> du 50 nage libre en 32&rsquo;&rsquo;41,2<sup>&egrave;me</sup> du 100 m&egrave;tres papillon en 1&rsquo;24&rsquo;&rsquo;52.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">HENRAS-MAROUF 4<sup>&egrave;me</sup> du 50 nage libre en 32&rsquo;&rsquo;48, 4<sup>&egrave;me</sup> du 100 brasse en 1&rsquo;31&rsquo;&rsquo;38.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">MARTINS M&eacute;lanie 1<sup>&egrave;re</sup> en 36&rsquo;&rsquo;58, 2<sup>&egrave;me</sup> du 100 dos en 1&rsquo;23&rsquo;&rsquo;14.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">RODIER Elena 2<sup>&egrave;me</sup> 39&rsquo;&rsquo;07, 4<sup>&egrave;me</sup> en 1&rsquo;25&rsquo;&rsquo;83.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">TARDIEU Auriane 5<sup>&egrave;me</sup> du 50 papillon et 100 papillon en 39&rsquo;&rsquo;16 et 1&rsquo;33&rsquo;&rsquo;00.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Chez les gar&ccedil;ons&nbsp;:</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">BELGHOMARI Abdelmalek 1<sup>er</sup> au 200 4 nages en 2&rsquo;44&rsquo;&rsquo;66, 2<sup>&egrave;me</sup> du 50 dos en 35&rsquo;&rsquo;58, 1<sup>er</sup> au 100 nage libre en 1&rsquo;06&rsquo;&rsquo;03.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">SERBOUH Elias 4<sup>&egrave;me</sup> du 200 4 nages 2&rsquo;50&rsquo;&rsquo;62, 1<sup>er</sup> au 50 et 100 dos en 34&rsquo;&rsquo;76,1&rsquo;15&rsquo;&rsquo;77.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">POVEDA Thibaud 5<sup>&egrave;me</sup> du 200 4 nages en 2&rsquo;53&rsquo;&rsquo;84, 2<sup>&egrave;me</sup> du 50 papillon en 36&rsquo;&rsquo;00,et 2<sup>&egrave;me</sup> du 100 nage libre en 1&rsquo;08&rsquo;&rsquo;57.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">FOUQUET Damien 4<sup>&egrave;me</sup> du 100 dos en 1&rsquo;28&rsquo;&rsquo;13.</span></span><o:p></o:p></p><p class=\"MsoNormal\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">PON Marius 6<sup>&egrave;me</sup> du 50 papillon en 39&rsquo;&rsquo;77</span></span><o:p></o:p></p><p class=\"MsoNormal\" style=\"text-align: left;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><strong><span style=\"font-size: medium;\"><span style=\"line-height: 115%;\">BRAVO A TOUTES ET A TOUS&nbsp;!!!&nbsp;</span></span></strong></span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/07/2012"));
			actu.setTitle("Annulation cours Toulouse-Lautrec Mercredi 12/12/2012	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l'attention des adh&eacute;rents de Toulouse-Lautrec, ouvrant &agrave; nouveau ses portes ce Lundi 10/12, merci de prendre note qu'en raison du d&eacute;roulement simultan&eacute; (aux m&ecirc;mes horaires) de la premi&egrave;re comp&eacute;tition interne, destin&eacute;e aux enfants de l'&eacute;cole de natation, ce mercredi 12 D&eacute;cembre &agrave; 19h sur Alex Jany ; <strong>les cours propos&eacute;s sur Toulouse-Lautrec&nbsp;</strong><strong>seront, en cons&eacute;quence, ajourn&eacute;s</strong>.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"> En comptant, de ce fait, sur votre participation massive &agrave; la comp&eacute;tition de mercredi, o&ugrave; une m&eacute;daille et un go&ucirc;ter attend chaque nageur !</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/10/2012"));
			actu.setTitle("Réouverture Toulouse-Lautrec	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Bonne nouvelle ! Apr&egrave;s 2 mois de fermeture pour divers gros travaux, la piscine a r&eacute;ouvert ses portes ce matin, en &eacute;tat de marche.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Nous reprenons donc, de ce fait, un fonctionnement normal pour les adh&eacute;rents de l'Ecole de Natation </span><strong><span style=\"font-family: 'Comic Sans MS';\">des Lundi et Mercredi, inscrits sur ce bassin, de 19h &agrave; 19h45 ou de 19h45 &agrave; 20h30.</span></strong></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">De m&ecirc;me pour les groupes comp&eacute;titions (Poussins, Benjamins, Minimes) qui basculent &agrave; nouveau sur ce site les Mardi et Vendredi selon. Merci de prendre note.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/10/2012"));
			actu.setTitle("Annulation séance Samedi 15/12/2012	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">A l'attention des enfants des groupes Avenirs, Licornes, Marsouins et Cachalots, veuillez noter que la s&eacute;ance de Samedi apr&egrave;s-midi sur L&eacute;o Lagrange (15h30 &agrave; 17h30) ne sera pas en mesure d'&ecirc;tre assur&eacute;e, en raison du d&eacute;roulement, tout le week-end, d'une comp&eacute;tition de natation organis&eacute;e par le Comit&eacute; R&eacute;gional.&nbsp;<br type=\"_moz\" /></span></span></p><p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Prochain cours assur&eacute;, le Samedi 22/12/2012</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">. Merci de votre compr&eacute;hension.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/11/2012"));
			actu.setTitle("Entraînements vacances de Noël	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span class=\"news_text\" style=\"font-family: Tahoma, Arial; text-align: justify;\"><span style=\"font-family: 'Comic Sans MS'; font-size: small; line-height: 19px; text-align: left;\">A l'attention des groupes comp&eacute;titions, Poussins, Benjamins, Minimes, CJS D&eacute;partementaux, section sportive R. Naves, Elite et Masters, veuillez trouver, ci-joint, les cr&eacute;neaux propos&eacute;es pendant les prochaines vacances de No&euml;l. Bonne f&ecirc;tes de fin d'ann&eacute;e ! (sans exc&egrave;s).</span></span></p><div><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><br /></span></span></div><p>&nbsp;</p>“	<p>&nbsp;<span style=\"font-family: 'Comic Sans MS'; font-size: small; background-color: rgb(255, 255, 153); line-height: 19px;\">1) Voici les s&eacute;ances que nous proposons aux enfants&nbsp;</span><strong style=\"font-family: 'Comic Sans MS'; font-size: small; background-color: rgb(255, 255, 153); line-height: 19px;\">du groupe Poussins</strong><span style=\"font-family: 'Comic Sans MS'; font-size: small; background-color: rgb(255, 255, 153); line-height: 19px;\">&nbsp;</span><strong style=\"font-family: 'Comic Sans MS'; font-size: small; background-color: rgb(255, 255, 153); line-height: 19px;\">encadr&eacute;s par Clo&eacute;</strong><span style=\"font-family: 'Comic Sans MS'; font-size: small; background-color: rgb(255, 255, 153); line-height: 19px;\">, lors des prochaines vacances :</span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp; &nbsp; Repos programm&eacute; les 2 semaines (r&eacute;g&eacute;naration des organismes).</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">2) Voici&nbsp;les s&eacute;ances que nous proposons aux enfants&nbsp;<strong>du groupe Benjamins encadr&eacute;s par S&eacute;bastien</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"> <b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;: repos.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;</span></span><b style=\"text-indent: 35.4pt;\"><u><span style=\"font-size: 10pt;\">2<sup>&egrave;me</sup>&nbsp;semaine</span></u></b><span style=\"text-indent: 35.4pt; font-size: 10pt; font-family: Arial, sans-serif;\">&nbsp;: </span><span style=\"font-family: 'Comic Sans MS';\"><span style=\"font-size: small;\"><span style=\"text-indent: 35.4pt;\">les Mercredi 02/01 et Jeudi 03/01/2012&nbsp;: de 19h &agrave; 20h45 sur Alex Jany.</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: 10pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style=\"font-family: 'Comic Sans MS';\"><span style=\"font-size: 10pt;\">&nbsp;</span><span style=\"font-size: small;\">le Vendredi 04/01&nbsp;: de 19h &agrave; 21</span></span><span style=\"font-size: small; font-family: 'Comic Sans MS';\">h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></font></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">le Samedi 05/01 : de 10h &agrave; 12h sur Alex Jany.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">3) &nbsp;Voici&nbsp;les s&eacute;ances que nous proposons aux enfants&nbsp;<strong>du groupe Minimes encadr&eacute;s par J&eacute;r&eacute;my</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>-</b>&nbsp;</span></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;: repos.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">-&nbsp;<span style=\"font-family: 'Comic Sans MS';\"><span style=\"font-size: small;\"><b style=\"text-indent: 35.4pt;\"><u>2<sup>&egrave;me</sup>&nbsp;semaine</u></b><span style=\"text-indent: 35.4pt;\">&nbsp;</span></span></span><span style=\"text-indent: 35.4pt;\">:&nbsp;</span><span style=\"text-indent: 35.4pt; font-size: small; font-family: 'Comic Sans MS';\">les Mercredi 02/01 et Jeudi 03/01/2012&nbsp;: de 19h &agrave; 20h45 sur Alex Jany.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: 10pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style=\"font-family: 'Comic Sans MS';\"><span style=\"font-size: small;\">le Vendredi 04/01&nbsp;: de 19h &agrave; 21</span></span><span style=\"font-size: small; font-family: 'Comic Sans MS';\">h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></font></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">le Samedi 05/01 : de 10h &agrave; 12h sur Alex Jany.</span></span><span style=\"text-indent: 35.4pt;\">&nbsp;</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">4)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs&nbsp;<strong>d</strong><strong>u groupe CJS D&eacute;partementaux encadr&eacute;s par St&eacute;phane</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-&nbsp;<b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;: repos.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: small; font-family: 'Comic Sans MS';\">-&nbsp;</span><b style=\"font-size: small; font-family: 'Comic Sans MS';\"><u>2<sup>&egrave;me</sup>&nbsp;semaine</u></b><span style=\"font-size: small; font-family: 'Comic Sans MS';\">&nbsp;:&nbsp;</span><span style=\"text-indent: 35.4pt; font-size: small; font-family: 'Comic Sans MS';\">les Mercredi 02/01 et Jeudi 03/01/2012&nbsp;: de 19h &agrave; 20h45 sur Alex Jany.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: 10pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span><span style=\"font-family: 'Comic Sans MS';\"><span style=\"font-size: small;\">le Vendredi 04/01&nbsp;: de 19h &agrave; 21</span></span><span style=\"font-size: small; font-family: 'Comic Sans MS';\">h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">le Samedi 05/01 : de 10h &agrave; 12h sur Alex Jany.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial; text-indent: 35.4pt;\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">5)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs&nbsp;<strong>du groupe Raymond Naves encadr&eacute;s par Cyril</strong>, lors des prochaines vacances :&nbsp;</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;<b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;: du Mercredi 26/12 au Vendredi 28/12 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><b>-&nbsp;<u>2<sup>&egrave;me</sup>&nbsp;semaine</u></b>&nbsp;: du Mercredi 02/01 au Vendredi 04/01 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Samedi 05/01 : de 10h &agrave; 12h sur Alex Jany.</span></span></p> <p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">6)&nbsp;Voici les s&eacute;ances que nous proposons&nbsp;<strong>aux nageurs du groupe Elite encadr&eacute;s par Philippe et Lo&iuml;c</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;<b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;:&nbsp;</span></span><span style=\"font-family: 'Comic Sans MS'; font-size: small;\">du Mercredi 26/12 au Vendredi 28/12 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;<b><u>2<sup>&egrave;me</sup>&nbsp;semaine</u></b>&nbsp;:&nbsp;</span></span><span style=\"font-family: 'Comic Sans MS'; font-size: small;\">du Mercredi 02/01 au Vendredi 04/01 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Samedi 05/01 : de 10h &agrave; 12h sur Alex Jany.</span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"background-color: rgb(255, 255, 153);\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">7)&nbsp;Voici les s&eacute;ances que nous proposons aux nageurs&nbsp;<strong>du groupe Masters encadr&eacute;s par R&eacute;mi</strong>, lors des prochaines vacances :</span></span></span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;<b><u>1<sup>&egrave;re</sup>&nbsp;semaine</u></b>&nbsp;:&nbsp;</span></span><span style=\"font-family: 'Comic Sans MS'; font-size: small;\">du Mercredi 26/12 au Vendredi 28/12 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></font><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">-</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;<b><u>2<sup>&egrave;me</sup>&nbsp;semaine</u></b>&nbsp;:&nbsp;</span></span><span style=\"font-family: 'Comic Sans MS'; font-size: small;\">du Mercredi 02/01 au Vendredi 04/01 : de 12h &agrave; 14h sur L&eacute;o Lagrange.</span></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><font size=\"2\" face=\"Arial\"><span style=\"font-size: 10pt;\"><o:p></o:p></span></font></p><p class=\"MsoNormal\" style=\"line-height: 19px; margin: 0px 0px 10px; font-family: Tahoma, Arial;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><br type=\"_moz\" /></span></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Merci de prendre note et de r&eacute;pondre pr&eacute;sent,&nbsp;</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/14/2012"));
			actu.setTitle("Résultats 1ère compétition interne	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Veuillez trouver, ci-joint, les r&eacute;sultats de la manifestation qui s'est d&eacute;roul&eacute;e ce Mercredi 12 D&eacute;cembre 2012, &agrave; Alex Jany, et o&ugrave;, pour l'occasion <strong>190 nageurs (record pulv&eacute;ris&eacute; !!!) </strong>ont r&eacute;pondu pr&eacute;sents, d'une tranche d'&acirc;ge de 5 ans, pour la plus jeune, &agrave; 20 ans pour la plus &acirc;g&eacute;e.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"> F&eacute;licitations &agrave; toutes et &agrave; tous pour ces brillantes performances qui ne font que mat&eacute;rialiser les progr&eacute;s effectu&eacute;s lors ce premier trimestre de pratique. Bonnes f&ecirc;tes de fin d'ann&eacute;e !</span></span></p>“	<p>&nbsp;</p><p><a href=\"/entrepot/transfert/file/2012-2013/R%C3%A9sultats%20comp%C3%A9titions/1%C3%A8re_comp%C3%A9tition_interne_12_12_2012.pdf\"><span style=\"font-size: large;\"><span style=\"font-family: 'Comic Sans MS';\">R&eacute;sultats 1&egrave;re comp&eacute;tition interne du Mercredi 12 D&eacute;cembre 2012</span></span></a></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/14/2012"));
			actu.setTitle("Championnat Régional 25m Castres	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Ci-joint, un bref compte-rendu de la comp&eacute;tition qui s'est d&eacute;roul&eacute;e le week-end pass&eacute;e &agrave; Castres, o&ugrave; <strong>48 nageurs</strong> ont brillamment d&eacute;fendu nos couleurs (plus grand nombre d'engag&eacute;s de la r&eacute;gion...) et o&ugrave; 13 d'entre eux se sont qualifi&eacute;s pour les interr&eacute;gions 25m de Bayonne dans 10 jours.</span></span></p>“	<p>&nbsp;</p><p><a href=\"/entrepot/transfert/file/2012-2013/MEETING_REGIONAL_CASTRES_2012.pdf\"><span style=\"font-size: large;\"><span style=\"font-family: 'Comic Sans MS';\">le compte-rendu</span></span></a></p><p>&nbsp;</p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("12/18/2012"));
			actu.setTitle("Vacances de Noël	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">La fin d'ann&eacute;e se rapprochant &agrave; grands pas, petites pr&eacute;cisions pour la fin des cours qui se profile cette fin de semaine.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Les cours de l'Ecole de Natation sont bien assur&eacute;s sur tous nos bassins <strong>jusqu'au Vendredi 21 D&eacute;cembre 2012 inclus</strong>, de m&ecirc;me pour <strong>les cours du Samedi</strong> apr&egrave;s-midi &agrave; L&eacute;o Lagrange (15h30 &agrave; 17h30) <strong>qui seront bien assur&eacute;s ce 22/12</strong>. Ensuite, interruption de nos activit&eacute;s pendant l'int&eacute;gralit&eacute; des vacances scolaires, <strong>avec une reprise programm&eacute;e de nos interventions &agrave; compter du Lundi 07 Janvier 2013, 19h.</strong> </span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">D'ici l&agrave;, reposez-vous bien, bonnes f&ecirc;tes de fin d'ann&eacute;e et &agrave; l'ann&eacute;e prochaine !!!</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/02/2013"));
			actu.setTitle("Voeux 2013	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Toute l'&eacute;quipe dirigeante, les entra&icirc;neurs, &eacute;ducateurs, officiels, b&eacute;n&eacute;voles de l'ASPTT Toulouse Natation se rejoignent pour vous souhaiter une bonne et heureuse ann&eacute;e 2013, en souhaitant que vous vous accomplissiez et &eacute;panoussiez dans votre pratique favorite. A bient&ocirc;t au bord des bassins... &nbsp;</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/07/2013"));
			actu.setTitle("Calendrier club 2013	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Tous les membres de l&rsquo;ASPTT Toulouse Natation se r&eacute;unissent pour vous souhaiter une excellente ann&eacute;e 2013&nbsp;!</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">En cette nouvelle ann&eacute;e, nous allons distribuer &agrave; tous nos adh&eacute;rents de l'&eacute;cole de natation notre nouveau calendrier avec pour chaque groupe sa photo personnalis&eacute;e. </span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Nous vendons ce calendrier pour <strong>5 euros pi&egrave;ces seulement</strong>. Si vous ne souhaitez pas l'acheter, merci de nous le retourner lors du cours suivant.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Une permanence sera assur&eacute;e par un membre du club tous les soirs, et sur chaque bassin,<strong> du Lundi 07 au Jeudi 31 janvier 2013</strong> afin de r&eacute;cup&eacute;rer les commandes (et les paiements).</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Si ce calendrier vous int&eacute;resse, dans la perspective d'offrir, ou de garder un bon souvenir de cette saison 2012-2013 au sein de votre club favori, vous pouvez nous faire parvenir le paiement directement sur chaque bassin, en vous adressant &agrave; la personne de permanence, de pr&eacute;f&eacute;rence par ch&egrave;que &agrave; l&rsquo;ordre suivant&nbsp;: <strong>ASPTT Grand Toulouse</strong>, ou &agrave; d&eacute;faut en esp&egrave;ces.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Nous vous remercions par avance de la participation et de la confiance que vous nous t&eacute;moignerez. Le bureau de la section.</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/03/2013"));
			actu.setTitle("Compte rendu Bayonne	");
			actu.setSummary("");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify; text-indent: 35.4pt;\">Du Vendredi 21 au Dimanche 23 d&eacute;cembre se sont d&eacute;roul&eacute;s les championnats interr&eacute;gionaux 25m &agrave; Bayonne.</span></span></span></p><p><span style=\"font-family: 'Comic Sans MS'; font-size: small; text-align: justify;\">En grand collectif, l&rsquo;ASPTT TOULOUSE avaient fait le d&eacute;placement avec <strong>38 nageurs</strong> ayant pour objectif principal une qualification aux Championnats de France de Nationale 2 hiver, bassin de 50m du mois de Mars.</span></p><p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">32 d&rsquo;entre eux y sont parvenus, pla&ccedil;ant l&rsquo;Asptt 1<sup>er</sup> club du Sud-Ouest en terme de nombre de qualifi&eacute;s aux Nationale 2</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">. Bravo les nageurs&nbsp;!!!</span></span></p><p>&nbsp;</p>“	<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><span style=\"text-align: justify;\">Dans un contexte difficile et concurrentiel, les nageurs ont port&eacute; haut les couleurs de notre club. A savoir&nbsp;:</span>  </span></span></p><p class=\"MsoListParagraph\" style=\"text-align: justify; text-indent: -18pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><!--[if !supportLists]-->&middot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!--[endif]--><strong><u>23 nageurs finalistes :</u></strong></span></span><u><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></u></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Nicolas ACHOTEGUI, Elodie BARBOTEAU, Elisa BETEILLE, Calvin BOUCHUT, Thibaut DANHO, Camille DAZET, Cl&eacute;ment DELBES, Alain DEBEUCKELAERE, Lucie DELBOS, Louise DEVAUD, Lisa FAUCONNIER, Laura FERON, Charlotte FOUQUET, Duncan GEORGES, Quentin GRIVEL, Marianne HUDY, Enzo MARTIN, Damien MARTINS, Kyumars MOVAHED, Julie NIVOIX, Ma&eacute;va RAMONJIARIVONY, Axelle ROS, Laura THIBAULT et Vivien VILLARD.</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">&nbsp;</span></span><u><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Le</span></span><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">s 2 relais 4 x 200 Nage Libre m&eacute;daill&eacute;s&nbsp;:</span></span></strong></u><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Le Bronze pour les Dames avec Cl&eacute;mence LEHOUX, Alice KUNTZ, Lucy DOHERTY et Marianne HUDY.</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">L&rsquo;Argent pour les Messieurs avec Thibaut LABANDIBAR, Duncan GEORGES, Vivien VILLARD et Cl&eacute;ment DELBES.</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoListParagraph\" style=\"text-align: justify; text-indent: -18pt;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><!--[if !supportLists]-->&middot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><u><strong><span style=\"font-family: 'Comic Sans MS';\">Les nageurs du club m&eacute;daill&eacute;s lors de cette comp&eacute;tition&nbsp;:</span></strong></u></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><u>Camille DAZET</u> 2<sup>&egrave;me</sup> sur 200 4 nages</span></span>&nbsp;<span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Dames</span></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><u>Alain DEBEUCKELAERE</u> <b>1<sup>er</sup> sur 50, 100 et 200 Dos</b></span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><u>Laura FERON</u> <b>1<sup>&egrave;re</sup> sur 100 Nage Libre et 50 Papillon</b>, 2<sup>&egrave;me</sup> sur 100 Papillon</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"><u>Duncan GEORGES</u> 2<sup>&egrave;me</sup> sur 200 Brasse</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small; font-family: 'Comic Sans MS';\"><u>Laura THIBAULT</u> <b>1<sup>&egrave;re</sup> sur 200 4 nages</b>, 2<sup>&egrave;me</sup> sur 50 Papillon et 3<sup>&egrave;me</sup> sur 50 Nage Libre</span><font face=\"Book Antiqua, serif\"><span style=\"font-size: 19px;\">.</span></font></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Cette comp&eacute;tition marquait la fin de la saison en petit bassin. Rendez-vous au premier trimestre 2013 pour les comp&eacute;titions en grand bassin.</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p><p class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Mais d&rsquo;abord place &agrave; un repos bien m&eacute;rit&eacute; lors des f&ecirc;tes de fin d&rsquo;ann&eacute;e.</span></span><span style=\"font-size:14.0pt;font-family:&quot;Book Antiqua&quot;,&quot;serif&quot;\"><o:p></o:p></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/16/2013"));
			actu.setTitle("Championnat Régional des Maîtres, Alex Jany	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Ce Dimanche 13 Janvier 2013, l'ASPTT Toulouse Natation avait le plaisir d'organiser, conjointement, avec le Comit&eacute; R&eacute;gional Midi-Pyr&eacute;n&eacute;es, la 1&egrave;re des 3 &eacute;tapes du Championnat R&eacute;gional des Ma&icirc;tres de cette saison 2012-2013. Pour l'occasion, ce ne sont pas moins de 167 nageurs de toute la r&eacute;gion (40 Dames et 127 Messieurs) qui ont r&eacute;pondu &agrave; l'appel, une petite semaine apr&egrave;s la tr&egrave;ve des f&ecirc;tes de fin d'ann&eacute;e.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\"> Le club, avait su mobilis&eacute; 17 nageurs Ma&icirc;tres (6 Filles et 11 Gar&ccedil;ons). Une reprise en fanfare n&eacute;anmoins puisque <strong>S&eacute;bastien Borderas a battu le record de France, cat&eacute;gorie C1 (25-29 ans) du 1500m NL</strong> en 16.32.80, am&eacute;lioration de pr&egrave;s de 16 secondes (!) et Anne V&eacute;lez, du SO Millau, a am&eacute;lior&eacute;, elle, un record d'Europe, toujours sur cette distance du 1500m NL Dames, en cat&eacute;gorie C2 (30-34 ans) en 18.05.34.</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Une belle entr&eacute;e en mati&egrave;re pour cette ann&eacute;e 2013, de vives f&eacute;licitations &agrave; S&eacute;bastien, qui bat, ici, son premier record de France. Bravo &agrave; lui !</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/24/2013"));
			actu.setTitle("Interclubs Masters Nationaux	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Fort de son statut de meilleur club r&eacute;gional</span></span></strong><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">, votre club pr&eacute;f&eacute;r&eacute; envoie sa &quot;Dream Team&quot; au Puy en Velay, ce week-end, &agrave; l'occasion des Interclubs Nationaux des Ma&icirc;tres qui regrouperont 70 &eacute;quipes de tout le territoire. <strong>Qualifi&eacute;s 6&egrave;me apr&egrave;s les &eacute;liminatoires de D&eacute;cembre</strong>, pourquoi ne pas tenter d'accrocher un top 5, challenge qui sera difficile &agrave; relever, mais pas impossible ! Dans tous les cas, mission leur est donn&eacute;e de faire aussi bien que l'an pass&eacute;, o&ugrave; notre &eacute;quipe 1 avait termin&eacute; &agrave; une magnifique 7&egrave;me place...</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Bonne chance &agrave; tous, vous pourrez suivre leurs exploits sur le live FFN (</span></span><a href=\"http://www.liveffn.com/cgi-bin/index.php?competition=5941\" target=\"_blank\"><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">http://www.liveffn.com/cgi-bin/index.php?competition=5941</span></span></a><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">).</span></span></p><p class=\"MsoNormal\" style=\"background-position: initial initial; background-repeat: initial initial;\"><o:p></o:p></p><p class=\"MsoNormal\" style=\"background-position: initial initial; background-repeat: initial initial;\"><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;;color:#1F497D\">&nbsp;</span><o:p></o:p></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/25/2013"));
			actu.setTitle("Annulation séance Samedi 02/02/2013	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">La mairie de Toulouse vient de nous avertir que la piscine de L&eacute;o Lagrange ne sera pas disponible le Samedi 02/02/2013, en raison du d&eacute;roulement d'une comp&eacute;tition sur l'&eacute;tablissement toute la journ&eacute;e. <strong>Par cons&eacute;quent, les cours de l'Ecole de Natation des groupes Licornes, Avenirs, Marsouins et Cachalots ne pourront &ecirc;tre assur&eacute;s ce jour</strong>. Merci de prendre note afin de ne pas vous d&eacute;placer en vain...</span></span></p>"));
			dao.save(actu);
			actu = new ActuEntity();
			actu.setCreationDate(format2.parse("01/28/2013"));
			actu.setTitle("Classement Interclubs des Maitres	");
			actu.setSummary("	");
			actu.setContent(new Text(
					"<p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Comme annonc&eacute; en fin de semaine derni&egrave;re, ce week-end se tenait au Puy en Velay, cet &eacute;v&egrave;nement majeur pour la cat&eacute;gorie des Ma&icirc;tres (nageurs de 25 ans &agrave; 99 ans).</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Fi&egrave;re de ses performances &agrave; l'issue des phases qualificatives de d&eacute;cembre, l'&eacute;quipe 1 de l'ASPTT TOULOUSE Natation r&eacute;ussit l'exploit de finir pour la <strong>3&egrave;me ann&eacute;e cons&eacute;cutive dans le top 10 (9&egrave;me exactement) </strong>de la comp&eacute;tition, sur 70 clubs repr&eacute;sent&eacute;s, mais surtout 2&egrave;me club hors Ile de France, derri&egrave;re Marseille et </span><strong><span style=\"font-family: 'Comic Sans MS';\">premier club du Sud-Ouest,qui plus est de Midi-Pyr&eacute;n&eacute;es.&nbsp;</span></strong></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Bravo et f&eacute;licitations &agrave; Camille, Laurence, Lucie, Typhanie, Bruno, Cyril, Jean-Luc, Micha&euml;l, Philippe et S&eacute;bastien qui ont vaillamment d&eacute;fendu nos couleurs lors de cette manifestation, qui ont donc su braver les difficult&eacute;s climatiques et sportives pour donner le meilleur d'eux-m&ecirc;me !</span></span></p><p><span style=\"font-size: small;\"><span style=\"font-family: 'Comic Sans MS';\">Le classement officiel (<a href=\"/entrepot/transfert/file/2012-2013/R%C3%A9sultats%20comp%C3%A9titions/Classement%20Interclubs%20Masters%20Le%20Puy%20en%20Velay.pdf\">ici</a>).</span></span></p>"));
			dao.save(actu);
			System.out.println("Fin actu");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void createCompetition() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		CompetitionDao dao = new CompetitionDao();
		CompetitionEntity competition = new CompetitionEntity();
		try {
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 2");
			competition.setBegin(format.parse("21/10/2012"));
			competition.setEnd(format.parse("21/10/2012"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 3");
			competition.setBegin(format.parse("28/10/2012"));
			competition.setEnd(format.parse("28/10/2012"));
			competition.setPlace("Balma");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats Interclubs Benjamins");
			competition.setBegin(format.parse("28/10/2012"));
			competition.setEnd(format.parse("28/10/2012"));
			competition.setPlace("Balma");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Interclubs Départementaux");
			competition.setBegin(format.parse("11/11/2012"));
			competition.setEnd(format.parse("11/11/2012"));
			competition.setPlace("Colomiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Interclubs Régionaux ");
			competition.setBegin(format.parse("10/11/2012"));
			competition.setEnd(format.parse("11/11/2012"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats France 25m");
			competition.setBegin(format.parse("15/11/2012"));
			competition.setEnd(format.parse("18/11/2012"));
			competition.setPlace("Angers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("1er Championnat Départemental Maîtres");
			competition.setBegin(format.parse("17/11/2012"));
			competition.setEnd(format.parse("17/11/2012"));
			competition.setPlace("Toulouse (Nakache)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 3");
			competition.setBegin(format.parse("17/11/2012"));
			competition.setEnd(format.parse("17/11/2012"));
			competition.setPlace("Nakache");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("1ère Trophées Paule Lacoste");
			competition.setBegin(format.parse("18/11/2012"));
			competition.setEnd(format.parse("18/11/2012"));
			competition.setPlace("Toulouse (Nakache)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 2");
			competition.setBegin(format.parse("18/11/2012"));
			competition.setEnd(format.parse("18/11/2012"));
			competition.setPlace("Nakache");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnat Départemental 25m");
			competition.setBegin(format.parse("24/11/2012"));
			competition.setEnd(format.parse("25/11/2012"));
			competition.setPlace("Portet / Garonne");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("1ères Médailles du TOAC");
			competition.setBegin(format.parse("01/12/2012"));
			competition.setEnd(format.parse("01/12/2012"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting Régional Interclubs");
			competition.setBegin(format.parse("02/12/2012"));
			competition.setEnd(format.parse("02/12/2012"));
			competition.setPlace("Colomiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats Régionaux 25m");
			competition.setBegin(format.parse("08/12/2012"));
			competition.setEnd(format.parse("09/12/2012"));
			competition.setPlace("Castres");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("1er Pass'compétition");
			competition.setBegin(format.parse("04/12/2012"));
			competition.setEnd(format.parse("04/12/2012"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("17ème Meeting Alex Jany");
			competition.setBegin(format.parse("15/12/2012"));
			competition.setEnd(format.parse("16/12/2012"));
			competition.setPlace("Léo Lagrange");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpts de France N2 25m");
			competition.setBegin(format.parse("21/12/2012"));
			competition.setEnd(format.parse("23/12/2012"));
			competition.setPlace("Bayonne");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 2");
			competition.setBegin(format.parse("13/01/2013"));
			competition.setEnd(format.parse("13/01/2013"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnat Régional Michel Ducros étape 1");
			competition.setBegin(format.parse("13/01/2013"));
			competition.setEnd(format.parse("13/01/2013"));
			competition.setPlace("Toulouse (Alex Jany)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("2ème Trophées Paule Lacoste");
			competition.setBegin(format.parse("20/01/2013"));
			competition.setEnd(format.parse("20/01/2013"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 3");
			competition.setBegin(format.parse("26/01/2013"));
			competition.setEnd(format.parse("26/01/2013"));
			competition.setPlace("Nakache");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("2ème Championnat Départemental Maîtres");
			competition.setBegin(format.parse("26/01/2013"));
			competition.setEnd(format.parse("26/01/2013"));
			competition.setPlace("Toulouse (Nakache)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Interclubs Nationaux des Maîtres");
			competition.setBegin(format.parse("26/01/2013"));
			competition.setEnd(format.parse("27/01/2013"));
			competition.setPlace("Le Puy en Velay");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Etape Meeting Speedo");
			competition.setBegin(format.parse("27/01/2013"));
			competition.setEnd(format.parse("27/01/2013"));
			competition.setPlace("Toulouse (Nakache)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Interclubs Régionaux Benjamins / Minimes");
			competition.setBegin(format.parse("03/02/2013"));
			competition.setEnd(format.parse("03/02/2013"));
			competition.setPlace("Pamiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition
					.setTitle("Interclubs Départementaux Benjamins / Minimes");
			competition.setBegin(format.parse("03/02/2013"));
			competition.setEnd(format.parse("03/02/2013"));
			competition.setPlace("Balma");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnat Régional Michel Ducros étape 2");
			competition.setBegin(format.parse("10/02/2013"));
			competition.setEnd(format.parse("10/02/2013"));
			competition.setPlace("Auch");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting international d'hiver FFN");
			competition.setBegin(format.parse("15/02/2013"));
			competition.setEnd(format.parse("16/02/2013"));
			competition.setPlace("Nancy");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("2èmes Médailles du TOAC");
			competition.setBegin(format.parse("16/02/2013"));
			competition.setEnd(format.parse("16/02/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting Départemental 1");
			competition.setBegin(format.parse("17/02/2013"));
			competition.setEnd(format.parse("17/02/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("5ème Grand Prix de la ville d'Agen");
			competition.setBegin(format.parse("23/02/2013"));
			competition.setEnd(format.parse("24/02/2013"));
			competition.setPlace("Agen");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("3ème Championnat Départemental Maîtres");
			competition.setBegin(format.parse("23/02/2013"));
			competition.setEnd(format.parse("23/02/2013"));
			competition.setPlace("Colomiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpts de France Hiver 25m Maîtres");
			competition.setBegin(format.parse("07/03/2013"));
			competition.setEnd(format.parse("10/03/2013"));
			competition.setPlace("Chartres");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting Régional 50m");
			competition.setBegin(format.parse("08/03/2013"));
			competition.setEnd(format.parse("10/03/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 2");
			competition.setBegin(format.parse("16/03/2013"));
			competition.setEnd(format.parse("16/03/2013"));
			competition.setPlace("Toulouse (Jean Boiteux)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("1er Natathlon Départemental");
			competition.setBegin(format.parse("17/03/2013"));
			competition.setEnd(format.parse("17/03/2013"));
			competition.setPlace("Toulouse (Alex Jany)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 3");
			competition.setBegin(format.parse("17/03/2013"));
			competition.setEnd(format.parse("17/03/2013"));
			competition.setPlace("Toulouse (Alex Jany)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpts de France N2 Hiver 50m");
			competition.setBegin(format.parse("22/03/2013"));
			competition.setEnd(format.parse("24/03/2013"));
			competition.setPlace("Angoulème ");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("3ème Trophées Paule Lacoste");
			competition.setBegin(format.parse("24/03/2013"));
			competition.setEnd(format.parse("24/03/2013"));
			competition.setPlace("Muret");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats de France Jeunes");
			competition.setBegin(format.parse("02/04/2013"));
			competition.setEnd(format.parse("06/04/2013"));
			competition.setPlace("?");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting Départemental 2");
			competition.setBegin(format.parse("06/04/2013"));
			competition.setEnd(format.parse("07/04/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats de France Elite");
			competition.setBegin(format.parse("09/04/2013"));
			competition.setEnd(format.parse("14/04/2013"));
			competition.setPlace("Rennes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Team Cup Départementale");
			competition.setBegin(format.parse("13/04/2013"));
			competition.setEnd(format.parse("13/04/2013"));
			competition.setPlace(" Colomiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("2ème Natathlon Départemental");
			competition.setBegin(format.parse("14/04/2013"));
			competition.setEnd(format.parse("14/04/2013"));
			competition.setPlace("Saverdun");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("ENF 3");
			competition.setBegin(format.parse("14/04/2013"));
			competition.setEnd(format.parse("14/04/2013"));
			competition.setPlace("Saverdun");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("20ème Coupe des Régions Trophée Jean Pommat");
			competition.setBegin(format.parse("10/05/2013"));
			competition.setEnd(format.parse("11/05/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("23èmes Nautiques de Tarbes");
			competition.setBegin(format.parse("11/05/2013"));
			competition.setEnd(format.parse("12/05/2013"));
			competition.setPlace("Tarbes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Meeting National des Maîtres");
			competition.setBegin(format.parse("18/05/2013"));
			competition.setEnd(format.parse("18/05/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("3ème Natathlon Départemental");
			competition.setBegin(format.parse("19/05/2013"));
			competition.setEnd(format.parse("19/05/2013"));
			competition.setPlace("Toulouse (Alex Jany)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnat Régional 50m");
			competition.setBegin(format.parse("25/05/2013"));
			competition.setEnd(format.parse("26/05/2013"));
			competition.setPlace("Tarbes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finales TOAC");
			competition.setBegin(format.parse("01/06/2013"));
			competition.setEnd(format.parse("01/06/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnat Régional Eté des Maîtres");
			competition.setBegin(format.parse("02/06/2013"));
			competition.setEnd(format.parse("02/06/2013"));
			competition.setPlace("Tarbes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition
					.setTitle("Meeting Départemental + Finale Challenge Speedo");
			competition.setBegin(format.parse("02/06/2013"));
			competition.setEnd(format.parse("02/06/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finale Régionale Team Cup ");
			competition.setBegin(format.parse("08/06/2013"));
			competition.setEnd(format.parse("08/06/2013"));
			competition.setPlace("Montauban");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Open EDF");
			competition.setBegin(format.parse("14/06/2013"));
			competition.setEnd(format.parse("15/06/2013"));
			competition.setPlace("Paris");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finale des Trophées");
			competition.setBegin(format.parse("16/06/2013"));
			competition.setEnd(format.parse("16/06/2013"));
			competition.setPlace("Saint-Gaudens");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finale Régionale Natathlon");
			competition.setBegin(format.parse("16/06/2013"));
			competition.setEnd(format.parse("16/06/2013"));
			competition.setPlace("Saint-Affrique");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("9ème Trophée national Lucien-Zins");
			competition.setBegin(format.parse("22/06/2013"));
			competition.setEnd(format.parse("23/06/2013"));
			competition.setPlace("Tarbes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finale Interrégionale Natathlon");
			competition.setBegin(format.parse("22/06/2013"));
			competition.setEnd(format.parse("22/06/2013"));
			competition.setPlace("Agen ?");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpts de France Eté 50m Maîtres");
			competition.setBegin(format.parse("20/06/2013"));
			competition.setEnd(format.parse("23/06/2013"));
			competition.setPlace("Antibes");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpt Départemental Eté");
			competition.setBegin(format.parse("22/06/2013"));
			competition.setEnd(format.parse("23/06/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Finale Départementale Natathlon");
			competition.setBegin(format.parse("22/06/2013"));
			competition.setEnd(format.parse("23/06/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats de France N2 Eté 50m");
			competition.setBegin(format.parse("28/06/2013"));
			competition.setEnd(format.parse("30/06/2013"));
			competition.setPlace("Toulouse (Léo Lagrange)");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Chpt de France Eté Open Maîtres Eau Libre");
			competition.setBegin(format.parse("06/07/13"));
			competition.setEnd(format.parse("06/07/13"));
			competition.setPlace("Torcy");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Coupe des Départements");
			competition.setBegin(format.parse("06/07/2013"));
			competition.setEnd(format.parse("07/07/2013"));
			competition.setPlace("Alex Jany ou Colomiers");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats Nationaux (16 ans et +)");
			competition.setBegin(format.parse("11/07/2013"));
			competition.setEnd(format.parse("14/07/2013"));
			competition.setPlace("Dunkerque");
			dao.save(competition);
			competition = new CompetitionEntity();
			competition.setSaison("2012-2013");
			competition.setTitle("Championnats de France Minimes");
			competition.setBegin(format.parse("18/07/2013"));
			competition.setEnd(format.parse("21/07/2013"));
			competition.setPlace("Béthune");

			dao.save(competition);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
