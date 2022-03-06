package ru.corporateproduct.raceth.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    private Date dateRun; // Надо сделать время до секунд
    @ManyToOne()
    @NotNull
    private Competition competition; //Соревнование по ID
    @ManyToOne()
    @NotNull
    private Distance distance; //Дистанция по ID
    @ManyToOne()
    @NotNull
    private CompetitionGroup group; //Группа по ID
}
