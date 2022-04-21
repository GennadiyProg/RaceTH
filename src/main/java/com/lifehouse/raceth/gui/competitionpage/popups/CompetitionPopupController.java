package com.lifehouse.raceth.gui.competitionpage.popups;

import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.competition.PrincipalAgeCalculation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class CompetitionPopupController implements Initializable {

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField mainJudgeTextField;

    @FXML
    private TextField mainSecretaryTextField;

    @FXML
    private TextField organizerTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton yearEndRadioButton;

    @FXML
    private RadioButton currentTimeRadioButton;

    @FXML
    private ToggleGroup principalAgeCalculation;

    private TableView<Competition> competitionTable;

    public ObjectProperty<Competition> newCompetition = new SimpleObjectProperty<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearEndRadioButton.setToggleGroup(principalAgeCalculation);
        currentTimeRadioButton.setToggleGroup(principalAgeCalculation);
    }

    @FXML
    public void createOrUpdateCompetition(ActionEvent event) {
        Competition competition = buildNewCompetition();
        if (competition == null) return;
        newCompetition.setValue(competition);
        cancel(event);
    }

    private Competition buildNewCompetition() {
        RadioButton selectedToggle = (RadioButton) principalAgeCalculation.getSelectedToggle();
        LocalDate localDate = dateDatePicker.getValue();
        if (nameTextField.getText().equals("") ||
                organizerTextField.getText().equals("") ||
                locationTextField.getText().equals("") ||
                localDate == null ||
                mainJudgeTextField.getText().equals("") ||
                mainSecretaryTextField.getText().equals("") ||
                selectedToggle == null){
            return null;
        }

        Date date = new Date(java.sql.Date.valueOf(localDate).getTime());
        return new Competition(nameTextField.getText(),
                organizerTextField.getText(),
                locationTextField.getText(),
                date,
                mainJudgeTextField.getText(),
                mainSecretaryTextField.getText(),
                switch (selectedToggle.getText()){
                    case "Дата старта" -> PrincipalAgeCalculation.CURRENT_TIME;
                    case "31.12 текущего года" -> PrincipalAgeCalculation.YEAR_END;
                    default -> null;
                });
    }

    @FXML
    public void cancel(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void edit(Competition competition) {
        fillFieldsFromEntity(competition);
    }

    private void fillFieldsFromEntity(Competition competition) {
        nameTextField.setText(competition.getName());
        locationTextField.setText(competition.getLocation());
        organizerTextField.setText(competition.getOrganizer());
        dateDatePicker.setValue(convertToLocalDateViaInstant(competition.getDate()));
        mainJudgeTextField.setText(competition.getMainJudge());
        mainSecretaryTextField.setText(competition.getMainSecretary());
        principalAgeCalculation.selectToggle(determineToggle(competition.getCalculationSystemAge()));
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private RadioButton determineToggle(PrincipalAgeCalculation principal) {
        return principal == PrincipalAgeCalculation.CURRENT_TIME ? currentTimeRadioButton : yearEndRadioButton;
    }
}
