package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.model.PrincipalAgeCalculation;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class CompetitionPageController implements Initializable {


    @FXML
    private TableView<Competition> competitionTable;

    @FXML
    private TableColumn<Competition, Date> dateColumn;

    @FXML
    private TableColumn<Competition, String> locationColumn;

    @FXML
    private TableColumn<Competition, String> mainJudgeColumn;

    @FXML
    private TableColumn<Competition, String> mainSecretaryColumn;

    @FXML
    private TableColumn<Competition, String> nameColumn;

    @FXML
    private TableColumn<Competition, String> organizerColumn;

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
    private RadioButton currentTimeRadioButton;

    @FXML
    private RadioButton yearEndRadioButton;

    private ToggleGroup principalAgeCalculation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        mainJudgeColumn.setCellValueFactory(new PropertyValueFactory<>("mainJudge"));
        mainSecretaryColumn.setCellValueFactory(new PropertyValueFactory<>("mainSecretary"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        organizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer"));

        principalAgeCalculation = new ToggleGroup();
        currentTimeRadioButton.setToggleGroup(principalAgeCalculation);
        yearEndRadioButton.setToggleGroup(principalAgeCalculation);
    }

    @FXML
    private void saveCompetitionAction(ActionEvent event) throws UnsupportedEncodingException {
        Date date = new Date(java.sql.Date.valueOf(dateDatePicker.getValue()).getTime());
        RadioButton selectedTogle = (RadioButton) principalAgeCalculation.getSelectedToggle();
        if (selectedTogle == null){
            return;
        }
        Competition competition = new Competition(titleTextField.getText(),
                organizerTextField.getText(),
                locationTextField.getText(),
                date,
                mainJudgeTextField.getText(),
                mainSecretaryTextField.getText(),
                switch (selectedTogle.getText()){
                    case "Дата старта" -> PrincipalAgeCalculation.CURRENT_TIME;
                    case "31.12 текущего года" -> PrincipalAgeCalculation.YEAR_END;
                    default -> null;
                });
        TmpStorage.competitions.add(competition);
        System.out.println(TmpStorage.competitions.get(0).getName());

        ObservableList<Competition> competitions = competitionTable.getItems();
        competitions.add(competition);
    }

}

