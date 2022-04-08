package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Data
public class RelayRunPageController implements Initializable {
    @FXML
    private AnchorPane main_pane;


    // Кнопки на панели команды
    @FXML
    void NewCommand(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CommandPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
    @FXML
    void EditCommand(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CommandPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void DeleteCommand(ActionEvent event) {

    }

    @FXML
    void NewPeople(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPeopleInRelayRunPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void EditPeople(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPeopleInRelayRunPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void DeletePeople(ActionEvent event) {

    }

    @FXML
    void NewRelay(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RelayRunCreateRelayRunPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

