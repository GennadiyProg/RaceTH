package com.lifehouse.raceth.logic.competitionpage.impl;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.logic.competitionpage.popups.GroupPopupController;
import com.lifehouse.raceth.model.view.GroupView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupServiceImpl implements CompetitionPageElementService {
    private final GroupDAO groupDAO;
    private final TableView<GroupView> groupTable;
    private GroupView lastEditedGroup;

    public GroupServiceImpl(TableView<GroupView> groupTable) {
        this.groupDAO = (GroupDAO) Main.appContext.getBean("groupDAO");
        this.groupTable = groupTable;
    }

    @Override
    public void create() {
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.create(newValue);
            lastEditedGroup = GroupView.convertToView(newValue);
            groupTable.getItems().add(lastEditedGroup);
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
            newValue.setId(groupView.getId());
            newValue.setCompetitions(groupView.getCompetitions());
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
        return fxmlLoader.getController();
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/competition/GroupPopup.fxml"));
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
