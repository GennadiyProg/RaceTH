package ru.corporateproduct.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    private long id; //ID
    private Chip chipById; //Чип по ID
    //тут должен быть спортсмен
    private Run runById; //Забег по ID
    private RelayTeam relayTeamById; //Команда эстафеты по ID
    private int relayStage; //Этап эстафеты
    private int tag; //Порядковый номер в забеге


}
