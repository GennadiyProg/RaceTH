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
    private long id; //ID
    @ManyToOne
    private Chip chip; //Чип по ID
    //тут должен быть спортсмен
    @ManyToOne
    private Run run; //Забег по ID
    @ManyToOne
    @NotNull
    private RelayTeam relayTeam; //Команда эстафеты по ID
    private int relayStage; //Этап эстафеты
    @NotNull
    private int tag; //Порядковый номер в забеге

}
