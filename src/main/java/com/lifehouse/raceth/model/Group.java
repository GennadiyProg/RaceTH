package com.lifehouse.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
