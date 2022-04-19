package com.lifehouse.raceth.gui.competitionpage.popups.impl;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.model.Competition;
import javafx.scene.control.TableView;

public class CompetitionServiceImpl {
    private TableView<Competition> competitionTable;
    private CompetitionDAO competitionDAO;

    public CompetitionServiceImpl(TableView<Competition> competitionTable, CompetitionDAO competitionDAO) {
        this.competitionTable = competitionTable;
        this.competitionDAO = competitionDAO;
    }

    public void create(){

    }

    public void edit(){

    }

    public void delete() {
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        competitionTable.getItems().removeAll(competition);
        competitionDAO.delete(competition);
    }

}
