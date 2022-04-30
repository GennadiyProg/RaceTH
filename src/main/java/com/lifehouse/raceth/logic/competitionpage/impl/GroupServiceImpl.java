package com.lifehouse.raceth.logic.competitionpage.impl;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.logic.competitionpage.popups.GroupPopupController;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.view.GroupView;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GroupServiceImpl implements CompetitionPageElementService {
    private final GroupDAO groupDAO;
    private final TableView<GroupView> groupTable;
    private final ChangeListener<Boolean> checkboxListener;
    private GroupView lastEditedGroup;

    public GroupServiceImpl(TableView<GroupView> groupTable) {
        this.groupDAO = new GroupDAO();
        this.groupTable = groupTable;
        checkboxListener = ((observableList, oldStatus, newStatus) -> {
            Competition curCompetition = CompetitionPageController.currentCompetition;
            attachCompetition(lastEditedGroup, curCompetition, newStatus);
        });
    }

    @Override
    public void create() {
        GroupPopupController controller = createGroupPopup();
        if (controller == null) return;
        controller.getNewGroup().addListener((observable, oldValue, newValue) -> {
            groupDAO.create(newValue);
            lastEditedGroup = GroupView.convertToView(newValue);
            lastEditedGroup.getCheckBox().selectedProperty().addListener(checkboxListener);
            groupTable.getItems().add(lastEditedGroup);
            groupTable.refresh();
        });
    }

    private void attachCompetition(GroupView groupView, Competition competition, Boolean status) {
        if (competition != null) {
            if (status){
                List<Competition> competitionList = groupView.getCompetitions();
                if (!competitionList.contains(competition)) {
                    competitionList.add(competition);
                }
            } else {
                groupView.getCompetitions().remove(
                        groupView.getCompetitions().stream()
                                .filter((el) -> el.getId() == competition.getId())
                                .findFirst().orElse(null)
                );
                groupDAO.update(GroupView.convertToModel(groupView));
            }
        } else {
            groupView.getCheckBox().selectedProperty().removeListener(checkboxListener);
            groupView.getCheckBox().setSelected(false);
            groupView.getCheckBox().selectedProperty().addListener(checkboxListener);
            new Alert(Alert.AlertType.WARNING, "Не указано текущее соревнование").show();
        }
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
        return fxmlLoader.getController();
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
