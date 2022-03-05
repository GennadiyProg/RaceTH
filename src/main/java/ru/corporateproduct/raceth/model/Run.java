package ru.corporateproduct.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Run {
    @Id
    private long id; //ID
    private String dateRun; //Дата забега(Fixme: хз какого типо его сделать, наверно надо какой-то отдельный тип)
    private Competition competitionById; //Соревнование по ID
    private Distance distanceByID; //Дистанция по ID
    private Group groupById; //Группа по ID
}
