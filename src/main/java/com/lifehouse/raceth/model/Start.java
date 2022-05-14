package com.lifehouse.raceth.model;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Start {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalTime startTime;
    @ManyToOne
    @NotNull
    private Group group;
    @ManyToOne
    @NotNull
    private Distance distance;
    private int laps;
    @ManyToOne
    @NotNull
    private CompetitionDay competitionDay;
    @ManyToOne
    private StartTab tab;

    public void setFields(Start start) {
        this.name = start.getName();
        this.startTime = start.getStartTime();
        this.group = start.getGroup();
        this.distance = start.getDistance();
        this.laps = start.getLaps();
    }
}
