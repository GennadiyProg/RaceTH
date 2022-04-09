package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Sportsman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class DistancePageController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TableView<Distance> distancesTable;

    @FXML
    private TableColumn<Distance, Long> idColumn;

    @FXML
    private TableColumn<Distance, String> nameColumn;

    @FXML
    private TableColumn<Distance, Integer> lengthColumn;

    @FXML
    private TableColumn<Distance, Integer> heightColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
    }

    @FXML
    void NewDistance(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DistancePopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            DistancePopupController distancePopupController = fxmlLoader.getController();
            distancePopupController.distancesTable = distancesTable;

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void EditDistance(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DistancePopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            Distance selectedDistance = distancesTable.getSelectionModel().getSelectedItem();

            DistancePopupController distancePopupController = fxmlLoader.getController();
            distancePopupController.distancesTable = distancesTable;
            distancePopupController.startEdit(selectedDistance);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
}

