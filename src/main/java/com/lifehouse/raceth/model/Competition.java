package com.lifehouse.raceth.model;

import javafx.print.Collation;
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
}
