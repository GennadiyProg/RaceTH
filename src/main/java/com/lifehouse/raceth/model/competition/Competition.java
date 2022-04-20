package com.lifehouse.raceth.model.competition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    private String name; //Название соревнования
    private String organizer; //Организатор
    private String location; //Место проведения
    private Date date; //Дата соревнования
    private String mainJudge; //Главный судья
    private String mainSecretary; //Главный секретарь
    private PrincipalAgeCalculation calculationSystemAge; //Принцип расчета возраста участника

    public Competition(String name, String organizer, String location, Date date, String mainJudge, String mainSecretary, PrincipalAgeCalculation calculationSystemAge) {
        this.name = name;
        this.organizer = organizer;
        this.location = location;
        this.date = date;
        this.mainJudge = mainJudge;
        this.mainSecretary = mainSecretary;
        this.calculationSystemAge = calculationSystemAge;
    }

    public void setFields(Competition competition){
        this.name = competition.name;
        this.organizer = competition.organizer;
        this.location = competition.location;
        this.date = competition.date;
        this.mainJudge = competition.mainJudge;
        this.mainSecretary = competition.mainSecretary;
        this.calculationSystemAge = competition.calculationSystemAge;
    }
}
