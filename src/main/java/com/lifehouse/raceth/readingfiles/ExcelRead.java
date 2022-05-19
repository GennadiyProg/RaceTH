package com.lifehouse.raceth.readingfiles;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    private String printCell(Row row, Cell cell)
    {
        LocalDate date = LocalDate.now();
        String forlist=null;
        Double forlistnumber = null;
        DataFormatter formatter = new DataFormatter();

        // Вывод значения в консоль
        switch (cell.getCellType()) {
            case STRING:
                forlist = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    date = cell.getLocalDateTimeCellValue().toLocalDate();
                    forlist = date.toString();
                }
                else{
                    forlistnumber = cell.getNumericCellValue();
                    forlist = forlistnumber.toString();}
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
        return forlist;
    }

    private void readCells()
    {
        String forlist;
        List<String> list = new ArrayList<>();
        list.add(0, "Начало");
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            list.clear();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                forlist = printCell(row, cell);
                list.add(forlist);
            }
            String element0 =(String) list.get(0);
            if (element0 == null){
                break;
            }
            else{
                System.out.println(list);
            }
        }
    }
}
