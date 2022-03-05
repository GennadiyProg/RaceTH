package ru.corporateproduct.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.util.StringHelper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {
    @Id
    private long id; //ID
    private Participant participantById; //Участник по ID
    private String crossingTime; //Время пересечения(Fixme: хз какого типо его сделать, наверно надо какой-то отдельный тип)
    //Todo: Здесь должно быть поле с номером круга, наверное
}
