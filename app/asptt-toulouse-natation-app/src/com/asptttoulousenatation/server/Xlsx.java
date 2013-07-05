package com.asptttoulousenatation.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;

public class Xlsx {

	public static void getXlsx(InputStream in, OutputStream out,
			InscriptionEntity pPrincipal, InscriptionEntity pEntity) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			XSSFSheet sheet = workbook.getSheetAt(0);
			sheet.getRow(4).getCell(25).setCellValue("x");

			if ("0".equals(pEntity.getCivilite())) {
				sheet.getRow(7).getCell(15).setCellValue("x");
			} else if ("1".equals(pEntity.getCivilite())) {
				sheet.getRow(7).getCell(20).setCellValue("x");
			} else if ("2".equals(pEntity.getCivilite())) {
				sheet.getRow(7).getCell(25).setCellValue("x");
			}
			sheet.getRow(9).getCell(6).setCellValue(pEntity.getNom());
			sheet.getRow(11).getCell(6).setCellValue(pEntity.getPrenom());
			// Date de naissance
			sheet.getRow(13).getCell(4)
					.setCellValue(pEntity.getDatenaissance().split("-")[2]);
			sheet.getRow(13).getCell(7)
					.setCellValue(pEntity.getDatenaissance().split("-")[1]);
			sheet.getRow(13).getCell(10)
					.setCellValue(pEntity.getDatenaissance().split("-")[0]);
			sheet.getRow(13).getCell(24)
					.setCellValue(pEntity.getLieunaissance());
			sheet.getRow(14).getCell(24).setCellValue(pEntity.getNationalite());
			sheet.getRow(16).getCell(4).setCellValue(pPrincipal.getAdresse());
			sheet.getRow(18).getCell(9).setCellValue(pPrincipal.getCodepostal());
			sheet.getRow(20).getCell(5).setCellValue(pPrincipal.getTelephone());
			sheet.getRow(22).getCell(5).setCellValue(pPrincipal.getEmail());

			if ("licenceLoisir".equals(pEntity.getTypeLicence())) {
				sheet.getRow(29).getCell(12).setCellValue("x");
			} else if ("licenceAdhesion".equals(pEntity.getTypeLicence())) {
				sheet.getRow(29).getCell(33).setCellValue("x");
			}
			if (BooleanUtils.toBoolean(pEntity.getLicenceFFN())) {
				sheet.getRow(34).getCell(13).setCellValue("x");
			} else {
				sheet.getRow(34).getCell(16).setCellValue("x");
			}
			if (BooleanUtils.toBoolean(pEntity.getCompetition())) {
				sheet.getRow(34).getCell(40).setCellValue("x");
			} else {
				sheet.getRow(34).getCell(43).setCellValue("x");
			}
			sheet.getRow(40).getCell(10)
					.setCellValue(pEntity.getAccordNomPrenom());
			if (StringUtils.isNotBlank(pEntity.getMineurParent())) {
				sheet.getRow(65).getCell(28)
						.setCellValue(pEntity.getMineurParent());
				sheet.getRow(67)
						.getCell(9)
						.setCellValue(
								pEntity.getNom() + " " + pEntity.getPrenom());
			}
			
			sheet.getRow(74).getCell(0).setCellValue(pPrincipal.getAccidentNom1() + " " + pPrincipal.getAccidentPrenom1());
			sheet.getRow(74).getCell(13).setCellValue(pPrincipal.getAccidentTelephone1());
			sheet.getRow(75).getCell(0).setCellValue(pPrincipal.getAccidentNom1() + " " + pPrincipal.getAccidentPrenom1());
			sheet.getRow(75).getCell(13).setCellValue(pPrincipal.getAccidentTelephone1());

			if(BooleanUtils.toBoolean(pEntity.getPresident())) {
				sheet.getRow(79).getCell(0).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getSecretaire())) {
				sheet.getRow(79).getCell(5).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getTresorier())) {
				sheet.getRow(79).getCell(10).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getBureau())) {
				sheet.getRow(79).getCell(15).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getCadre())) {
				sheet.getRow(79).getCell(23).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getOfficiel())) {
				sheet.getRow(79).getCell(29).setCellValue("X");
			}
			if(BooleanUtils.toBoolean(pEntity.getConseil())) {
				sheet.getRow(79).getCell(35).setCellValue("X");
			}
			
			if("scolaire".equals(pEntity.getProfession())) {
				sheet.getRow(83).getCell(8).setCellValue("X");
			}
			if("etudiant".equals(pEntity.getProfession())) {
				sheet.getRow(83).getCell(14).setCellValue("X");
			}
			if("salarie".equals(pEntity.getProfession())) {
				sheet.getRow(83).getCell(20).setCellValue("X");
			}
			if("retraite".equals(pEntity.getProfession())) {
				sheet.getRow(83).getCell(26).setCellValue("X");
			}
			if("demandeur".equals(pEntity.getProfession())) {
				sheet.getRow(83).getCell(32).setCellValue("X");
			}
			sheet.getRow(96).getCell(9).setCellValue(pPrincipal.getProfessionTextMere() + " / " + pPrincipal.getProfessionTextPere());
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
