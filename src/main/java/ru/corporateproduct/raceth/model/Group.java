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
public class Group {
    @Id
    private long id; //ID
    private String nameGroup; // Название группы
    private String gender; // Пол
    private int age; // Возраст
}
