package com.asptttoulousenatation.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xlsx {

	public static void getXlsx(InputStream in, OutputStream out) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			XSSFSheet sheet = workbook.getSheetAt(0);
			sheet.getRow(4).getCell(25).setCellValue("x");
			sheet.getRow(7).getCell(15).setCellValue("x");
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
