package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Run;
import com.lifehouse.raceth.model.Sportsman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private TableView<Checkpoint> checkpointTable;

    @FXML
    private TableView<Run> runTable;

    @FXML
    private TableColumn<Run, String> groupColumn;

    @FXML
    private TableColumn<Run, String> startTimeColumn;

    @FXML
    private TableColumn<Run, String> lapColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
    }

    @FXML
    void addingGroup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MarksGroupPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
}
