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

    public CompetitionServiceImpl() {
        this.competitionDAO = new CompetitionDAO();
    }

    @Override
    public void create(TableView<?> table) {
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return;
        }
        CompetitionPopupController competitionPopupController = fxmlLoader.getController();
        competitionPopupController.setCompetitionTable((TableView<Competition>) table);
    }

    @Override
    public void edit(TableView<?> table) {
        TableView<Competition> competitionTable = (TableView<Competition>)table;
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }

        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return;
        }
        CompetitionPopupController competitionPopupController = fxmlLoader.getController();
        competitionPopupController.edit(competition);
        competitionPopupController.setCompetitionTable(competitionTable);
    }

    @Override
    public void delete(TableView<?> table) {
        TableView<Competition> competitionTable = (TableView<Competition>) table;
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        competitionTable.getItems().removeAll(competition);
        competitionDAO.delete(competition);
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CompetitionPopup.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return fxmlLoader;
    }
}
