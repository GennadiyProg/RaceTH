package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.dao.DAO;
import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class CompetitionPageController {


    @FXML
    private DatePicker date_dp;

    @FXML
    private TextField location_tf;

    @FXML
    private TextField mainJudge_tf;

    @FXML
    private TextField mainSecretary_tf;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField organizer_tf;

    @FXML
    private TextField title_tf;

    @FXML
    private void saveCompetitionAction(ActionEvent event) {
        LocalDate localDate = date_dp.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Competition competition = new Competition(title_tf.getText(),
                organizer_tf.getText(),
                location_tf.getText(),
                date,
                mainJudge_tf.getText(),
                mainSecretary_tf.getText());
        TmpStorage.competitions.add(competition);
        System.out.println("hello");
    }
}

