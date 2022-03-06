package ru.corporateproduct.raceth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.util.StringHelper;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //ID
    @ManyToOne
    private Participant participant; //Участник по ID
    private Date crossingTime;
    //Todo: Здесь должно быть поле с номером круга, наверное
}
