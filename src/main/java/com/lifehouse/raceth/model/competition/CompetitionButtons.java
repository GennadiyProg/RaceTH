package com.lifehouse.raceth.model.competition;

public enum CompetitionButtons {
    CREATECOMPETITIONBUTTON,
    EDITCOMPETITIONBUTTON,
    DELETECOMPETITIONBUTTON;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
