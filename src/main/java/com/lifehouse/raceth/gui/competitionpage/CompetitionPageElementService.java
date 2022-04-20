package com.lifehouse.raceth.gui.competitionpage;

import javafx.scene.control.TableView;

import java.io.IOException;

public interface CompetitionPageElementService {
    void create(TableView<?> table);
    void edit(TableView<?> table);
    void delete(TableView<?> table);
}
