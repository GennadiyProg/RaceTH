package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DateTimeStringConverter;
import lombok.Data;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class MarksGroupPopupController {

    @FXML
    private Button addInTable;

    @FXML
    private TextField circle;

    @FXML
    private TextField time;


    @FXML
    void AddToTable(ActionEvent event)  {

    }

    @FXML
    void Saving(ActionEvent event) {
        try{

            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("can't loading");
        }
    }

}


