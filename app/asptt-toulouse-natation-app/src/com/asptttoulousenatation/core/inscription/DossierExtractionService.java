package com.asptttoulousenatation.core.inscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/dossiers/extraction")
@Produces("application/json")
public class DossierExtractionService {

	private static final Logger LOG = Logger.getLogger(DossierExtractionService.class.getName());

	private DossierNageurDao dao = new DossierNageurDao();
	private DossierDao dossierDao = new DossierDao();
	private GroupDao groupeDao = new GroupDao();

	@Path("/fields/{fields}{conditions:(/[^/]+?)?}")
	@GET
	@Produces("text/csv; charset=UTF-8")
	public Response extraction(@PathParam("fields") String fields, @QueryParam("groupes") Set<Long> groupes,
			@PathParam("conditions") String conditions, @QueryParam("dossierOnly") Boolean dossierOnly) {
		String[] fieldsToChoose = fields.split("_");
		Set<String> conditionsToAdd = new HashSet<>(Arrays.asList(conditions.split("_")));
		List<List<String>> extractions = new ArrayList<>();
		Map<Long, List<String>> extractionDossiers = new HashMap<>();

		List<DossierNageurEntity> nageurs;
		List<CriterionDao<? extends Object>> criteria;
		if (CollectionUtils.isNotEmpty(groupes)) {
			criteria = new ArrayList<CriterionDao<? extends Object>>(
					groupes.size());
			for (Long groupe : groupes) {
				criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE, groupe, Operator.EQUAL));
			}
			nageurs = dao.find(criteria, Operator.OR, null);
		} else {
			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
					DossierService.NEW_SAISON, Operator.EQUAL));
			nageurs = dao.find(criteria);
		}
		
		for (DossierNageurEntity nageur : nageurs) {
			if (canAddNageur(conditionsToAdd, nageur)) {
				DossierEntity dossier = dossierDao.get(nageur.getDossier());
				List<String> nageurValues = new ArrayList<>(fieldsToChoose.length);
				for (String field : fieldsToChoose) {
					switch (field) {
					case "NOM":
						nageurValues.add(nageur.getNom());
						break;
					case "PRENOM":
						nageurValues.add(nageur.getPrenom());
						break;
					case "EMAIL": {
						String email = dossier.getEmail();
						if(StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
							email+=" / " + dossier.getEmailsecondaire();
						}
						nageurValues.add(email);
					}
						break;
					case "GROUPE": {
						if (nageur.getGroupe() != null) {
							GroupEntity groupe = groupeDao.get(nageur.getGroupe());
							if (groupe != null) {
								nageurValues.add(groupe.getTitle());
							}
						}
					}
						break;
					case "SHORT":
						nageurValues.add(nageur.getShortPantalon());
						break;
					case "TSHIRT":
						nageurValues.add(nageur.getTshirt());
						break;
					case "MAILLOT":
						nageurValues.add(nageur.getMaillot());
						break;
					case "PROFESSION": {
						nageurValues.add(nageur.getProfession());
						nageurValues.add(dossier.getParent1Profession());
						nageurValues.add(dossier.getParent2Profession());
					}
						break;
					case "MONTANT":
						nageurValues.add(nageur.getTarif() == null ? "0" : nageur.getTarif().toString());
						break;
					case "COMMENTAIRE":
						nageurValues.add(StringUtils.defaultString(dossier.getComment()));
						break;
					case "PAIEMENT":
						String paiement = "";
						switch(DossierStatutEnum.valueOf(dossier.getStatut())) {
						case ANNULE: 
						case ATTENTE:
						case EXPIRE:
						case INITIALISE:
						case PREINSCRIT:
						case PAIEMENT_PARTIEL:
							paiement = "non payé";
						break;
						case PAIEMENT_COMPLET:
						case INSCRIT:
							paiement = "à jour";
							break;
							default: paiement = "invalide";
						}
						nageurValues.add(paiement);
						break;
					case "CONTRIBUTION" : nageurValues.add(dossier.getContribution());
					break;
					default:// Do nothing
					}
				}
				extractions.add(nageurValues);
				if(dossierOnly) {
					extractionDossiers.put(nageur.getDossier(), nageurValues);
				}
			}
		}
		
		if(dossierOnly) {
			extractions = new ArrayList<>(extractionDossiers.values());
		}
		
		StrBuilder extractionAsString = new StrBuilder();
		// Build header
		extractionAsString.appendWithSeparators(fieldsToChoose, ",");
		if (fields.contains("PROFESSION")) {
			extractionAsString.append(",").append("PROFESSION PARENT 1").append(",").append("PROFESSION PARENT 2");
		}
		extractionAsString.appendNewLine();
		for (List<String> nageurFields : extractions) {
			StrBuilder nageurFieldsBuilder = new StrBuilder();
			nageurFieldsBuilder.appendWithSeparators(nageurFields, ",");
			extractionAsString.append(nageurFieldsBuilder.toString()).appendNewLine();
		}
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(extractionAsString.toString().getBytes("UTF-8"));

			String contentDisposition = "attachment;filename=extraction.csv;";
			return Response.ok(out.toByteArray(), "text/csv").header("content-disposition", contentDisposition).build();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
			return Response.serverError().build();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
					return Response.serverError().build();
				}
			}
		}
	}

	private boolean canAddNageur(Set<String> conditions, DossierNageurEntity nageur) {
		boolean canAdd = true;
		if (nageur.getSaison() != null && nageur.getSaison().equals(DossierService.NEW_SAISON)) {
			for (String condition : conditions) {
				switch (condition) {
				case "FACTURE": {
					DossierEntity dossier = dossierDao.get(nageur.getDossier());
					canAdd = canAdd && BooleanUtils.toBoolean(dossier.getFacture());
				}
					break;
				default: // Do nothing
				}
			}
		} else {
			canAdd = false;
		}
		return canAdd;
	}
	
	@Path("/adherons")
	@POST
	@Produces("text/csv; charset=UTF-8")
	public String adherons(List<Long> dossierIds) {
		StrBuilder extractionAsString = new StrBuilder();
		for (Long dossierId : dossierIds) {
			List<String> nageurFields = new ArrayList<>();
			DossierEntity dossier = dossierDao.get(dossierId);
			List<DossierNageurEntity> nageurs = dao.findByDossier(dossierId);
			for (DossierNageurEntity nageur : nageurs) {
				nageurFields.add(nageur.getNom());
				nageurFields.add(nageur.getPrenom());
				nageurFields.add(new DateTime(nageur.getNaissance().getTime()).toString("dd/MM/yyyy"));
				nageurFields.add(nageur.getCivilite() == "0" ? "M" : "F");
				nageurFields.add(dossier.getEmail());
				nageurFields.add(nageur.getProfession());
				nageurFields.add("1");// Benevole
				nageurFields.add(dossier.getAdresse());
				nageurFields.add("");// Adresse suite
				nageurFields.add(dossier.getCodepostal());
				nageurFields.add(dossier.getVille());
				nageurFields.add(dossier.getTelephone());
				nageurFields.add(dossier.getTelephoneSecondaire());
				nageurFields.add("");// La poste
				nageurFields.add("");// Identifiant la poste
				nageurFields.add(BooleanUtils.isTrue(nageur.getFonctionnaire()) ? "0" : "1");
				nageurFields.add("0");// Image
				nageurFields.add("0");// Email FSASPTT
				nageurFields.add("1"); // Info partenaires
				nageurFields.add("1"); // Compétition
				nageurFields.add("1");// Licence dÈlÈgataire;
				nageurFields.add("1");// Dirigeant de l'activitÈ;
				nageurFields.add("1");// Juge arbitre pour cette activitÈ;
				nageurFields.add(nageur.getTarif().toString());// Montant
																// section;
				nageurFields.add("23");// Montant licence dÈlÈgataire;
				nageurFields.add("0");// Montant autre;
				nageurFields.add("0");// Id catÈgorie
				extractionAsString.appendWithSeparators(nageurFields, ";").appendNewLine();
			}
		}

		return extractionAsString.toString();
		/**ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(extractionAsString.toString().getBytes("UTF-8"));

			String contentDisposition = "attachment;filename=adherons.csv;";
			return Response.ok(out.toByteArray(), "text/csv").header("content-disposition", contentDisposition).build();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
			return Response.serverError().build();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
					return Response.serverError().build();
				}
			}
		}**/
	}
}
