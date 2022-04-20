package com.lifehouse.raceth.model.competition;

public enum GroupButtons {
    CREATEGROUPBUTTON,
    EDITGROUPBUTTON,
    DELETEGROUPBUTTON;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
