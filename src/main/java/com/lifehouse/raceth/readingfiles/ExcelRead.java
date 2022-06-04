package com.lifehouse.raceth.readingfiles;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.util.List;

import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.EncryptedDocumentException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class ExcelRead {
    private final String FILE = "src/main/java/com/lifehouse/raceth/readingfiles/example.xlsx";
    private final boolean directly = false;
    private MarksMonitorCompetitionController fileController;
    private XSSFWorkbook book;
    private XSSFSheet sheet;

    public ExcelRead(MarksMonitorCompetitionController controller) {
        fileController = controller;

    }

    public void readExcel() {
        if (directly) {
            openBookDirectly(FILE);
        } else {
            openBook(FILE);
        }

        if (book != null) {
            System.out.println("Excel Book open");
            sheet = book.getSheet("Лист1");
            if (sheet != null) {
                System.out.println("Page open");
                readCells();
            } else {
                System.out.println("Non found page");
            }
            try {
                if (!directly)
                    book.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Error reading Excel");
    }

    private void openBook(final String path) {
        try {
            File file = new File(path);
            book = (XSSFWorkbook) WorkbookFactory.create(file);


        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void openBookDirectly(final String path) {
        File file = new File(path);
        try {
            OPCPackage opcPackage = OPCPackage.open(file);
            book = new XSSFWorkbook(opcPackage);
            opcPackage.close();
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private String formatCell(Row row, Cell cell) {
        String cellValue = null;
        // Вывод значения в консоль
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    cellValue = Integer.toString((int) Math.round(cell.getNumericCellValue()));
                }
                break;
        }
        return cellValue;
    }

    private void readCells() {
        List<String> list = new ArrayList<>();
        list.add(0, "Начало");
        for (Row row : sheet) {
            list.clear();
            for (Cell cell : row) {
                list.add(formatCell(row, cell));
            }
            if (list.get(0) == null) {
                break;
            } else {
                System.out.println(list);
                fileController.addNewParticipant(list);
            }
        }
    }
}
