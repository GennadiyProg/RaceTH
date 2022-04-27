package com.lifehouse.raceth.gui.search.impl;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.gui.search.SearchEngine;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.viewmodel.GroupView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupTableSearchImpl implements SearchEngine {
    private final TableView<GroupView> groupTable;
    private final GroupDAO groupDAO;
    private final TextField groupTf;

    private final List<GroupView> groups = new ArrayList<>();

    public GroupTableSearchImpl(TableView<GroupView> groupTable, GroupDAO groupDAO, TextField groupTf) {
        this.groupTable = groupTable;
        this.groupDAO = groupDAO;
        this.groupTf = groupTf;
        search();
    }

    @Override
    public void search() {
        groupTf.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        groups.addAll(groupDAO.getAllGroupViews());
                    } else {
                        groups.clear();
                    }
                }
        );
        groupTf.textProperty().addListener((observable, oldValue, newValue) -> groupTable.setItems(
                groups
                        .stream()
                        .filter(c -> c.getName().contains(newValue.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );
    }
}
