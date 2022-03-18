package com.lifehouse.raceth.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

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
    private Date birthdate;
    @NotNull
    @Enumerated (value = EnumType.STRING)
    private Gender gender;
    @NotNull
    private String region;
    // МБ разряд
}
