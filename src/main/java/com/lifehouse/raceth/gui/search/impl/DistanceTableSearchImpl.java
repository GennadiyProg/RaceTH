package com.lifehouse.raceth.gui.search.impl;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.search.SearchEngine;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.viewmodel.DistanceView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistanceTableSearchImpl implements SearchEngine {
    private final TableView<DistanceView> distanceTable;
    private final DistanceDAO distanceDAO;
    private final TextField distanceTf;
    private final List<DistanceView> distances = new ArrayList<>();

    public DistanceTableSearchImpl(TableView<DistanceView> distanceTable, DistanceDAO distanceDAO, TextField distanceTf) {
        this.distanceTable = distanceTable;
        this.distanceDAO = distanceDAO;
        this.distanceTf = distanceTf;
    }

    @Override
    public void search() {
        distanceTf.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        distances.addAll(distanceDAO.getAllDistanceViews());
                    } else {
                        distances.clear();
                    }
                }
        );
        distanceTf.textProperty().addListener((observable, oldValue, newValue) -> distanceTable.setItems(
                distances
                        .stream()
                        .filter(c -> c.getLocation().contains(newValue.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );
    }
}
