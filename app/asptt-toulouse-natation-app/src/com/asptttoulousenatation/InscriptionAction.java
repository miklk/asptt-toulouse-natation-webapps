package com.asptttoulousenatation;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class InscriptionAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(InscriptionAction.class
			.getName());

	private Inscription2Dao inscription2Dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doPost(pReq, pResp);
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String action = pReq.getParameter("action");
		if ("loadCreneaux".equals(action)) {
			loadCreneaux(pReq, pResp);
		} else if("imprimerNew".equals(action)) {
			imprimerNew(pReq, pResp);
		}
	}

	protected void loadCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long groupId = Long.valueOf(pReq.getParameter("selectedGroupe"));
		// Retrieve slots
		List<CriterionDao<? extends Object>> lSlotCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lSlotCriterion = new CriterionDao<Long>(
				SlotEntityFields.GROUP, groupId, Operator.EQUAL);
		lSlotCriteria.add(lSlotCriterion);
		SlotDao slotDao = new SlotDao();
		List<SlotEntity> lEntities = slotDao.find(lSlotCriteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				return pO1.getDayOfWeek().compareTo(pO2.getDayOfWeek());
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void loadGroupes(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.INSCRIPTION,
				Boolean.TRUE, Operator.EQUAL));
		GroupDao dao = new GroupDao();
		List<GroupEntity> entities = dao.find(criteria);
		List<GroupUi> lUis = new GroupTransformer().toUi(entities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			@Override
			public int compare(GroupUi pO1, GroupUi pO2) {
				if (StringUtils.containsIgnoreCase(pO1.getTitle(), "Ecole")) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void imprimerNew(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		Long numero = 0l;
		try {
			numero = Long.valueOf(pReq.getParameter("numero"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		InscriptionEntity2 adherent = inscription2Dao.get(numero);
		InscriptionEntity2 parent = adherent;
		if (adherent.getPrincipal() != null) {
			parent = inscription2Dao.get(adherent.getPrincipal());
		}
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "");
    	PdfReader reader = new PdfReader(new FileInputStream(path + "doc/bulletin.pdf"));

		
    	ServletOutputStream out = pResp.getOutputStream();
    	String contentType = "application/pdf";
		String contentDisposition = "attachment;filename=inscription_asptt_natation.pdf;";
		pResp.setContentType(contentType);
		pResp.setHeader("Content-Disposition", contentDisposition);

		try {
			PdfStamper 	stamper = new PdfStamper(reader, out);
    	AcroFields fields = stamper.getAcroFields();

		fields.setField("untitled1", adherent.getNom());
		fields.setField("untitled2", adherent.getPrenom());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dateNaissance = format.parse(adherent.getDatenaissance());
				SimpleDateFormat formatDD = new SimpleDateFormat("dd");
				fields.setField("untitled3", formatDD.format(dateNaissance));
				SimpleDateFormat formatMM = new SimpleDateFormat("MM");
				fields.setField("untitled4", formatMM.format(dateNaissance));
				SimpleDateFormat formatYYYY = new SimpleDateFormat("yyyy");
				fields.setField("untitled5", formatYYYY.format(dateNaissance));
			} catch (ParseException e) {
				LOG.severe("Adherent " + adherent.getNom() + " "
						+ e.getMessage());
			}
		
		
		fields.setField("untitled6", parent.getProfession());
		fields.setField("untitled7", parent.getAdresse());
		fields.setField("untitled8", parent.getCodepostal());
		fields.setField("untitled9", parent.getVille());
		fields.setField("untitled10", parent.getTelephone());
		fields.setField("untitled11", parent.getEmail());
		fields.setField("untitled12", parent.getAccordNomPrenom());
		fields.setField("untitled13", adherent.getMineurParent());
		fields.setField("untitled14", adherent.getMineur());
		
			if (StringUtils.isNotBlank(adherent.getCivilite())) {
				switch (adherent.getCivilite()) {
				case "0":
					fields.setField("untitled15", "X");
					break;
				case "1":
					fields.setField("untitled16", "X");
					break;
				default:
				}
			}
		
		if(BooleanUtils.isTrue(adherent.getNouveau())) {
			fields.setField("untitled17", "X");
		} else {
			fields.setField("untitled18", "X");
		}
		
		GroupEntity group = groupDao.get(adherent.getNouveauGroupe());
		if(BooleanUtils.isTrue(group.getLicenceFfn())) {
			fields.setField("untitled19", "X");
			fields.setField("untitled20", "X");
		}
		
		fields.setField("accidentNom1", StringUtils.defaultString(parent.getAccidentNom1()) + " " + StringUtils.defaultString(parent.getAccidentPrenom1()));
		fields.setField("accidentTel1", parent.getAccidentTelephone1());
		fields.setField("accidentNom2", StringUtils.defaultString(parent.getAccidentNom2()) + " " + StringUtils.defaultString(parent.getAccidentPrenom2()));
		fields.setField("accidentTel2", parent.getAccidentTelephone2());
		
        stamper.close();
        reader.close();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out.flush();
		out.close();
	}
}