package com.lifehouse.raceth.model.competition;

public enum CompetitionPageButton {
    COMPETITIONBUTTON,
    GROUPBUTTON,
    DISTANCEBUTTON;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
