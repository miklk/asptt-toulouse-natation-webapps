package com.asptttoulousenatation.core.page;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.google.appengine.api.datastore.Blob;

@Path("/page-edition")
@Produces("application/json")
public class PageEditionService {

	private static final Logger LOG = Logger.getLogger(PageEditionService.class
			.getName());

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();
	
	@Path("/area/all")
	@GET
	public List<AreaEntity> findArea() {
		return new ArrayList<>(areaDao.getAll());
	}
	
	@Path("/area/save")
	@POST
	public void saveArea(PageEditionSaveAreaParameters parameters) {
		final AreaEntity entity;
		if(parameters.getId()!= null) {
			entity = areaDao.get(parameters.getId());
		} else {
			entity = new AreaEntity();
		}
		entity.setTitle(parameters.getTitle());
		entity.setOrder(parameters.getOrder().shortValue());
		areaDao.save(entity);
	}
	
	@Path("/area/pages/{areaId}")
	@GET
	public List<PageEditionUi> findPage(@PathParam("areaId") Long area) {
		List<MenuEntity> menus = menuDao.findByArea(area);
		List<PageEditionUi> uis = new ArrayList<>();
		for(MenuEntity menu: menus) {
			PageEditionUi ui = new PageEditionUi();
			ui.setId(menu.getId());
			ui.setTitle(menu.getTitle());
			ui.setOrder(menu.getOrder());
			ui.setAlone(menu.getAlone());
			ui.setDisplay(menu.isDisplay());
			ui.setDivider(menu.getDivider());
			ui.setIdentifier(menu.getIdentifier());
			
			//Content
			ContentEntity content = contentDao.findByMenu(menu.getId());
			if(content != null) {
				ui.setContent(new String(content.getData().getBytes()));
			}
			uis.add(ui);
		}
		return uis;
	}
	
	@Path("/page/save/{areaId}")
	@POST
	public void savePage(@PathParam("areaId") Long area, PageEditionUi page) {
		final MenuEntity entity;
		ContentEntity content;
		if(page.getId() == null) {
			entity = new MenuEntity();
			content = new ContentEntity();
		} else {
			entity = menuDao.get(page.getId());
			content = contentDao.findByMenu(page.getId());
			if(content == null) {
				content = new ContentEntity();
			}
		}
		entity.setArea(area);
		entity.setTitle(page.getTitle());
		entity.setOrder(page.getOrder());
		entity.setDivider(page.getDivider());
		entity.setAlone(page.getAlone());
		entity.setIdentifier(page.getIdentifier());
		entity.setDisplay(page.getDisplay());
		MenuEntity menu = menuDao.save(entity);
		content.setMenu(menu.getId());
		content.setKind(ContentDataKindEnum.TEXT.name());
		content.setData(new Blob(page.getContent().getBytes()));
		contentDao.save(content);
	}
}
