package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.util.Date;

@Data
public class CompetitionPageController {

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField mainJudgeTextField;

    @FXML
    private TextField mainSecretaryTextField;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField organizerTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private void saveCompetitionAction(ActionEvent event) {
        Date date = new Date(java.sql.Date.valueOf(dateDatePicker.getValue()).getTime());
        Competition competition = new Competition(titleTextField.getText(),
                organizerTextField.getText(),
                locationTextField.getText(),
                date,
                mainJudgeTextField.getText(),
                mainSecretaryTextField.getText());
        TmpStorage.competitions.add(competition);
        System.out.println(TmpStorage.competitions.get(0));
    }

}

