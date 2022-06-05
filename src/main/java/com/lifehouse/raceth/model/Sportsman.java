package com.lifehouse.raceth.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sportsman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String patronymic;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @NotNull
    private String region;
    private String discharge;

    public Sportsman(String name, String lastname, String patronymic, LocalDate birthdate, Gender gender, String region) {
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.region = region;
    }

    public Sportsman(String name, String lastname, String patronymic, LocalDate birthdate, Gender gender, String region, String discharge) {
        this.name = name;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.region = region;
        this.discharge = discharge;
    }
}
