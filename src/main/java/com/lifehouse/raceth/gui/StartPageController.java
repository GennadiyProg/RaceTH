package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    //Вехнее меню выбора
    @FXML
    private ComboBox<String> ActiveComp;

    private String[] Competition = {"Марафон 2022", "Кросс лето", "Соревнование весна"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActiveComp.setValue("Марафон 2022");
        ActiveComp.getItems().addAll(Competition);
    }

    @FXML
    private RunPageController runPageController;

    @FXML
    private CompetitionPageController competitionPageController;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        competitionPageController.getMain_pane().toFront();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        runPageController.getMain_pane().toFront();
    }
}
