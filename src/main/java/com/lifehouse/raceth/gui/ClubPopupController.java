package com.lifehouse.raceth.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class ClubPopupController implements Initializable {
    @FXML
    private AnchorPane main_pane;
    @FXML
    private TextField clubName;
    @FXML
    private TextField coach;
    @FXML
    private ChoiceBox<String> region;
    @FXML
    private Button regionButt;

    public void initialize(URL var1, ResourceBundle var2) {
        region.getItems().addAll("item1", "item2", "item3");
    }

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


