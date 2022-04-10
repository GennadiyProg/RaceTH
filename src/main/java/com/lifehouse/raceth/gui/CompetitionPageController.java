package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Competition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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

    private final StringProperty value = new SimpleStringProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        mainJudgeColumn.setCellValueFactory(new PropertyValueFactory<>("mainJudge"));
        mainSecretaryColumn.setCellValueFactory(new PropertyValueFactory<>("mainSecretary"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        organizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer"));
    }

    @FXML
    public void makeCompetitionCurrent(ActionEvent event){
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }
        value.setValue(competition.getName());
    }

    @FXML
    void createCompetition(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = loadResources();

        CompetitionPopupController competitionPopupController = fxmlLoader.getController();
        competitionPopupController.setCompetitionTable(competitionTable);
    }

    @FXML
    void editCompetition(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = loadResources();

        CompetitionPopupController competitionPopupController = fxmlLoader.getController();
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }
        competitionPopupController.edit(competition);
        competitionPopupController.setCompetitionTable(competitionTable);
    }

    private FXMLLoader loadResources() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CompetitionPopup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return fxmlLoader;
    }

    @FXML
    void deleteCompetition(ActionEvent event) {
        List<Competition> competitions = competitionTable.getSelectionModel().getSelectedItems();
        competitionTable.getItems().removeAll(competitions);
    }
}

