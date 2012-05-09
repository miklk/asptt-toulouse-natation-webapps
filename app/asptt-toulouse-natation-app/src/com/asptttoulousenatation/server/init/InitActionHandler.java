package com.asptttoulousenatation.server.init;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.lang.StringUtils;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDao;
import com.asptttoulousenatation.core.server.dao.competition.CompetitionDayDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionDayEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.server.dao.user.UserDao;
import com.asptttoulousenatation.core.server.dao.user.UserDataDao;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.CompetitionDayTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.server.util.Utils;
import com.asptttoulousenatation.shared.event.UiEvent;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;
import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
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
	private CompetitionDayTransformer competitionDayTransformer = new CompetitionDayTransformer();

	private static final Logger LOG = Logger.getLogger(InitActionHandler.class
			.getName());

	public InitResult execute(InitAction pArg0, ExecutionContext pArg1)
			throws DispatchException {
		LOG.info("Init action");

//		 createData();
		InitResult lInitResult = new InitResult();
//		lInitResult.setPhoto(getPicture());

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
			List<MenuEntity> lMenuEntities = menuDao
					.find(lCriteria, lMenuOrder);
			LOG.info("retrieving menu #" + lMenuEntities.size());
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(
					lMenuEntities.size());
			for (MenuEntity lMenuEntity : lMenuEntities) {
				if (lMenuEntity.getParent() == null) {
					// Retrieve sub menu
					List<MenuUi> lSubMenuUis = new ArrayList<MenuUi>(
							lMenuEntity.getSubMenu().size());
					for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
						MenuEntity lSubMenu = menuDao.get(lSubMenuId);
						lSubMenuUis.add(menuTransformer.toUi(lSubMenu));
					}
					MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
					lMenu.setSubMenus(lSubMenuUis);
					lMenuUis.put(lMenu.getTitle(), lMenu);
				}
			}
			AreaUi lArea = areaTransformer.toUi(lAreaEntity);
			lArea.setMenuSet(lMenuUis);
			lAreaUis.put(lArea.getTitle(), lArea);
		}
		lInitResult.setArea(lAreaUis);

		// Actu
		List<ActuEntity> lActuEntities = actuDao.getAll();
		LOG.info("retrieving actu #" + lActuEntities.size());
		ArrayList<ActuUi> lActu = new ArrayList<ActuUi>(
				actuTransformer.toUi(lActuEntities));
		lInitResult.setActu(lActu);

		// Events "calendar"
		lInitResult.setEvents(getEvents());
		return lInitResult;
	}

	private Map<Date, List<UiEvent>> getEvents() {
		final Map<Date, List<UiEvent>> lEvents = new HashMap<Date, List<UiEvent>>();
		List<CompetitionEntity> lEntities = competitionDao.getAll();
		for (CompetitionEntity lEntity : lEntities) {
			for (Long lDay : lEntity.getDays()) {
				CompetitionDayEntity lDayEntity = competitionDayDao.get(lDay);

				UiEvent lEvent = competitionDayTransformer.toUiEvent(
						lDayEntity, lEntity);
				List<UiEvent> lEventList = lEvents.get(lEvent.getEventDate());
				if (lEventList == null) {
					lEventList = new ArrayList<UiEvent>();
				}
				lEventList.add(lEvent);
				lEvents.put(lEvent.getEventDate(), lEventList);
			}
		}
		return lEvents;
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

		AreaEntity lAreaEntity = new AreaEntity(null, "Club",
				ProfileEnum.PUBLIC, (short) 1);
		AreaEntity lAreaEntity2 = lAreaDao.save(lAreaEntity);

		// Historique
		createMenu(MenuItems.VIDE.toString(), "Historique du club",
				lAreaEntity2.getId(), "contenu de l'historique du club",
				"contenu de l'historique", false, true, 1);
		createMenu(MenuItems.VIDE.toString(), "Organisation du club",
				lAreaEntity2.getId(), "contenu de l'organisation du club",
				"contenu de l'organisation du club", false, true, 2);
		createMenu(MenuItems.VIDE.toString(), "Lieux d'entrainements",
				lAreaEntity2.getId(), "contenu de Lieux d'entrainements",
				"contenu de Lieux d'entrainements", false, true, 3);
		createMenu(MenuItems.VIDE.toString(), "Officiels",
				lAreaEntity2.getId(), "contenu de officiels",
				"contenu de officiels", false, true, 4);
		createMenu(MenuItems.VIDE.toString(), "Vie du club",
				lAreaEntity2.getId(), "contenu de vie du club",
				"contenu de la vie du club", false, true, 5);

		lAreaEntity = new AreaEntity(null, "Groupes", ProfileEnum.PUBLIC,
				(short) 2);
		AreaEntity lAreaGroupes = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.VIDE.toString(), "Ecole de natation",
				lAreaGroupes.getId(), "contenu de Ecole de natation",
				"contenu de Ecole de natation", false, true, 1);
		createMenu(MenuItems.VIDE.toString(), "Loisirs adultes",
				lAreaGroupes.getId(), "contenu de Loisirs",
				"contenu de Loisirs", false, true, 2);
		createMenu(MenuItems.VIDE.toString(), "Aquagym", lAreaGroupes.getId(),
				"contenu de Aquagym", "contenu de Aquagym", false, true, 3);
		createMenu(MenuItems.VIDE.toString(), "Perfectionnement",
				lAreaGroupes.getId(), "contenu de Perfectionnement",
				"contenu de perfrectionnement", false, true, 4);
		createMenu(MenuItems.VIDE.toString(), "Compétitions",
				lAreaGroupes.getId(), "contenu de Compétitions",
				"contenu de Compétitions", false, true, 5);
		createMenu(MenuItems.VIDE.toString(), "Masters", lAreaGroupes.getId(),
				"contenu de Masters", "contenu de Masters", false, true, 6);
		createMenu(MenuItems.VIDE.toString(), "Eau libre",
				lAreaGroupes.getId(), "contenu de Eau libre",
				"contenu de Eau libre", false, true, 7);

		createMenu(MenuItems.VIDE.toString(), "Centre de formation",
				lAreaGroupes.getId(), "contenu de Centre de formation",
				"contenu de Centre de formation", false, true, 6);

		lAreaEntity = new AreaEntity(null, "Compétitions", ProfileEnum.PUBLIC,
				(short) 3);
		AreaEntity lAreaCompetition = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.VIDE.toString(), "Calendrier",
				lAreaCompetition.getId(), "contenu de Calendrier",
				"contenu de Calendrier", false, true, 1);
		createMenu(MenuItems.VIDE.toString(), "Résultats",
				lAreaCompetition.getId(), "contenu de Résultats",
				"contenu de Résultats", false, true, 2);
		createMenu(MenuItems.VIDE.toString(), "Records du club",
				lAreaCompetition.getId(), "contenu de Records du club",
				"contenu de Records du club", false, true, 3);
		createMenu(MenuItems.VIDE.toString(), "Ranking",
				lAreaCompetition.getId(), "contenu de Ranking",
				"contenu de Ranking", false, true, 4);

		lAreaEntity = new AreaEntity(null, "Boutique", ProfileEnum.PUBLIC,
				(short) 4);
		AreaEntity lAreaBoutique = lAreaDao.save(lAreaEntity);
		createMenu(
				MenuItems.VIDE.toString(),
				"Informations",
				lAreaBoutique.getId(),
				"contenu de Informations sur la boutique",
				"Partenariat avec la boutique arena de blagnac 10% de remise en caisse sur tout le magasin<br />et sur présentation d’un justificatif d’appartenance à l’ASPTT toulouse.",
				false, true, 1);

		lAreaEntity = new AreaEntity(null, "Inscription", ProfileEnum.PUBLIC,
				(short) 5);
		AreaEntity lAreaInscription = lAreaDao.save(lAreaEntity);
		createMenu(
				MenuItems.VIDE.toString(),
				"Informations",
				lAreaInscription.getId(),
				"contenu de Informations sur l'inscription",
				"Pour vous inscrire, veuillez télécharger le dossier ci-joint. Ce dossier doit être complété et renvoyé à l'association.",
				false, true, 1);
		createMenu(
				MenuItems.VIDE.toString(),
				"MotDePasse",
				lAreaInscription.getId(),
				"contenu de MotDePasse sur l'inscription",
				"L'espace privé n'est accessible qu'aux licenciés du club. Vous recevez votre mot de passe par e-mail une fois que votre inscription est validé par nos soins.<br />Si toute fois, vous avez oublié votre mot de passe, nous vous invitons à entrer votre adresse e-mail dans le champ suivant afin d'en recevoir un nouveau.",
				false, false, 2);

		lAreaEntity = new AreaEntity(null, "Contact", ProfileEnum.PUBLIC,
				(short) 6);
		AreaEntity lAreaContact = lAreaDao.save(lAreaEntity);
		createMenu(
				MenuItems.VIDE.toString(),
				"Contact",
				lAreaContact.getId(),
				"Contacter le secrétariat",
				"ASPTT Toulouse Natation 4 rue des Sept Troubadours 2ème étage - Bureau 215  31000 Toulouse Tel : 05.61.62.68.45 Fax : 05.61.14.86.09",
				false, false, 1);

		// Admin
		lAreaEntity = new AreaEntity(null, "Actualités", ProfileEnum.ADMIN,
				false, (short) 1);
		AreaEntity lAreaAdmActu = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.NEWS_PUBLICATION.toString(), "Publier",
				lAreaAdmActu.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 1);
		createMenu(MenuItems.NEWS_EDITION.toString(), "Modification",
				lAreaAdmActu.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2);

		lAreaEntity = new AreaEntity(null, "Utilisateur", ProfileEnum.ADMIN,
				false, (short) 5);
		AreaEntity lAreaAdmUser = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.USER_CREATION.toString(), "Création",
				lAreaAdmUser.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 1);
		createMenu(MenuItems.USER_EDITION.toString(), "Modification",
				lAreaAdmUser.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2);

		// Organisation du club
		lAreaEntity = new AreaEntity(null, "Organisation du club",
				ProfileEnum.ADMIN, false, (short) 3);
		AreaEntity lAreaClub = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.CLUB_GROUP_EDITION.toString(), "Groupes",
				lAreaClub.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 1);
		createMenu(MenuItems.CLUB_SLOT_EDITION.toString(), "Créneaux",
				lAreaClub.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 2);

		// Officiels
		lAreaEntity = new AreaEntity(null, "Officier", ProfileEnum.OFFICIEL,
				false, (short) 6);
		AreaEntity lAreaOfficier = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.OFFICIEL_VIEW.toString(),
				"Consulter le calendier", lAreaOfficier.getId(),
				StringUtils.EMPTY, StringUtils.EMPTY, true, true, 1);

		// Compétition
		lAreaEntity = new AreaEntity(null, "Compétitions saison",
				ProfileEnum.ADMIN, true, (short) 4);
		AreaEntity lAreaCompetitionSaison = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.COMPETITION_EDITION.toString(), "Modification",
				lAreaCompetitionSaison.getId(), StringUtils.EMPTY,
				StringUtils.EMPTY, true, true, 1);

		lAreaEntity = new AreaEntity(null, "Structure du site",
				ProfileEnum.ADMIN, (short) 2);
		lAreaDao.save(lAreaEntity);

		createUserAdmin();
		createUserRoot();
		createUserOfficiel();
	}

	private void createMenu(String pMenuKey, String pTitle, Long pArea,
			String pSummary, String pContent, boolean pShortcut,
			boolean pDisplay, int pOrder) {
		MenuEntity lMenuHisto = new MenuEntity(pMenuKey, pTitle, pArea,
				pShortcut, pDisplay, pOrder, null, null);
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
		String[] result = new String[3];
		PicasawebService myService = new PicasawebService("asptt_test");
		try {
			myService.setUserCredentials(
					"webmaster@asptt-toulouse-natation.com", "31000_asptt");
			URL feedUrl = new URL(
					"https://picasaweb.google.com/data/feed/api/user/webmaster@asptt-toulouse-natation.com");

			Query myQuery = new Query(feedUrl);
			myQuery.setStringCustomParameter("kind", "photo");
			myQuery.setStringCustomParameter("tag", "banniere");

			AlbumFeed searchResultsFeed = myService.query(myQuery,
					AlbumFeed.class);

			result = new String[searchResultsFeed.getPhotoEntries().size()];
			int i = 0;
			for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
				result[i] = photo.getMediaContents().get(0).getUrl();
				i++;
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
}
