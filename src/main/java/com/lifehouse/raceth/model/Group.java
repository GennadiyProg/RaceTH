package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Group\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int ageFrom;
    private int ageTo;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "competition_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitions = new ArrayList<>();

    public Group(String name, int ageFrom, int ageTo, Gender gender) {
        this.name = name;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = gender;
    }

    public void setFields(Group group) {
        this.name = group.getName();
        this.gender = group.getGender();
        this.ageFrom = group.getAgeFrom();
        this.ageTo = group.getAgeTo();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
