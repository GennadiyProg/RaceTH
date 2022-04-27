package com.lifehouse.raceth.gui.search.impl;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.gui.search.SearchEngine;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CompetitionTableSearchImpl implements SearchEngine {
    private final TableView<Competition> competitionTable;
    private final CompetitionDAO competitionDAO;
    private final TextField competitionTf;

    private final List<Competition> competitions = new ArrayList<>();

    public CompetitionTableSearchImpl(TableView<Competition> competitionTable, CompetitionDAO competitionDAO, TextField competitionTF) {
        this.competitionTable = competitionTable;
        this.competitionDAO = competitionDAO;
        this.competitionTf = competitionTF;
        search();
    }

    @Override
    public void search() {
        competitionTf.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        competitions.addAll(competitionDAO.getAllCompetitions());
                    } else {
                        competitions.clear();
                    }
                }
        );
        competitionTf.textProperty().addListener((observable, oldValue, newValue) -> competitionTable.setItems(
                competitions
                        .stream()
                        .filter(c -> c.getName().contains(newValue.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );
    }
}
