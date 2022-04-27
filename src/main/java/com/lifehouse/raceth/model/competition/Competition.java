package com.lifehouse.raceth.model.competition;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

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


    private Date fromDate; //Дата соревнования
    private Date toDate; //Дата соревнования
    private String mainJudge; //Главный судья
    private String mainSecretary; //Главный секретарь
    private PrincipalAgeCalculation calculationSystemAge; //Принцип расчета возраста участника

    @ManyToMany
    @JoinTable(name = "competition_distance",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "distance_id"))
    private List<Distance> distances = new ArrayList<>(); // Дистанции, объявленные в этом соревновании
    @ManyToMany
    @JoinTable(name = "competition_group",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups = new ArrayList<>(); // Группы (категории), объявленные в этом соревновании

    public Competition(String name,
                       String organizer,
                       String location,
                       Date fromDate,
                       Date toDate,
                       String mainJudge,
                       String mainSecretary,
                       PrincipalAgeCalculation calculationSystemAge) {
        this.name = name;
        this.organizer = organizer;
        this.location = location;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.mainJudge = mainJudge;
        this.mainSecretary = mainSecretary;
        this.calculationSystemAge = calculationSystemAge;
    }

    public String getDate() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd");
        return formater.format(fromDate) + " - " + formater.format(toDate);
    }

    public void setFields(Competition competition){
        this.name = competition.name;
        this.organizer = competition.organizer;
        this.location = competition.location;
        this.fromDate = competition.fromDate;
        this.toDate = competition.toDate;
        this.mainJudge = competition.mainJudge;
        this.mainSecretary = competition.mainSecretary;
        this.calculationSystemAge = competition.calculationSystemAge;
    }
}
