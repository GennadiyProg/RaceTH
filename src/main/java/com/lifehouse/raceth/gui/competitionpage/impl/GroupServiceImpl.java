package com.lifehouse.raceth.gui.competitionpage.impl;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.gui.competitionpage.popups.GroupPopupController;
import com.lifehouse.raceth.model.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupServiceImpl implements CompetitionPageElementService {
    private final GroupDAO groupDAO;
    private final TableView<Group> groupTable;

    public GroupServiceImpl(TableView<Group> groupTable) {
        this.groupDAO = new GroupDAO();
        this.groupTable = groupTable;
    }

    @Override
    public void create() {
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.create(newValue);
            groupTable.getItems().add(newValue);
            groupTable.refresh();
        });
    }

    @Override
    public void edit() {
        Group group = groupTable.getSelectionModel().getSelectedItem();
        if (group == null){
            return;
        }
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.edit(group);
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.update(newValue);
            groupTable.getSelectionModel().getSelectedItem().setFields(newValue);
            groupTable.refresh();
        });
    }

    @Override
    public void delete() {
        Group group = groupTable.getSelectionModel().getSelectedItem();
        if (group == null){
            return;
        }
        groupTable.getItems().removeAll(group);
        groupDAO.delete(group);
    }

    private GroupPopupController createGroupPopup(){
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return null;
        }
        GroupPopupController groupPopupController = fxmlLoader.getController();
        groupPopupController.setGroupTable(groupTable);
        return groupPopupController;
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/GroupPopup.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return fxmlLoader;
    }
}
