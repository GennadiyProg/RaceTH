package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceView {
    private CheckBox checkBox = new CheckBox();
    private long id;
    private String location;
    private int length;
    private int height; // Набор высоты
    private List<Competition> competitions = new ArrayList<>();

    public static DistanceView convertToView(Distance distance) {
        return new DistanceView(
                new CheckBox(),
                distance.getId(),
                distance.getLocation(),
                distance.getLength(),
                distance.getHeight(),
                distance.getCompetitions()
        );
    }

    public static Distance convertToModel(DistanceView distanceView) {
        return new Distance(
                distanceView.getId(),
                distanceView.getLocation(),
                distanceView.getLength(),
                distanceView.getHeight(),
                distanceView.getCompetitions()
        );
    }

    public void setFields(DistanceView distance) {
        this.location = distance.getLocation();
        this.length = distance.getLength();
        this.height = distance.getHeight();
    }
}