package com.lifehouse.raceth.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String chip;
    @ManyToOne
    private Sportsman sportsman;
    @ManyToOne
    private Start start;
    @ManyToOne
    @NotNull
    private RelayTeam relayTeam;
    private int relayStage;
    @NotNull
    private int startNumber;

    public Participant(String chip, Sportsman sportsman, int startNumber) {
        this.chip = chip;
        this.sportsman = sportsman;
        this.startNumber = startNumber;
    }
}
