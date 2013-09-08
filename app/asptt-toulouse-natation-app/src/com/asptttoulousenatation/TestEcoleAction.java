package com.asptttoulousenatation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class TestEcoleAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(TestEcoleAction.class
			.getName());

	private InscriptionDao inscriptionDao = new InscriptionDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String action = pReq.getParameter("action");
		if ("loadCreneaux".equals(action)) {
			loadCreneaux(pReq, pResp);
		} else if ("export".equals(action)) {
			export(pReq, pResp);
		} else if ("test".equals(action)) {
			test(pReq, pResp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doGet(pReq, pResp);
	}

	protected void export(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		// Créneau
		String creneau = pReq.getParameter("selectedCreneau");
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.ID, Long
				.valueOf(creneau), Operator.EQUAL));
		List<SlotEntity> creneaux = slotDao.find(criteria);
		if (CollectionUtils.isNotEmpty(creneaux)) {
			SlotEntity creneauEntity = creneaux.get(0);
			ServletOutputStream out = pResp.getOutputStream();
			String contentType = "application/pdf";
			String contentDisposition = "attachment;filename="
					+ creneauEntity.getDayOfWeek() + ".pdf;";
			pResp.setContentType(contentType);
			pResp.setHeader("Content-Disposition", contentDisposition);
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, out);
				document.open();
				float[] cellWidth = { 1f, 2f, 2f, 2f, 2f, 2f, 2f, 2f };
				PdfPTable table = new PdfPTable(cellWidth);
				table.setWidthPercentage(90);
				Font title = FontFactory.getFont(FontFactory.HELVETICA, 14,
						Font.BOLD);
				PdfPCell cell = new PdfPCell(new Paragraph(
						creneauEntity.getDayOfWeek(), title));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("NB"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Nom"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Prénom"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Année"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Moniteur"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Groupe"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Horaire"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph("Ligne d'eau"));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				List<InscriptionEntity> entities = inscriptionDao.getAll();
				int counter = 1;
				Font content = FontFactory.getFont(FontFactory.HELVETICA, 9);
				for (InscriptionEntity entity : entities) {
					if (StringUtils.contains(entity.getCreneaux(), creneau)) {
						table.addCell(new Paragraph(Integer.toString(counter),
								content));
						table.addCell(new Paragraph(entity.getNom(), content));
						table.addCell(new Paragraph(entity.getPrenom(), content));
						table.addCell(new Paragraph(entity.getDatenaissance(),
								content));
						table.addCell("");
						table.addCell("");
						table.addCell("");
						table.addCell("");
						counter++;
					}
				}
				document.add(table);
				document.close();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
			out.close();
		}
	}

	protected void loadCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		String groupe = "ECOLE DE NATATION - nouveaux adhérents";
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(GroupEntityFields.TITLE, groupe,
				Operator.EQUAL));
		List<GroupEntity> groupes = groupDao.find(criteria);
		if (CollectionUtils.isNotEmpty(groupes)) {
			GroupEntity groupeEntity = groupes.get(0);
			// Retrieve slots
			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP,
					groupeEntity.getId(), Operator.EQUAL));
			List<SlotEntity> lEntities = slotDao.find(criteria);
			List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
			Collections.sort(lUis, new Comparator<SlotUi>() {

				public int compare(SlotUi pO1, SlotUi pO2) {
					return pO1.getDayOfWeek().compareTo(pO2.getDayOfWeek());
				}

				protected void loadCreneaux(HttpServletRequest pReq,
						HttpServletResponse pResp) throws ServletException,
						IOException {
					String groupe = "ECOLE DE NATATION - nouveaux adhérents";
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteria.add(new CriterionDao<String>(
							GroupEntityFields.TITLE, groupe, Operator.EQUAL));
					List<GroupEntity> groupes = groupDao.find(criteria);
					if (CollectionUtils.isNotEmpty(groupes)) {
						GroupEntity groupeEntity = groupes.get(0);
						// Retrieve slots
						criteria = new ArrayList<CriterionDao<? extends Object>>(
								1);
						criteria.add(new CriterionDao<Long>(
								SlotEntityFields.GROUP, groupeEntity.getId(),
								Operator.EQUAL));
						List<SlotEntity> lEntities = slotDao.find(criteria);
						List<SlotUi> lUis = new SlotTransformer()
								.toUi(lEntities);
						Collections.sort(lUis, new Comparator<SlotUi>() {

							public int compare(SlotUi pO1, SlotUi pO2) {
								return pO1.getDayOfWeek().compareTo(
										pO2.getDayOfWeek());
							}
						});
						Gson gson = new Gson();
						String json = gson.toJson(lUis);
						pResp.setContentType("application/json;charset=UTF-8");
						pResp.getWriter().write(json);
					}
				}
			});
			Gson gson = new Gson();
			String json = gson.toJson(lUis);
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
		}
	}

	protected void test(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		InputStream template = new FileInputStream(getServletContext()
				.getRealPath(
						"v2" + System.getProperty("file.separator") + "doc"
								+ System.getProperty("file.separator")
								+ "templace_test.pdf"));

		PdfReader reader = new PdfReader(template);
		ServletOutputStream out = pResp.getOutputStream();
		String contentType = "application/pdf";
		String contentDisposition = "attachment;filename=test.pdf;";
		pResp.setContentType(contentType);
		pResp.setHeader("Content-Disposition", contentDisposition);
		try {
			PdfStamper stamper = new PdfStamper(reader, out);
			stamper.getAcroFields().setField("nom_prenom", "YAHOUUUUUU");
			stamper.close();
			reader.close();
			out.flush();
			out.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}