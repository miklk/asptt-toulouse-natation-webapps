package com.asptttoulousenatation.server.init;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
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
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateAction;
import com.asptttoulousenatation.core.shared.reference.IsDataUpdateResult;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.ApplicationLoader;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.server.util.Utils;
import com.asptttoulousenatation.shared.init.InitAction;
import com.asptttoulousenatation.shared.init.InitResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;
import com.google.gdata.data.Link;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class InitActionHandler implements ActionHandler<InitAction, InitResult> {

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();

	private AreaTransformer areaTransformer = new AreaTransformer();
	private MenuTransformer menuTransformer = new MenuTransformer();

	private ActuDao actuDao = new ActuDao();

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
}