package com.lifehouse.raceth.model.viewmodel;

import com.lifehouse.raceth.model.*;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartView {
    private CheckBox checkBox = new CheckBox();
    private long id;
    private String name;
    private LocalTime startTime;
    private Group group;
    private Distance distance;
    private int laps;
    private CompetitionDay competitionDay;
    private StartTab tab;

    public static StartView convertToView(Start start) {
         return new StartView(
                new CheckBox(),
                start.getId(),
                start.getName(),
                start.getStartTime(),
                start.getGroup(),
                start.getDistance(),
                start.getLaps(),
                start.getCompetitionDay(),
                start.getTab()
        );
    }

    public static Start convertToModel(StartView startView) {
        return new Start(
                startView.getId(),
                startView.getName(),
                startView.getStartTime(),
                startView.getGroup(),
                startView.getDistance(),
                startView.getLaps(),
                startView.getCompetitionDay(),
                startView.getTab()
        );
    }
}
