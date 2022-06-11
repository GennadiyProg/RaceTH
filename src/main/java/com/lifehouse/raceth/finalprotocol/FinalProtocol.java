package com.lifehouse.raceth.finalprotocol;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import com.lifehouse.raceth.dao.CheckpointDAO;
import com.lifehouse.raceth.logic.marksmonitorpage.MarksMonitorCompetitionController;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Participant;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class FinalProtocol {

    private CheckpointDAO checkpointDAO;
    private ArrayList<String> titleValue = new ArrayList<String>((Arrays.asList("Место", "Фамилия", "Имя", "Год рождения", "Город", "Клуб", "Стартовый номер", "Финиш", "Категория")));

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(font);
        return style;
    }

    private String calculateTime(LocalTime checkpoint1, LocalTime checkpoint2) {
        checkpoint1 = checkpoint1.minusHours(checkpoint2.getHour());
        checkpoint1 = checkpoint1.minusMinutes(checkpoint2.getMinute());
        checkpoint1 = checkpoint1.minusSeconds(checkpoint2.getSecond());
        checkpoint1 = checkpoint1.minusNanos(checkpoint2.getNano());
        return checkpoint1.toString();
    }

    public void createFinalProtocol(CheckpointDAO checkpointDAO) throws IOException {
        XSSFWorkbook protocol = new XSSFWorkbook();
        XSSFSheet sheet = protocol.createSheet("Метки");
        sheet.setDisplayGridlines(false); //Отключение отображения сетки
        XSSFCellStyle titleStyle = createStyleForTitle(protocol);

        //Форматирование для даты рождения
        CreationHelper createHelper = protocol.getCreationHelper();
        CellStyle styleDate = protocol.createCellStyle();
        styleDate.setDataFormat(createHelper.createDataFormat().getFormat("yyyy"));

        List<Checkpoint> checkpointList = checkpointDAO.getAllCheckpoints();
        int lapCount = checkpointList.get(0).getParticipant().getStart().getLaps();

        //Заполнение списка заголовков столбцов
        for (int i = 0; i < lapCount-1; i++) titleValue.add(i + 7, "Круг " + (i + 1));

        int rownum = 0;
        Cell cell;
        Row row = sheet.createRow(rownum);


        for (int i = 0; i < titleValue.size(); i++) { //Заполнение строки заголовков
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(titleValue.get(i));
            cell.setCellStyle(titleStyle);
        }

        List<Checkpoint> checkpointOfParticipant;
        for (int i = 1; i < checkpointList.size(); i++) {

        }

        List<Long> ignoreList = new ArrayList<Long>();
        for (Checkpoint checkpoint : checkpointList) {
            if (ignoreList.contains(checkpoint.getParticipant().getId())) {
                continue;
            } else {
                ignoreList.add(checkpoint.getParticipant().getId());
            }
            rownum++;
            row = sheet.createRow(rownum);
            //Все чекпоинты этого участника
            List<Checkpoint> participantCheckpoints = checkpointDAO.getPartisipantsCheckpoint(checkpoint.getParticipant());

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(1); //Место

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(checkpoint.getParticipant().getSportsman().getLastname()); //Фамилия

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(checkpoint.getParticipant().getSportsman().getName()); //Имя

            cell = row.createCell(3);
            cell.setCellValue(Date.from(checkpoint.getParticipant().getSportsman().getBirthdate().atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime().toInstant()));
            cell.setCellStyle(styleDate); //Год рождения(в Excel передается полностью дата, отображается только год)

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(checkpoint.getParticipant().getSportsman().getRegion()); //Город

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(checkpoint.getParticipant().getClub()); //Клуб

            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue(checkpoint.getParticipant().getStartNumber()); //Стартовый номер

            for (int i = 0; i < participantCheckpoints.size()-1; i++) {
                cell = row.createCell(7+i, CellType.STRING);
                cell.setCellValue(calculateTime(participantCheckpoints.get(i).getCrossingTime(),checkpoint.getParticipant().getStart().getStartTime()));
            }

            cell = row.createCell(7+participantCheckpoints.size()-1, CellType.STRING);
            cell.setCellValue(calculateTime(participantCheckpoints.get(lapCount-1).getCrossingTime(),checkpoint.getParticipant().getStart().getStartTime())); //Финишное время(относительное)

            cell = row.createCell(8+participantCheckpoints.size()-1, CellType.STRING);
            cell.setCellValue(checkpoint.getParticipant().getStart().getGroup().getName()); //Группа

        }

        //Настройка автоширины для столбцов
        for (int i = 0; i < titleValue.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        File file = new File("src/main/java/com/lifehouse/raceth/finalprotocol/finalprotocol.xlsx");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        protocol.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }
}
