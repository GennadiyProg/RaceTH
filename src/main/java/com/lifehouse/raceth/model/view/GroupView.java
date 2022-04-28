package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupView {
    private CheckBox checkBox = new CheckBox();
    private long id; //ID
    private String name; // Название группы
    private int ageFrom;
    private int ageTo;
    private Gender gender; // Пол
    private List<Competition> competitions = new ArrayList<>();

    public static GroupView convertToView(Group group) {
        return new GroupView(
                new CheckBox(),
                group.getId(),
                group.getName(),
                group.getAgeFrom(),
                group.getAgeTo(),
                group.getGender(),
                group.getCompetitions()
        );
    }

    public static Group convertToModel(GroupView groupView) {
        return new Group(
                groupView.getId(),
                groupView.getName(),
                groupView.getAgeFrom(),
                groupView.getAgeTo(),
                groupView.getGender(),
                groupView.getCompetitions()
        );
    }

    public void setFields(GroupView groupView) {
        this.name = groupView.getName();
        this.gender = groupView.getGender();
        this.ageFrom = groupView.getAgeFrom();
        this.ageTo = groupView.getAgeTo();
    }
}
