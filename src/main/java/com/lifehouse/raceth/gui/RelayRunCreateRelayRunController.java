package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class RelayRunCreateRelayRunController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button cancelButt;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<?> distance;

    @FXML
    private ComboBox<?> group;

    @FXML
    private Button saveButt;

    @FXML
    private ComboBox<?> sorev;


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


