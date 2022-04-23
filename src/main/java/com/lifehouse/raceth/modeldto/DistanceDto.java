package com.lifehouse.raceth.modeldto;

import com.lifehouse.raceth.model.competition.Competition;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class DistanceDto {
    private CheckBox check = new CheckBox();
    private long id;
    private String location;
    private int length;
    private int height; // Набор высоты
    private List<Competition> competitions = new ArrayList<>();
}
