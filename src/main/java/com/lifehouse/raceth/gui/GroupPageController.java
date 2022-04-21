package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.gui.competitionpage.popups.GroupPopupController;
import com.lifehouse.raceth.model.Group;
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
    private TableView<Group> competitionGroupsTable;

    @FXML
    private TableColumn<Group, Long> idColumn;

    @FXML
    private TableColumn<Group, String> nameColumn;

    @FXML
    private TableColumn<Group, Gender> genderColumn;

    @FXML
    private TableColumn<Group, Integer> ageFromColumn;

    @FXML
    private TableColumn<Group, Integer> ageToColumn;
    private GroupDAO groupDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageFromColumn.setCellValueFactory(new PropertyValueFactory<>("ageFrom"));
        ageToColumn.setCellValueFactory(new PropertyValueFactory<>("ageTo"));

        groupDAO = new GroupDAO();

        competitionGroupsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Group> groups = competitionGroupsTable.getItems();
        groups.addAll(groupDAO.getAllGroups());
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
            groupPopupController.setGroupTable(competitionGroupsTable);

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
            groupPopupController.setGroupTable(competitionGroupsTable);
            List<Group> groups = competitionGroupsTable.getSelectionModel().getSelectedItems();

            if (groups.size() != 1) {
                throw new Exception();
                // todo: тут показ ошибки о том что выбранно слишком много элементов для редактирования
            }

            groupPopupController.edit(groups.get(0));

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    private void RemoveRows(ActionEvent event) {
        List<Group> groups = competitionGroupsTable.getSelectionModel().getSelectedItems();
        competitionGroupsTable.getItems().removeAll(groups);

    }
}

