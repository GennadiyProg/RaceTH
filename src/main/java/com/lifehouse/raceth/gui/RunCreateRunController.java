package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class RunCreateRunController {
    @FXML
    private AnchorPane main_pane;


    @FXML
    void Saving(ActionEvent event) {
        try{

            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }
}


