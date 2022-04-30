package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    private String name; // Название группы
    private int ageFrom;
    private int ageTo;
    private Gender gender; // Пол
    @ManyToMany(mappedBy = "groups")
    private List<Competition> competitions = new ArrayList<>();

    public Group(String name, int ageFrom, int ageTo, Gender gender) {
        this.name = name;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = gender;
    }

    public void setFields(Group group) {
        this.name = group.getName();
        this.gender = group.getGender();
        this.ageFrom = group.getAgeFrom();
        this.ageTo = group.getAgeTo();
    }

    @Override
    public String toString() {
        return this.name;
    }
}