package com.lifehouse.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    @ManyToOne
    private Participant participant; //Участник по ID
    private LocalTime crossingTime;
    private int lap;
    public Checkpoint(Participant participant,LocalTime crossingTime,int lap) {
        this.participant = participant;
        this.crossingTime = crossingTime;
        this.lap = lap;
    }
}
