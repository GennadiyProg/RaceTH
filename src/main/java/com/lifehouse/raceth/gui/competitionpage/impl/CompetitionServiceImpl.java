package com.lifehouse.raceth.gui.competitionpage.impl;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.gui.competitionpage.popups.CompetitionPopupController;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CompetitionServiceImpl implements CompetitionPageElementService {
    private final CompetitionDAO competitionDAO;
    private final TableView<Competition> competitionTable;

    public CompetitionServiceImpl(TableView<Competition> competitionTable) {
        this.competitionDAO = new CompetitionDAO();
        this.competitionTable = competitionTable;
    }

    @Override
    public void create() {
        CompetitionPopupController controller = createCompetitionPopup();
        if (controller == null) return;
        controller.getNewCompetition().addListener((observable, oldValue, newValue) -> {
            competitionDAO.create(newValue);
            competitionTable.getItems().add(newValue);
            competitionTable.refresh();
        });
    }

    @Override
    public void edit() {
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }
        CompetitionPopupController controller = createCompetitionPopup();
        if (controller == null) return;

        controller.edit(competition);
        controller.getNewCompetition().addListener((observable, oldValue, newValue) -> {
            competitionDAO.update(newValue);
            competitionTable.getSelectionModel().getSelectedItem().setFields(newValue);
            competitionTable.refresh();
        });
    }

    @Override
    public void delete() {
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }
        competitionTable.getItems().removeAll(competition);
        competitionDAO.delete(competition);
    }

    private CompetitionPopupController createCompetitionPopup(){
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return null;
        }
        CompetitionPopupController competitionPopupController = fxmlLoader.getController();
        competitionPopupController.setCompetitionTable(competitionTable);
        return competitionPopupController;
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CompetitionPopup.fxml"));
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
