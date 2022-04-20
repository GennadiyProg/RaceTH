package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class ClubPageController {
    @FXML
    private AnchorPane main_pane;


    @FXML
    void NewClub(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Club/ClubsPopup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
    @FXML

    void EditClub(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Club/ClubsPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
}