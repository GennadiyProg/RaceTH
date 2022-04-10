package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Gender;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Data
public class GroupPageController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TableView<CompetitionGroup> competitionGroupsTable;

    @FXML
    private TableColumn<CompetitionGroup, Long> idColumn;

    @FXML
    private TableColumn<CompetitionGroup, String> nameColumn;

    @FXML
    private TableColumn<CompetitionGroup, Gender> genderColumn;

    @FXML
    private TableColumn<CompetitionGroup, Integer> ageFromColumn;

    @FXML
    private TableColumn<CompetitionGroup, Integer> ageToColumn;
    private CompetitionGroupDAO competitionGroupDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageFromColumn.setCellValueFactory(new PropertyValueFactory<>("ageFrom"));
        ageToColumn.setCellValueFactory(new PropertyValueFactory<>("ageTo"));

        competitionGroupDAO = new CompetitionGroupDAO();

        competitionGroupsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<CompetitionGroup> competitionGroups = competitionGroupsTable.getItems();
        competitionGroups.addAll(competitionGroupDAO.GetAllGroups());
    }

    @FXML
    void NewGroup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/GroupPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            GroupPopupController groupPopupController = fxmlLoader.getController();
            groupPopupController.setCompetitionGroupsTable(competitionGroupsTable);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    void EditGroup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/GroupPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            GroupPopupController groupPopupController = fxmlLoader.getController();
            groupPopupController.setCompetitionGroupsTable(competitionGroupsTable);
            List<CompetitionGroup> competitionGroups = competitionGroupsTable.getSelectionModel().getSelectedItems();

            if (competitionGroups.size() != 1) {
                throw new Exception();
                // todo: тут показ ошибки о том что выбранно слишком много элементов для редактирования
            }

            groupPopupController.Edit(competitionGroups.get(0));

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    private void RemoveRows(ActionEvent event) {
        List<CompetitionGroup> competitionGroups = competitionGroupsTable.getSelectionModel().getSelectedItems();
        competitionGroupsTable.getItems().removeAll(competitionGroups);

    }
}

