package com.lifehouse.raceth.readingfiles;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;


import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.ss.util.CellReference;

import org.apache.poi.EncryptedDocumentException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelRead{
    private  final  String  FILE     = "src/main/java/com/lifehouse/raceth/readingfiles/example.xlsx";
    private  final  boolean directly = false;
    private MarksMonitorCompetitionController fileController;
    private  XSSFWorkbook   book;
    private XSSFSheet sheet;

    public ExcelRead(MarksMonitorCompetitionController controller)
    {
        fileController = controller;

    }

    public void readExcel() {
        if (directly)
            openBookDirectly(FILE);
        else
            openBook(FILE);
        if (book != null) {
            System.out.println ("Excel Book open");
            sheet = book.getSheet("Лист1");
            if (sheet != null) {
                System.out.println ("Page open");
                readCells();
            } else {
                System.out.println ("Non found page");
            }
            try {
                if (!directly)
                    book.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println ("Error reading Excel");
    }

    private void openBook(final String path)
    {
        try {
            File file = new File(path);
            book = (XSSFWorkbook) WorkbookFactory.create(file);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openBookDirectly(final String path)
    {
        File file = new File(path);
        try {
            OPCPackage pkg = OPCPackage.open(file);
            book = new XSSFWorkbook(pkg);
            pkg.close();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printCell(XSSFRow row, XSSFCell cell)
    {
        DataFormatter formatter = new DataFormatter();
        System.out.print(" : ");

        String text = formatter.formatCellValue(cell);
        System.out.print(text + " / ");

        // Вывод значения в консоль
        switch (cell.getCellType()) {
            case STRING:
                System.out.println(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    System.out.println(cell.getDateCellValue());
                else
                    System.out.println(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                System.out.println(cell.getBooleanCellValue());
                break;
            case FORMULA:
                System.out.println(cell.getCellFormula());
                break;
            case BLANK:
                System.out.println();
                break;
            default:
                System.out.println();
        }
    }

    private void readCells()
    {
        // Определение граничных строк обработки
        int rowStart = Math.min(  0, sheet.getFirstRowNum());
        int rowEnd   = Math.max(100, sheet.getLastRowNum ());

        for (int rw = rowStart; rw < rowEnd; rw++) {
            XSSFRow row = sheet.getRow(rw);
            if (row == null) {
                // System.out.println("row '" + rw + "' is not created");
                continue;
            }
            short minCol = row.getFirstCellNum();
            short maxCol = row.getLastCellNum();
            for(short col = minCol; col < maxCol; col++) {
                // @SuppressWarnings("deprecation")
                // XSSFCell cell = rw.getCell(col, XSSFRow.RETURN_BLANK_AS_NULL);
                XSSFCell cell = row.getCell(col);
                if (cell == null) {
                    // System.out.println("cell '" + col + "' is not created");
                    continue;
                }
                printCell(row, cell);
            }
        }
    }
}
