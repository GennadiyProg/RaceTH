package com.lifehouse.raceth.model;

import javafx.print.Collation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    private String nameCompetition; //Название соревнования
    private String dateCompetition; //Дата соревнования(Fixme: хз какого типо его сделать, наверно надо какой-то отдельный тип)
    private String organizer; //Организатор

//    @OneToMany(mappedBy = "competition")
//    private Collection<Run> runs;
}
