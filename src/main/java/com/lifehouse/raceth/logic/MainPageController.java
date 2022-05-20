package com.lifehouse.raceth.logic;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.logic.startpage.StartPageController;
import com.lifehouse.raceth.model.CompetitionDay;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class MainPageController implements Initializable {
/*
    ⠄⠄⠄⢰⣧⣼⣯⠄⣸⣠⣶⣶⣦⣾⠄⠄⠄⠄⡀⠄⢀⣿⣿⠄⠄⠄⢸⡇⠄⠄
⠄⠄⠄⣾⣿⠿⠿⠶⠿⢿⣿⣿⣿⣿⣦⣤⣄⢀⡅⢠⣾⣛⡉⠄⠄⠄⠸⢀⣿⠄
⠄⠄⢀⡋⣡⣴⣶⣶⡀⠄⠄⠙⢿⣿⣿⣿⣿⣿⣴⣿⣿⣿⢃⣤⣄⣀⣥⣿⣿⠄
⠄⠄⢸⣇⠻⣿⣿⣿⣧⣀⢀⣠⡌⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠿⠿⣿⣿⣿⠄
⠄⢀⢸⣿⣷⣤⣤⣤⣬⣙⣛⢿⣿⣿⣿⣿⣿⣿⡿⣿⣿⡍⠄⠄⢀⣤⣄⠉⠋⣰
⠄⣼⣖⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣿⣿⣿⣿⣿⢇⣿⣿⡷⠶⠶⢿⣿⣿⠇⢀⣤
⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⣿⣿⣿⡇⣿⣿⣿⣿⣿⣿⣷⣶⣥⣴⣿⡗
⢀⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠄
⢸⣿⣦⣌⣛⣻⣿⣿⣧⠙⠛⠛⡭⠅⠒⠦⠭⣭⡻⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠄
⠘⣿⣿⣿⣿⣿⣿⣿⣿⡆⠄⠄⠄⠄⠄⠄⠄⠄⠹⠈⢋⣽⣿⣿⣿⣿⣵⣾⠃⠄
⠄⠘⣿⣿⣿⣿⣿⣿⣿⣿⠄⣴⣿⣶⣄⠄⣴⣶⠄⢀⣾⣿⣿⣿⣿⣿⣿⠃⠄⠄
⠄⠄⠈⠻⣿⣿⣿⣿⣿⣿⡄⢻⣿⣿⣿⠄⣿⣿⡀⣾⣿⣿⣿⣿⣛⠛⠁⠄⠄⠄
⠄⠄⠄⠄⠈⠛⢿⣿⣿⣿⠁⠞⢿⣿⣿⡄⢿⣿⡇⣸⣿⣿⠿⠛⠁⠄⠄⠄⠄⠄
⠄⠄⠄⠄⠄⠄⠄⠉⠻⣿⣿⣾⣦⡙⠻⣷⣾⣿⠃⠿⠋⠁⠄⠄⠄⠄⠄⢀⣠⣴
⣿⣿⣿⣶⣶⣮⣥⣒⠲⢮⣝⡿⣿⣿⡆⣿⡿⠃⠄⠄⠄⠄⠄⠄⠄⣠⣴⣿⣿⣿
 */

    @FXML
    public AnchorPane competitionPage;
//    @FXML
//    public AnchorPane run_page;
    @FXML
    public AnchorPane relay_run_page;
    @FXML
    public AnchorPane club_page;
    @FXML
    public AnchorPane marks_monitor_page;//Вехнее меню выбора
    @FXML
    private Label activeCompetitionLabel;
    @FXML
    private AnchorPane firstAnch;

    private AnchorPane pane = null;

    private StartDAO startDAO;
    private StartPageController startPageController;

    @FXML
    private CompetitionPageController competitionPageController;

    public static Competition currentCompetition;
    public static CompetitionDay currentCompetitionDay;

    public MainPageController() {
        currentCompetitionDay = new CompetitionDay(1, new Date(), null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");

        activeCompetitionLabel.setText("Неопределено");
        competitionPageController.getValue().addListener((observable, oldValue, newValue) -> activeCompetitionLabel.setText(newValue));

        defineStartPageController();

        frontCompetitionPage(null);
    }

    @FXML
    private void frontCompetitionPage(ActionEvent event) {
        competitionPage.toFront();
    }
    @FXML
    private void frontStartPage(ActionEvent event) {
        if (currentCompetition == null) {
            alertNoSelectedCompetition();
            return;
        }

        startPageController.onMounted();
        pane.toFront();
    }
    @FXML
    private void frontRelayRunPage(ActionEvent event) {
        if (currentCompetition == null) {
            alertNoSelectedCompetition();
            return;
        }

        relay_run_page.toFront();
    }
    @FXML
    private void frontClubPage(ActionEvent event) {
        club_page.toFront();
    }
    @FXML
    private void frontMarksMonitorPage(ActionEvent event) {
        if (currentCompetition == null) {
            alertNoSelectedCompetition();
            return;
        }

        marks_monitor_page.toFront();
    }

    private void alertNoSelectedCompetition() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ошибка");
        alert.setContentText("Не выбрано текущее соревнование");
        alert.show();
    }

    public void defineStartPageController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/start/StartPage.fxml"));
        try {
            pane = loader.load();
            pane.managedProperty().bind(pane.visibleProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startPageController = loader.getController();
        firstAnch.getChildren().add(pane);
    }
}
