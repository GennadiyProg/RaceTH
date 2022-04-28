package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private Date date;
    @ManyToOne
    @NotNull
    private Competition competition;

    public CompetitionDay(Date date, Competition competition) {
        this.date = date;
        this.competition = competition;
    }

    @Override
    public String toString() {
        /*LocalDate normalizedDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();*/
        return competition.getName() + " " + date.getDate() + " число";
    }
}
