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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.lang.StringUtils;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.util.CollectionUtils;
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
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.actu.GetAllActuAction;
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
import com.asptttoulousenatation.shared.event.UiEvent;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;
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

		// createData();
		// createUsers();
		// createCompetition();
		InitResult lInitResult = new InitResult();
		lInitResult.setPhoto(getPicture());

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
						List<MenuUi> lSubMenuUis = new ArrayList<MenuUi>(
								lMenuEntity.getSubMenu().size());
						for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
							MenuEntity lSubMenu = menuDao.get(lSubMenuId);
							lSubMenuUis.add(menuTransformer.toUi(lSubMenu));
						}
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
			// Actu
			List<ActuUi> lActu = pContext.execute(new GetAllActuAction()).getResult();
			LOG.info("retrieving actu #" + lActu.size());
			pContext.execute(new SetDataUpdateAction(ActuEntity.class, false));
			applicationLoader.setActu(lActu);
		}

		IsDataUpdateResult lEventUpdateResult = pContext
				.execute(new IsDataUpdateAction(CompetitionEntity.class));
		if (lEventUpdateResult.isDataUpdated()
				|| applicationLoader.getEvents() == null) {
			// Events "calendar"
			pContext.execute(new SetDataUpdateAction(CompetitionEntity.class,
					false));
			applicationLoader.setEvents(getEvents());
		}

		lInitResult.setArea(applicationLoader.getArea());
		lInitResult.setActu(applicationLoader.getActu());
		lInitResult.setEvents(applicationLoader.getEvents());
		Long endTime = System.currentTimeMillis();
		LOG.info("Loading duration: " + (endTime - startTime) + " ms");
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
			if (CollectionUtils.isEmpty(lEntity.getDays())) {
				List<UiEvent> lEventUis = competitionTransformer
						.toUiEvent(lEntity);
				for (UiEvent lEvent : lEventUis) {
					List<UiEvent> lEventList;
					Date lDate = new Date(Date.UTC(lEvent.getEventDate()
							.getYear(), lEvent.getEventDate().getMonth(),
							lEvent.getEventDate().getDate(), 0, 0, 0));
					if (lEvents.containsKey(lDate)) {
						lEventList = lEvents.get(lDate);
					} else {
						lEventList = new ArrayList<UiEvent>();
					}
					lEventList.add(lEvent);
					lEvents.put(lDate, lEventList);
				}
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
				"Consulter le calendrier", lAreaOfficier.getId(),
				StringUtils.EMPTY, StringUtils.EMPTY, true, true, 1);
		createMenu(MenuItems.OFFICIEL_SUBSCRIPTION.toString(), "Inscription",
				lAreaOfficier.getId(), StringUtils.EMPTY, StringUtils.EMPTY,
				true, true, 2);

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

		// Swimmer stat
		lAreaEntity = new AreaEntity(null, "Suivi des nageurs",
				ProfileEnum.ADMIN, false, (short) 5);
		AreaEntity lAreaStat = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.SWIMMER_STAT_DAY.toString(), "Par jour",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 1);
		createMenu(MenuItems.SWIMMER_STAT_WEEK.toString(), "Par semaine",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 2);
		createMenu(MenuItems.SWIMMER_STAT_MONTH.toString(), "Par mois",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 3);
		createMenu(MenuItems.SWIMMER_STAT_YEAR.toString(), "Par année",
				lAreaStat.getId(), StringUtils.EMPTY, StringUtils.EMPTY, true,
				false, 4);

		// Profil
		lAreaEntity = new AreaEntity(null, "Mon profil", ProfileEnum.NAGEUR,
				false, (short) 5);
		AreaEntity lAreaProfil = lAreaDao.save(lAreaEntity);
		createMenu(MenuItems.PROFILE_PASSWORD.toString(),
				"Changer de mot de passe", lAreaProfil.getId(),
				StringUtils.EMPTY, StringUtils.EMPTY, true, false, 1);

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
		String[] result = null;
		PicasawebService myService = new PicasawebService("asptt_test");
		try {
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
