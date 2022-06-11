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
    @Nullable
    private int relayStage;
    private int startNumber;
    private String club;

    public Participant(String chip, Sportsman sportsman, int startNumber) {
        this.chip = chip;
        this.sportsman = sportsman;
        this.startNumber = startNumber;
    }

    public Participant(Sportsman sportsman, Start start, String club) {
        this.sportsman = sportsman;
        this.start = start;
        this.club = club;
    }
}
