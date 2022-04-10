package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class StartPageController implements Initializable {

    @FXML
    public AnchorPane competitionPage;
    @FXML
    public AnchorPane run_page;
    @FXML
    public AnchorPane relay_run_page;
    @FXML
    public AnchorPane distance_page;
    @FXML
    public AnchorPane club_page;
    @FXML
    public AnchorPane group_page;
    @FXML
    public AnchorPane marks_monitor_page;//Вехнее меню выбора
    @FXML
    private Label activeCompetitionLabel;

    @FXML
    private CompetitionPageController competitionPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        run_page.managedProperty().bind(run_page.visibleProperty());

        activeCompetitionLabel.setText("Неопределено");
        competitionPageController.getValue().addListener((observable, oldValue, newValue) -> activeCompetitionLabel.setText(newValue));
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        competitionPage.toFront();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        run_page.toFront();
    }

    @FXML
    private void handleButtonAction3(ActionEvent event) {
        relay_run_page.toFront();
    }

    @FXML
    private void handleButtonAction4(ActionEvent event) {
        marks_monitor_page.toFront();
    }

    @FXML
    private void handleButtonAction5(ActionEvent event) {
        distance_page.toFront();
    }

    @FXML
    private void handleButtonAction6(ActionEvent event) {
        club_page.toFront();
    }

    @FXML
    private void handleButtonAction7(ActionEvent event) {
        group_page.toFront();
    }
}
