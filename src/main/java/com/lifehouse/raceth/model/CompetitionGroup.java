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
    private String nameGroup; // Название группы
    private String gender; // Пол
    private int age; // Возраст
}
