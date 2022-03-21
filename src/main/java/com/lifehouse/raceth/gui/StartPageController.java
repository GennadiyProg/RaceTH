package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    @FXML
    public AnchorPane competition_page;
    @FXML
    public AnchorPane run_page;
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
    private void handleButtonAction(ActionEvent event) {
        competition_page.toFront();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        run_page.toFront();
    }
}