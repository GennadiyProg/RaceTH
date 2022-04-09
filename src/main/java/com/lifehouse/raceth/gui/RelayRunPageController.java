package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.RelayTeam;
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
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class RelayRunPageController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TableView<RelayTeam> relayTeamsTable;

    @FXML
    private TableColumn<RelayTeam, Integer> relayTeamIdColumn;

    @FXML
    private TableColumn<RelayTeam, String> relayTeamNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        relayTeamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        relayTeamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    // Кнопки на панели команды
    @FXML
    void addRelayTeam(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CommandPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            CommandPopupController controller = fxmlLoader.getController();
            controller.setRelayTeamTable(relayTeamsTable);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
    @FXML
    void editRelayTeam(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CommandPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            CommandPopupController controller = fxmlLoader.getController();
            controller.setRelayTeamTable(relayTeamsTable);
            RelayTeam relayTeam = relayTeamsTable.getSelectionModel().getSelectedItem();
            if (relayTeam == null) return;
            controller.edit(relayTeam);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void newPeople(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPeopleInRelayRunPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void editPeople(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPeopleInRelayRunPopup.fxml"));
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

