package com.lifehouse.raceth.modeldto;

import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class GroupDto {
    private CheckBox check = new CheckBox();
    private long id; //ID
    private String name; // Название группы
    private int ageFrom;
    private int ageTo;
    private Gender gender; // Пол
    private List<Competition> competitions = new ArrayList<>();
}
