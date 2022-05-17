package com.lifehouse.raceth.logic;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.logic.startpage.StartPageController;
import com.lifehouse.raceth.model.CompetitionDay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        competitionPage.toFront();
    }
    @FXML
    private void handleButtonAction2(ActionEvent event) {
        startPageController.onMounted();
        pane.toFront();
    }
    @FXML
    private void handleButtonAction3(ActionEvent event) {
        relay_run_page.toFront();
    }
    @FXML
    private void handleButtonAction6(ActionEvent event) {
        club_page.toFront();
    }
    @FXML
    private void handleButtonAction7(ActionEvent event) { marks_monitor_page.toFront();}

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
