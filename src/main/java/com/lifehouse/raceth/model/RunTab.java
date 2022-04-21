package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//Класс хранит в себе старты, отображаемые в заезде
public class RunTab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    @ManyToOne
    private Competition competition;
    @OneToMany
    private Run run;
}
