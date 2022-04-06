package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class CommandPopupController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button cancelButt;

    @FXML
    private Button saveButt;

    @FXML
    private TextField commandName;

    @FXML
    void Saving(ActionEvent event) {
        try{
            System.out.println(commandName.getText()); //Сохранения названия
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


