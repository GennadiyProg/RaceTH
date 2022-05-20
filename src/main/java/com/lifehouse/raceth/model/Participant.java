package com.lifehouse.raceth.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
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
    @Nullable
    private RelayTeam relayTeam;
    private int relayStage;
    private int startNumber;

    @ManyToOne
    private Group group;

    public Participant(String chip, Sportsman sportsman, int startNumber) {
        this.chip = chip;
        this.sportsman = sportsman;
        this.startNumber = startNumber;
    }
}
