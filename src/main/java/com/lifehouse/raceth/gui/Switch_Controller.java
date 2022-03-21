package com.lifehouse.raceth.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Switch_Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Switch_Controller() {
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartUp_Page.fxml"));
        AnchorPane pain = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(pain);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SportReg_Page.fxml"));
        AnchorPane pain = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(pain);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowPartic_Page.fxml"));
        AnchorPane pain = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(pain);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene4(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SportReg_Page.fxml"));
        AnchorPane pain = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(pain);
        stage.setScene(scene);
        stage.show();
    }


    //Вехнее меню выбора
    @FXML
    private ComboBox<String> ActiveComp;

    private String[] Competition = {"Марафон 2022", "Кросс лето", "Соревнование весна"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActiveComp.setValue(Competition[0]);
        ActiveComp.getItems().addAll(Competition);
    }


    @FXML
    private AnchorPane firstAnch, secAnch, run_anch, run_page;
    @FXML
    private Button sorevButt, zabegButt, esta;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        secAnch.toFront();
    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {
        run_page.toFront();
    }
}
