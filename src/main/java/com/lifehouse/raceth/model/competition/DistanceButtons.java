package com.lifehouse.raceth.model.competition;

public enum DistanceButtons {
    CREATEDISTANCEBUTTON,
    EDITDISTANCEBUTTON,
    DELETEDISTANCEBUTTON;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
