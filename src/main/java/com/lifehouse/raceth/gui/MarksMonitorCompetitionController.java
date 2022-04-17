package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.rfid.RFID;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Data
public class MarksMonitorCompetitionController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField lastNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void newLastNumber() {

//        lastNumber.setText("tag");
        Runnable task = () -> lastNumber.setText(RFID.getTag());
//        CompletableFuture<String> reader = CompletableFuture.supplyAsync(newSupplier);
        CompletableFuture.runAsync(task);

    }
}