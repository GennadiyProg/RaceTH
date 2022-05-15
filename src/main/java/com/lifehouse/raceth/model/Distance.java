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
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;
    private int length;
    private int height;
    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "competition_distance",
            joinColumns = @JoinColumn(name = "distance_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitions = new ArrayList<>();

    public Distance(String location, int length, int height) {
        this.location = location;
        this.length = length;
        this.height = height;
    }

    public void setFields(Distance distance) {
        this.location = distance.getLocation();
        this.length = distance.getLength();
        this.height = distance.getHeight();
    }

    @Override
    public String toString() {
        return location + " " + length;
    }
}
