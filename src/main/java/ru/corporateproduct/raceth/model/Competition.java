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
public class Competition {
    @Id
    private long id; //ID
    private String nameCompetition; //Название соревнования
    private String dateCompetition; //Дата соревнования(Fixme: хз какого типо его сделать, наверно надо какой-то отдельный тип)
    private String organizer; //Организатор
}
