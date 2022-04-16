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
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;

    @FXML
    private  TabPane tabPane;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Tab tab = new Tab("Tab_");

            Label label = new Label("This is Tab");

            // add label to the tab
            tab.setContent(label);

            tabPane.getTabs().add(tab);

            // create a tab which when pressed creates a new tab
            Tab newtab = new Tab();

            EventHandler<Event> event = e -> {
                if (newtab.isSelected())
                {
                    // create Tab
                    Tab tab1 = new Tab("Tab_");

                    // create a label
                    Label label1 = new Label("This is Tab: ");

                    // add label to the tab
                    tab1.setContent(label1);

                    // add tab
                    tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab1);

                    // select the last tab
                    tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
                }
            };

            // set event handler to the tab
            newtab.setOnSelectionChanged(event);

            tabPane.getTabs().add(newtab);
        }
}