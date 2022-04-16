package com.lifehouse.raceth.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;

    @FXML
    private  TabPane tabPane;

    @FXML
    private AnchorPane main_pane;

    @FXML
    void AddingGroup(ActionEvent event) {
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

            // create a label
            Label label = new Label("This is Tab: ");


            // add label to the tab
            tab.setContent(label);

            // add tab
            tabPane.getTabs().add(tab);

            // create a tab which
            // when pressed creates a new tab
            Tab newtab = new Tab();


            EventHandler<Event> event = new EventHandler<Event>() {

                public void handle(Event e)
                {
                    if (newtab.isSelected())
                    {

                        // create Tab
                        Tab tab = new Tab("Tab_");

                        // create a label
                        Label label = new Label("This is Tab: ");



                        // add label to the tab
                        tab.setContent(label);

                        // add tab
                        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);

                        // select the last tab
                        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
                    }
                }
            };

            // set event handler to the tab
            newtab.setOnSelectionChanged(event);

            // add newtab
            tabPane.getTabs().add(newtab);
        };
};
