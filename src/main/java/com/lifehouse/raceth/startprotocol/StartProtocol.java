package com.lifehouse.raceth.startprotocol;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.io.File;
import java.io.IOException;
import com.lifehouse.raceth.dao.CheckpointDAO;
import com.lifehouse.raceth.dao.ParticipantDAO;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Participant;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StartProtocol {
    private ArrayList<String> titleValue = new ArrayList<String>((Arrays.asList("Номер", "Фамилия", "Имя", "Город", "Стартовый номер", "Год рождения", "Время старта", "Примечания")));

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private static XSSFCellStyle createStyleCenterAlignment(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private String calculateTime(LocalTime checkpoint1, LocalTime checkpoint2) {
        checkpoint1 = checkpoint1.minusHours(checkpoint2.getHour());
        checkpoint1 = checkpoint1.minusMinutes(checkpoint2.getMinute());
        checkpoint1 = checkpoint1.minusSeconds(checkpoint2.getSecond());
        checkpoint1 = checkpoint1.minusNanos(checkpoint2.getNano());
        return checkpoint1.toString();
    }

    public void createStartProtocol(ParticipantDAO participantDAO) throws IOException {
        XSSFWorkbook protocol = new XSSFWorkbook();
        XSSFSheet sheet = protocol.createSheet("Стартовый протокол");
        sheet.setDisplayGridlines(false); //Отключение отображения сетки
        XSSFCellStyle titleStyle = createStyleForTitle(protocol);

        //Форматирование для даты рождения
        CreationHelper createHelper = protocol.getCreationHelper();
        CellStyle styleDate = protocol.createCellStyle();
        styleDate.setDataFormat(createHelper.createDataFormat().getFormat("yyyy"));
        styleDate.setAlignment(HorizontalAlignment.CENTER);

        List<Participant> participantList= participantDAO.getAllParticipants();

        int rownum = 0;
        Cell cell;
        Row row = sheet.createRow(rownum);

        for (int i = 0; i < titleValue.size(); i++) { //Заполнение строки заголовков
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(titleValue.get(i));
            cell.setCellStyle(titleStyle);
        }

        for (Participant participant : participantList) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(participant.getId());
            cell.setCellStyle(createStyleCenterAlignment(protocol));

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(participant.getSportsman().getLastname());
            cell.setCellStyle(createStyleCenterAlignment(protocol));

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(participant.getSportsman().getName());
            cell.setCellStyle(createStyleCenterAlignment(protocol));

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(participant.getSportsman().getRegion());
            cell.setCellStyle(createStyleCenterAlignment(protocol));

            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(participant.getStartNumber());
            cell.setCellStyle(createStyleCenterAlignment(protocol));

            cell = row.createCell(5);
            cell.setCellValue(Date.from(participant.getSportsman().getBirthdate().atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime().toInstant()));
            cell.setCellStyle(styleDate); //Год рождения(в Excel передается полностью дата, отображается только год)

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(participant.getStart().getStartTime().toString());
            cell.setCellStyle(createStyleCenterAlignment(protocol));
        }

        //Настройка автоширины для столбцов
        for (int i = 0; i < titleValue.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        File file = new File("./startprotocol.xlsx");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        protocol.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
    }
}
