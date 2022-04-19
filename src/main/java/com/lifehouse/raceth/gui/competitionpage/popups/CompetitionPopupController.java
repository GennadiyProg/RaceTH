package com.lifehouse.raceth.gui.competitionpage.popups;


import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.PrincipalAgeCalculation;
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

    private CompetitionDAO competitionDAO;
    private long changedCompetitionId = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearEndRadioButton.setToggleGroup(principalAgeCalculation);
        currentTimeRadioButton.setToggleGroup(principalAgeCalculation);
        competitionDAO = new CompetitionDAO();
    }

    @FXML
    public void createCompetition(ActionEvent event) {
        Competition newCompetition = buildNewCompetition();
        if (newCompetition == null) {
            return;
        }

        if (changedCompetitionId >= 0L) {
            competitionDAO.update(newCompetition);
            changedCompetitionId = -1;

            cancel(event);

            competitionTable.getSelectionModel().getSelectedItem().setFields(newCompetition);
            competitionTable.refresh();
            return;
        }
        newCompetition.setId(competitionTable.getItems().size());
        competitionDAO.create(newCompetition);
        competitionTable.getItems().add(newCompetition);
        competitionTable.refresh();

        cancel(event);
    }

    private Competition buildNewCompetition() {
        RadioButton selectedToggle = (RadioButton) principalAgeCalculation.getSelectedToggle();
        LocalDate localDate = dateDatePicker.getValue();
        if (localDate == null || selectedToggle == null){
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
        changedCompetitionId = competition.getId();
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
