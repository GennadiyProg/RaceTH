package com.lifehouse.raceth.logic.relayrunpage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class PeopleInRelayRunPopupController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private DatePicker birth;
    @FXML
    private Button cancelButt;
    @FXML
    private TextField chip;
    @FXML
    private TextField city;
    @FXML
    private ComboBox<?> club;
    @FXML
    private ToggleGroup gender;
    @FXML
    private TextField name;
    @FXML
    private TextField patronic;
    @FXML
    private ComboBox<?> rank;
    @FXML
    private Button saveButt;
    @FXML
    private Button saveButt1;
    @FXML
    private TextField startNumber;
    @FXML
    private TextField surname;

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
        try{                                                           //Отмена
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }
}


