package com.asptttoulousenatation.core.page;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.document.DocumentTransformer;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentDataKindEnum;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;

public class PageResource {
	private static final Logger LOG = Logger.getLogger(PageResource.class.getName());
	private MenuDao menuDao = new MenuDao();
	private ContentDao dao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	private String menuTitle;

	public PageResource(String pMenuTitle) {
		super();
		menuTitle = pMenuTitle;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public PageUi getPage() {
		final PageUi page;
		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		CriterionDao<String> lMenuCriterion = new CriterionDao<String>(MenuEntityFields.IDENTIFIER, menuTitle,
				Operator.EQUAL);
		lMenuCriteria.add(lMenuCriterion);
		List<MenuEntity> menus = menuDao.find(lMenuCriteria);
		if (!menus.isEmpty()) {
			Long menuId = menus.get(0).getId();
			page = new PageUi();
			getPageData(menuId, page);

			// Get sous-menu
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(MenuEntityFields.PARENT, menuId, Operator.EQUAL));
			List<MenuEntity> sousMenus = menuDao.find(criteria);
			List<PageUi> sousPages = new ArrayList<PageUi>();
			for (MenuEntity sousMenu : sousMenus) {
				PageUi sousPage = new PageUi();
				sousPage.setTitre(sousMenu.getTitle());
				getPageData(sousMenu.getId(), sousPage);
				sousPages.add(sousPage);
			}
			page.setHasSubContent(CollectionUtils.isNotEmpty(sousPages));
			page.setSousPages(sousPages);
		} else {
			page = new PageUi();
		}
		return page;
	}

	private void getPageData(Long menuId, PageUi page) {
		List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
		lContentCriteria.add(new CriterionDao<Long>(ContentEntityFields.MENU, menuId, Operator.EQUAL));
		lContentCriteria.add(
				new CriterionDao<String>(ContentEntityFields.KIND, ContentDataKindEnum.TEXT.name(), Operator.EQUAL));

		List<ContentEntity> contents = dao.find(lContentCriteria);
		if (!contents.isEmpty()) {
			for (ContentEntity content : contents) {
				try {
					page.setContent(new String(content.getData().getBytes(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Text encoding", e);
				}
				// Get documents
				List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				lDocumentCriteria.add(new CriterionDao<Long>(DocumentEntityFields.MENU, menuId, Operator.EQUAL));
				List<DocumentEntity> lDocumentEntities = documentDao.find(lDocumentCriteria);
				DocumentTransformer documentTransformer = new DocumentTransformer();
				List<DocumentUi> lDocumentUis = documentTransformer.toUi(lDocumentEntities);
				page.setDocuments(lDocumentUis);
			}
		}
	}
}