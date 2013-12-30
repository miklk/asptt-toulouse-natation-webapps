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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
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
		} else if ("presence".equals(action)) {
			presence(pReq, pResp);
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
			GroupEntity group = groupDao.get(creneauEntity.getGroup());
			ServletOutputStream out = pResp.getOutputStream();
			String contentType = "application/pdf";
			String contentDisposition = "attachment;filename="
					+ group.getTitle() + "_" + creneauEntity.getDayOfWeek()
					+ ".pdf;";
			pResp.setContentType(contentType);
			pResp.setHeader("Content-Disposition", contentDisposition);
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, out);
				document.open();
				float[] cellWidth = { 1f, 2f, 2f, 2f, 2f, 2f, 2f, 2f };
				PdfPTable table = new PdfPTable(cellWidth);
				table.setWidthPercentage(90);
				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 14,
						Font.BOLD);
				String title = group.getTitle() + " "
						+ creneauEntity.getDayOfWeek();
				PdfPCell cell = new PdfPCell(new Paragraph(title, titleFont));
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
		// Retrieve slots
		List<SlotEntity> entities = slotDao.getAll();
		List<SlotUi> lUis = new ArrayList<SlotUi>();
		for (SlotEntity entity : entities) {
			SlotUi ui = new SlotTransformer().toUi(entity);
			lUis.add(ui);
			GroupEntity group = groupDao.get(entity.getGroup());
			ui.setGroup(new GroupTransformer().toUi(group));
		}
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				return pO1.getGroup().getTitle()
						.compareTo(pO2.getGroup().getTitle());
			}
		});
		Gson gson = new Gson();
		String json = gson.toJson(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
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

	protected void presence(HttpServletRequest pReq, HttpServletResponse pResp)
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
			GroupEntity group = groupDao.get(creneauEntity.getGroup());
			ServletOutputStream out = pResp.getOutputStream();
			String contentType = "application/octet-stream";
			String fileName = "presence_"
					+ StringUtils.split(StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(
							StringUtils.replace(group.getTitle(), " ", "_"),
							"-", "_"),",", "_"), "(", "_"), ")", "_"), "_" ) [0] + "_" + creneauEntity.getDayOfWeek()
					+ ".xls";
			LOG.warning("Print " + fileName);
			String contentDisposition = "attachment;filename=" + fileName;
			pResp.setContentType(contentType);
			pResp.setHeader("Content-Disposition", contentDisposition);

			InputStream fichier = new FileInputStream(getServletContext()
					.getRealPath(
							"v2" + System.getProperty("file.separator") + "doc"
									+ System.getProperty("file.separator")
									+ "presence_template.xls"));
			try {
				HSSFWorkbook workbook = new HSSFWorkbook(fichier);
				HSSFSheet sheet = workbook.getSheetAt(0);
				sheet.getPrintSetup().setLandscape(false);
				sheet.getRow(35)
						.getCell(0)
						.setCellValue(
								group.getTitle()
										+ " "
										+ creneauEntity.getDayOfWeek()
										+ " "
										+ creneauEntity.getBegin()
										/ 60
										+ ":"
										+ StringUtils.rightPad(
												"" + creneauEntity.getBegin()
														% 60, 2, "0"));

				// Adherent
				criteria = new ArrayList<CriterionDao<? extends Object>>(1);
				criteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.NOUVEAUGROUPE, group.getId(),
						Operator.EQUAL));
				int row = 13;
				int count = 0;
				List<InscriptionEntity> adherents = inscriptionDao
						.find(criteria, new OrderDao(InscriptionEntityFields.NOM, OrderOperator.ASC));
				for (InscriptionEntity adherent : adherents) {
					HSSFRow sheetRow = sheet.getRow(row);
					if (sheetRow != null) {
						if (StringUtils.isNotBlank(adherent.getCreneaux())
								&& adherent.getCreneaux().contains(creneau)) {
							try {

								sheet.getRow(row)
										.getCell(1)
										.setCellValue(
												adherent.getNom() + " "
														+ adherent.getPrenom());

								String anneeNaissance;
								if (StringUtils.isNotBlank(adherent
										.getDatenaissance())
										&& StringUtils.contains(
												adherent.getDatenaissance(),
												"-")) {

									anneeNaissance = adherent
											.getDatenaissance().split("-")[0];
								} else {
									anneeNaissance = "-";
								}
								sheet.getRow(row).getCell(2)
										.setCellValue(anneeNaissance);
							} catch (Exception e) {
								LOG.severe("Adherent " + adherent.getId() + " " + e.getMessage());
							}
							row++;
							count++;
						}
					} else {
						LOG.warning("Row null " + row);
					}
				}
				workbook.write(out);
			} catch (IOException e) {
				LOG.log(java.util.logging.Level.SEVERE,
						"Erreur d'écriture de la fiche de présence du créneau "
								+ creneauEntity.getId(), e);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
			}
			out.flush();
			out.close();
		}
	}
}