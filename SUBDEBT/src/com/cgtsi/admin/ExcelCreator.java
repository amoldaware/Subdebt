/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtsi.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cgtsi.actionform.RPActionForm;


public class ExcelCreator {

    public HSSFWorkbook createWorkbook(ArrayList userList) throws Exception {
    	HSSFWorkbook wb = new HSSFWorkbook();
    	try
    	{
        
        HSSFSheet sheet = wb.createSheet("User Data");
        DecimalFormat dec = new DecimalFormat("#0.00");
        /**
         * Setting the width of the first three columns.
         */
        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 7500);
        sheet.setColumnWidth(2, 5000);

        /**
         * Style for the header cells.
         */
        HSSFCellStyle headerCellStyle = wb.createCellStyle();
        HSSFFont boldFont = wb.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(boldFont);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("PAY ID"));
        cell = row.createCell(1);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("VERTUAL ACCOUNT NO."));
        cell = row.createCell(2);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("AMOUNT"));
        cell = row.createCell(3);
        cell.setCellStyle(headerCellStyle);
        cell.setCellValue(new HSSFRichTextString("IFSC CODE"));
        cell = row.createCell(4);
        cell.setCellStyle(headerCellStyle);

        for (int index = 0; index < userList.size(); index++) {
            row = sheet.createRow(index+1);
            cell = row.createCell(0);
          //  UserData userData = (UserData) userList.get(index);
            RPActionForm actionForm =(RPActionForm)userList.get(index);
            HSSFRichTextString PaymentId = new HSSFRichTextString(actionForm.getPaymentId1());
            cell.setCellValue(PaymentId);
            cell = row.createCell(1);
            HSSFRichTextString Vaccno = new HSSFRichTextString(actionForm.getVaccno());
            cell.setCellValue(Vaccno);
            cell = row.createCell(2);
            HSSFRichTextString Ammount = new HSSFRichTextString((dec.format(actionForm.getAmmount2())));
            cell = row.createCell(2);
            cell.setCellValue(Ammount);
            HSSFRichTextString Ifsccode = new HSSFRichTextString(actionForm.getIfscCode());
            cell = row.createCell(3);
            cell.setCellValue(Ifsccode);
        }
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return wb;
    }
}
