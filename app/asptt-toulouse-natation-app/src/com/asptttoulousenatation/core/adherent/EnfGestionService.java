package com.asptttoulousenatation.core.adherent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.enf.EnfDayGroupe;
import com.asptttoulousenatation.core.enf.EnfGroupeCreneau;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.util.DayComparator;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

@Path("/enf")
@Produces("application/json")
public class EnfGestionService {

	private static final Logger LOG = Logger.getLogger(EnfGestionService.class.getName());

	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private DossierNageurDao nageurDao = new DossierNageurDao();
	private DossierDao dossierDao = new DossierDao();

	@Path("/creneaux")
	@GET
	public List<EnfDayGroupe> creneauPerGroupePerDay() {
		List<EnfDayGroupe> days = new ArrayList<>(7);
		List<SlotEntity> entities = slotDao.getAll();
		Map<String, EnfDayGroupe> daysMap = new HashMap<>();
		for (SlotEntity entity : entities) {
			GroupEntity groupeEntity = groupDao.get(entity.getGroup());
			if (groupeEntity != null) {
				if (BooleanUtils.isTrue(groupeEntity.getEnf())) {
					String day = entity.getDayOfWeek();
					final EnfDayGroupe dayGroupe;
					if (daysMap.containsKey(day)) {
						dayGroupe = daysMap.get(day);
					} else {
						dayGroupe = new EnfDayGroupe();
						dayGroupe.setDayOfWeek(day);
						daysMap.put(day, dayGroupe);
					}
					EnfGroupeCreneau groupe = new EnfGroupeCreneau();

					groupe.setGroupe(groupeEntity.getTitle());
					groupe.addCreneau(SlotTransformer.getInstance().toUi(entity));
					dayGroupe.addGroupe(groupe);
				}
			} else {
				LOG.warning("Créneau sans groupe " + entity.getId() + " (" + entity.getGroup() + ")");
			}
		}

		days.addAll(daysMap.values());
		for(EnfDayGroupe day: days) {
			day.sort();
		}
		Collections.sort(days, new DayComparator());
		return days;
	}

	@Path("/presences/{creneau}")
	@GET
	@Produces({ "application/vnd.ms-excel" })
	public Response fiche(@PathParam("creneau") Long creneau) {
		// Créneau
		SlotEntity creneauEntity = slotDao.get(creneau);

		if (creneauEntity != null) {
			GroupEntity groupe = groupDao.get(creneauEntity.getGroup());
			URL resource = getClass().getResource("/");
			String path = resource.getPath();
			path = path.replace("WEB-INF/classes/", "");

			try {
				InputStream fichier = new FileInputStream(path + "doc/presence_template.xls");
				HSSFWorkbook workbook = new HSSFWorkbook(fichier);
				HSSFSheet sheet = workbook.getSheetAt(0);
				sheet.getPrintSetup().setLandscape(false);
				sheet.getRow(35).getCell(0)
						.setCellValue(groupe.getTitle() + " " + creneauEntity.getDayOfWeek() + " "
								+ new DateTime(creneauEntity.getBeginDt().getTime()).toString("HH:mm"));

				// Adherent
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
				criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE, groupe.getId(), Operator.EQUAL));
				int row = 13;
				List<DossierNageurEntity> entities = nageurDao.find(criteria,
						new OrderDao(DossierNageurEntityFields.NOM, OrderOperator.ASC));
				for (DossierNageurEntity adherent : entities) {
					HSSFRow sheetRow = sheet.getRow(row);
					if (sheetRow != null) {
						if (StringUtils.isNotBlank(adherent.getCreneaux())
								&& adherent.getCreneaux().contains(creneau.toString())) {
							try {
								StringBuilder nomPrenom = new StringBuilder();
								nomPrenom.append(adherent.getNom()).append(" ").append(adherent.getPrenom());
								if (BooleanUtils.isFalse(adherent.getCertificat())) {
									nomPrenom.append(" (1)");
								}

								DossierEntity dossier = dossierDao.get(adherent.getDossier());
								if (!DossierStatutEnum.PAIEMENT_COMPLET.equals(dossier.getStatut())
										&& !DossierStatutEnum.INSCRIT.equals(dossier.getStatut())) {
									nomPrenom.append(" (2)");
								}
								sheet.getRow(row).getCell(1).setCellValue(nomPrenom.toString());

								String anneeNaissance;
								if (adherent.getNaissance() != null) {
									anneeNaissance = Integer
											.toString(new DateTime(adherent.getNaissance().getTime()).getYear());
								} else {
									anneeNaissance = "-";
								}
								sheet.getRow(row).getCell(2).setCellValue(anneeNaissance);
							} catch (Exception e) {
								LOG.log(Level.SEVERE, "Adherent " + adherent.getId(), e);
							}
							row++;
						}
					} else {
						LOG.warning("Row null " + row);
					}
				}

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				workbook.write(out);

				String fileName = "presence_"
						+ StringUtils
								.split(StringUtils.replace(StringUtils.replace(
										StringUtils.replace(StringUtils.replace(
												StringUtils.replace(groupe.getTitle(), " ", "_"), "-", "_"), ",", "_"),
						"(", "_"), ")", "_"), "_")[0] + "_" + creneauEntity.getDayOfWeek() + ".xls";
				String contentDisposition = "attachment;filename=" + fileName;
				return Response.ok(out.toByteArray(), "application/vnd.ms-excel")
						.header("content-disposition", contentDisposition).build();
			} catch (IOException e) {
				LOG.log(java.util.logging.Level.SEVERE,
						"Erreur d'écriture de la fiche de présence du créneau " + creneauEntity.getId(), e);
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Error presence", e);
			}
		}
		return Response.serverError().build();
	}

	@Path("/jours/{day}/{piscine}")
	@GET
	@Produces({ "application/vnd.ms-excel" })
	public Response jours(@PathParam("day") String jour, @PathParam("piscine") String piscine) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(SlotEntityFields.DAYOFWEEK, jour, Operator.EQUAL));
		List<SlotEntity> creneaux = slotDao.find(criteria);
		if (CollectionUtils.isNotEmpty(creneaux)) {
			String fileName = "presence_" + jour + ".xls";

			URL resource = getClass().getResource("/");
			String path = resource.getPath();
			path = path.replace("WEB-INF/classes/", "");
			try {
				InputStream fichier = new FileInputStream(path + "doc/presence_template2.xls");
				HSSFWorkbook workbook = new HSSFWorkbook(fichier);
				HSSFSheet sheet = workbook.getSheetAt(0);
				sheet.getPrintSetup().setLandscape(false);
				List<DossierNageurEntity> adherentsSelected = new ArrayList<DossierNageurEntity>();
				for (SlotEntity creneauEntity : creneaux) {
					GroupEntity group = groupDao.get(creneauEntity.getGroup());
					if(group != null) {
						if (BooleanUtils.isFalse(group.getLicenceFfn())) {
							// Adherent
							criteria = new ArrayList<CriterionDao<? extends Object>>(1);
							criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE, group.getId(),
									Operator.EQUAL));
	
							List<DossierNageurEntity> adherents = nageurDao.find(criteria,
									new OrderDao(DossierNageurEntityFields.NOM, OrderOperator.ASC));
							try {
								for (DossierNageurEntity adherent : adherents) {
									if (StringUtils.isNotBlank(adherent.getCreneaux())
											&& adherent.getCreneaux().contains(Long.toString(creneauEntity.getId()))
											&& StringUtils.isNotBlank(creneauEntity.getSwimmingPool())
											&& StringUtils.containsIgnoreCase(creneauEntity.getSwimmingPool(), piscine)) {
										adherentsSelected.add(adherent);
									}
								}
							} catch (Exception e) {
								LOG.log(Level.SEVERE, "Erreur récupération des adhérents du groupe " + group.getTitle(), e);
							}
						}
					}
				}
				int row = 2;
				HSSFRow sheetRow = sheet.getRow(0);
				sheetRow.getCell(0).setCellValue(jour + " " + piscine);
				Collections.sort(adherentsSelected, new Comparator<DossierNageurEntity>() {

					@Override
					public int compare(DossierNageurEntity pO1, DossierNageurEntity pO2) {
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
								nom = pO1.getNom().compareTo(pO2.getNom());
							}

							if (nom == 0) {
								if (pO1.getPrenom() == pO2.getPrenom()) {
									return 0;
								} else if (pO1.getPrenom() == null) {
									return -1;
								} else if (pO2.getPrenom() == null) {
									return 1;
								} else {
									return pO1.getPrenom().compareTo(pO2.getPrenom());
								}
							} else {
								return nom;
							}
						}
					}
				});
				for (DossierNageurEntity adherent : adherentsSelected) {
					sheetRow = sheet.createRow(row);
					if (sheetRow != null) {
						try {
							GroupEntity groupEntity = groupDao.get(adherent.getGroupe());
							List<CriterionDao<? extends Object>> criteriaCreneau = new ArrayList<CriterionDao<? extends Object>>(
									2);
							criteriaCreneau
									.add(new CriterionDao<String>(SlotEntityFields.DAYOFWEEK, jour, Operator.EQUAL));
							criteriaCreneau.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupEntity.getId(),
									Operator.EQUAL));
							List<SlotEntity> creneauxEntities = slotDao.find(criteriaCreneau);
							sheetRow.createCell(0).setCellValue(StringUtils.defaultString(adherent.getNom()));
							sheetRow.getCell(0).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(0).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(1).setCellValue(StringUtils.defaultString(adherent.getPrenom()));
							sheetRow.getCell(1).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(1).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

							String naissance = "";
							if (adherent.getNaissance() != null) {
								naissance = new DateTime(adherent.getNaissance().getTime())
										.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
							}
							sheetRow.createCell(2).setCellValue(naissance);
							sheetRow.getCell(2).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(2).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(3).setCellValue(StringUtils.defaultString(groupEntity.getTitle()));
							sheetRow.getCell(3).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(3).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.createCell(4)
									.setCellValue(StringUtils.defaultString(creneauxEntities.get(0).getEducateur()));
							sheetRow.getCell(4).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(4).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
							
							boolean hasCertificat = BooleanUtils.toBoolean(adherent.getCertificat());
							sheetRow.createCell(5)
							.setCellValue(BooleanUtils.toString(hasCertificat, "", "KO"));
							sheetRow.getCell(5).getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(5).getCellStyle().setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(5).getCellStyle().setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
							sheetRow.getCell(5).getCellStyle().setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
						} catch (Exception e) {
							LOG.log(Level.SEVERE, "Adherent " + adherent.getId(), e);
						}
						row++;
					} else {
						LOG.warning("Row null " + row);
					}
				}

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				workbook.write(out);
				String contentDisposition = "attachment;filename=" + fileName;
				return Response.ok(out.toByteArray(), "application/vnd.ms-excel")
						.header("content-disposition", contentDisposition).build();
			} catch (IOException e) {
				LOG.log(java.util.logging.Level.SEVERE, "Erreur d'écriture de la fiche de présence du créneau", e);
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Error export jour", e);
			}

		}
		return Response.serverError().build();
	}
}