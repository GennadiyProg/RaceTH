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
public class CompetitionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    private String name; // Название группы
    private Gender gender; // Пол
    private int ageFrom;
    private int ageTo;

    @Override
    public String toString() {
        return this.name;
    }

    public void setFields(CompetitionGroup group) {
        this.name = group.getName();
        this.gender = group.getGender();
        this.ageFrom = group.getAgeFrom();
        this.ageTo = group.getAgeTo();
    }
}
