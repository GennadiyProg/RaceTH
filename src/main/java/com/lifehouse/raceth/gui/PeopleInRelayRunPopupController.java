package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class PeopleInRelayRunPopupController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField commandName;

    @FXML
    void Saving(ActionEvent event) {
        try{
            System.out.println(commandName.getText());                 //Добавление участника
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{                                                           //Отмена
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }
}


