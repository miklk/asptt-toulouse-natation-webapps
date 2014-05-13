package com.asptttoulousenatation.core.page;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;

public class PageResource {

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao dao = new ContentDao();
	private String menuTitle;
	
	
	
	
	public PageResource(String pMenuTitle) {
		super();
		menuTitle = pMenuTitle;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public PageUi getPage() {
		final PageUi page;
		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<String> lMenuCriterion = new CriterionDao<String>(MenuEntityFields.TITLE, menuTitle, Operator.EQUAL);
		lMenuCriteria.add(lMenuCriterion);
		List<MenuEntity> menus = menuDao.find(lMenuCriteria);
		if(!menus.isEmpty()) {
			List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lContentCriterion = new CriterionDao<Long>(ContentEntityFields.MENU, menus.get(0).getId(), Operator.EQUAL);
			lContentCriteria.add(lContentCriterion);
			List<ContentEntity> contents = dao.find(lContentCriteria);
			if(!contents.isEmpty()) {
			page = new PageUi(new String(contents.get(0).getData().getBytes()));
			} else {
				page = new PageUi();	
			}
		} else {
			page = new PageUi();
		}
		return page;
	}	
}