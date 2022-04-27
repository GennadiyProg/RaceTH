package com.lifehouse.raceth.gui.competitionpage.impl;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.gui.competitionpage.popups.GroupPopupController;
import com.lifehouse.raceth.model.viewmodel.GroupView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupServiceImpl implements CompetitionPageElementService {
    private final GroupDAO groupDAO;
    private final TableView<GroupView> groupTable;

    public GroupServiceImpl(TableView<GroupView> groupTable) {
        this.groupDAO = new GroupDAO();
        this.groupTable = groupTable;
    }

    @Override
    public void create() {
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.create(newValue);
            groupTable.getItems().add(GroupView.convertToView(newValue));
            groupTable.refresh();
        });
    }

    @Override
    public void edit() {
        GroupView groupView = groupTable.getSelectionModel().getSelectedItem();
        if (groupView == null){
            return;
        }
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.edit(groupView);
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.update(newValue);
            groupTable.getSelectionModel().getSelectedItem().setFields(GroupView.convertToView(newValue));
            groupTable.refresh();
        });
    }

    @Override
    public void delete() {
        GroupView groupView = groupTable.getSelectionModel().getSelectedItem();
        if (groupView == null){
            return;
        }
        groupTable.getItems().removeAll(groupView);
        groupDAO.delete(GroupView.convertToModel(groupView));
    }

    private GroupPopupController createGroupPopup(){
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return null;
        }
        GroupPopupController groupPopupController = fxmlLoader.getController();
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
