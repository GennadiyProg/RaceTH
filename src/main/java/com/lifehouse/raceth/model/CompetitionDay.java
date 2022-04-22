package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
}
