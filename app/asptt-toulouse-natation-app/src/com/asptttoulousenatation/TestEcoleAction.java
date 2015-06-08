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

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TestEcoleAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(TestEcoleAction.class
			.getName());

	private Inscription2Dao inscriptionDao = new Inscription2Dao();
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
		} else if ("presence".equals(action)) {
			presence(pReq, pResp);
		} else if ("jours".equals(action)) {
			jours(pReq, pResp);
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
				List<InscriptionEntity2> entities = inscriptionDao.getAll();
				int counter = 1;
				Font content = FontFactory.getFont(FontFactory.HELVETICA, 9);
				for (InscriptionEntity2 entity : entities) {
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
			if (entity.getGroup() == null || entity.getGroup() < 1) {
				LOG.severe("Groupe non valide pour " + entity.getId());
			} else {
				SlotUi ui = new SlotTransformer().toUi(entity);
				lUis.add(ui);
				GroupEntity group = groupDao.get(entity.getGroup());
				ui.setGroup(new GroupTransformer().toUi(group));
			}
		}
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				return pO1.getGroup().getTitle()
						.compareTo(pO2.getGroup().getTitle());
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
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
					+ StringUtils.split(StringUtils.replace(StringUtils
							.replace(StringUtils.replace(StringUtils.replace(
									StringUtils.replace(group.getTitle(), " ",
											"_"), "-", "_"), ",", "_"), "(",
									"_"), ")", "_"), "_")[0] + "_"
					+ creneauEntity.getDayOfWeek() + ".xls";
			LOG.warning("Print " + fileName);
			String contentDisposition = "attachment;filename=" + fileName;
			pResp.setContentType(contentType);
			pResp.setHeader("Content-Disposition", contentDisposition);

			InputStream fichier = new FileInputStream(getServletContext()
					.getRealPath(
							System.getProperty("file.separator") + "doc"
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
				List<InscriptionEntity2> adherents = inscriptionDao.find(
						criteria, new OrderDao(InscriptionEntityFields.NOM,
								OrderOperator.ASC));
				for (InscriptionEntity2 adherent : adherents) {
					HSSFRow sheetRow = sheet.getRow(row);
					if (sheetRow != null) {
						if (StringUtils.isNotBlank(adherent.getCreneaux())
								&& adherent.getCreneaux().contains(creneau)) {
							try {
								StringBuilder nomPrenom = new StringBuilder();
								nomPrenom.append(adherent.getNom()).append(" ")
										.append(adherent.getPrenom());
								if (BooleanUtils.isFalse(adherent
										.getCertificat())) {
									nomPrenom.append(" (1)");
								}
								if (BooleanUtils
										.isFalse(adherent.getPaiement())) {
									nomPrenom.append(" (2)");
								}
								if (BooleanUtils.isFalse(adherent.getComplet())) {
									nomPrenom.append(" (3)");
								}
								sheet.getRow(row).getCell(1)
										.setCellValue(nomPrenom.toString());

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
								LOG.severe("Adherent " + adherent.getId() + " "
										+ e.getMessage());
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

	protected void jours(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		// Créneau
		String jour = pReq.getParameter("selectedJour");
		String piscine = pReq.getParameter("selectedPiscine");
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(SlotEntityFields.DAYOFWEEK, jour,
				Operator.EQUAL));
		List<SlotEntity> creneaux = slotDao.find(criteria);
		if (CollectionUtils.isNotEmpty(creneaux)) {
			String fileName = "presence_" + jour + ".xls";
			LOG.warning("Print " + fileName);
			ServletOutputStream out = pResp.getOutputStream();
			String contentType = "application/octet-stream";
			String contentDisposition = "attachment;filename=" + fileName;
			pResp.setContentType(contentType);
			pResp.setHeader("Content-Disposition", contentDisposition);

			InputStream fichier = new FileInputStream(getServletContext()
					.getRealPath(
							System.getProperty("file.separator") + "doc"
									+ System.getProperty("file.separator")
									+ "presence_template2.xls"));
			try {
				HSSFWorkbook workbook = new HSSFWorkbook(fichier);
				HSSFSheet sheet = workbook.getSheetAt(0);
				sheet.getPrintSetup().setLandscape(false);
				List<InscriptionEntity2> adherentsSelected = new ArrayList<InscriptionEntity2>();
				for (SlotEntity creneauEntity : creneaux) {
					GroupEntity group = groupDao.get(creneauEntity.getGroup());
					if (BooleanUtils.isFalse(group.getLicenceFfn())) {
						// Adherent
						criteria = new ArrayList<CriterionDao<? extends Object>>(
								1);
						criteria.add(new CriterionDao<Long>(
								InscriptionEntityFields.NOUVEAUGROUPE, group
										.getId(), Operator.EQUAL));

						List<InscriptionEntity2> adherents = inscriptionDao
								.find(criteria, new OrderDao(
										InscriptionEntityFields.NOM,
										OrderOperator.ASC));
						try {
							for (InscriptionEntity2 adherent : adherents) {
								if (StringUtils.isNotBlank(adherent
										.getCreneaux())
										&& adherent.getCreneaux().contains(
												Long.toString(creneauEntity
														.getId()))
										&& StringUtils.isNotBlank(creneauEntity
												.getSwimmingPool())
										&& StringUtils
												.containsIgnoreCase(
														creneauEntity
																.getSwimmingPool(),
														piscine)) {
									adherentsSelected.add(adherent);
								}
							}
						} catch (Exception e) {
							LOG.severe("Erreur récupération des adhérents du groupe "
									+ group.getTitle() + " " + e.getMessage());
						}
					}
				}
				int row = 2;
				HSSFRow sheetRow = sheet.getRow(0);
				sheetRow.getCell(0).setCellValue(jour + " " + piscine);
				Collections.sort(adherentsSelected,
						new Comparator<InscriptionEntity2>() {

							@Override
							public int compare(InscriptionEntity2 pO1,
									InscriptionEntity2 pO2) {
								if (pO1 == pO2) {
									return 0;
								} else if (pO1 == null) {
									return -1;
								} else if (pO2 == null) {
									return 1;
								} else {
									final int nom;
									if (pO1.getNom() == pO2.getNom()) {
										nom = 0;
									} else if (pO1.getNom() == null) {
										nom = -1;
									} else if (pO2.getNom() == null) {
										nom = 1;
									} else {
										nom = pO1.getNom().compareTo(
												pO2.getNom());
									}

									if (nom == 0) {
										if (pO1.getPrenom() == pO2.getPrenom()) {
											return 0;
										} else if (pO1.getPrenom() == null) {
											return -1;
										} else if (pO2.getPrenom() == null) {
											return 1;
										} else {
											return pO1.getPrenom().compareTo(
													pO2.getPrenom());
										}
									} else {
										return nom;
									}
								}
							}
						});
				for (InscriptionEntity2 adherent : adherentsSelected) {
					sheetRow = sheet.createRow(row);
					if (sheetRow != null) {
						try {
							GroupEntity groupEntity = groupDao.get(adherent
									.getNouveauGroupe());
							List<CriterionDao<? extends Object>> criteriaCreneau = new ArrayList<CriterionDao<? extends Object>>(
									2);
							criteriaCreneau.add(new CriterionDao<String>(
									SlotEntityFields.DAYOFWEEK, jour,
									Operator.EQUAL));
							criteriaCreneau.add(new CriterionDao<Long>(
									SlotEntityFields.GROUP,
									groupEntity.getId(), Operator.EQUAL));
							List<SlotEntity> creneauxEntities = slotDao
									.find(criteriaCreneau);
							sheetRow.createCell(0)
									.setCellValue(
											StringUtils.defaultString(adherent
													.getNom()));
							sheetRow.getCell(0).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(1).setCellValue(
									StringUtils.defaultString(adherent
											.getPrenom()));
							sheetRow.getCell(1).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(2).setCellValue(
									StringUtils.defaultString(adherent
											.getDatenaissance()));
							sheetRow.getCell(2).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(3).setCellValue(
									StringUtils.defaultString(groupEntity
											.getTitle()));
							sheetRow.getCell(3).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(4).setCellValue(
									StringUtils.defaultString(creneauxEntities
											.get(0).getEducateur()));
							sheetRow.getCell(4).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
						} catch (Exception e) {
							LOG.severe("Adherent " + adherent.getId() + " "
									+ e.getMessage());
						}
						row++;
					} else {
						LOG.warning("Row null " + row);
					}
				}
				workbook.write(out);
			} catch (IOException e) {
				LOG.log(java.util.logging.Level.SEVERE,
						"Erreur d'écriture de la fiche de présence du créneau",
						e);
			} catch (Exception e) {
				LOG.severe(e.getMessage());
			}
			out.flush();
			out.close();
		}
	}
}