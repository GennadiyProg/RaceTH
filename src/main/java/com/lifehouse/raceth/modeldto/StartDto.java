package com.lifehouse.raceth.modeldto;

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
public class StartDto {
    private CheckBox check = new CheckBox();
    private long id;
    private String name;
    private LocalTime startTime;
    private Group group;
    private Distance distance;
    private int laps;
    private CompetitionDay competitionDay;
    private StartTab tab;

    public static StartDto convertToDto(Start start) {
        StartDto startDto = new StartDto();
        startDto.setId(start.getId());
        startDto.setStartTime(start.getStartTime());
        startDto.setCompetitionDay(null);
        startDto.setDistance(start.getDistance());
        startDto.setGroup(start.getGroup());
        startDto.setLaps(start.getLaps());

        return startDto;
    }

    public static Start convertToStart(StartDto startDto) {
        Start start = new Start();
        start.setId(startDto.getId());
        start.setStartTime(startDto.getStartTime());
        start.setCompetitionDay(startDto.getCompetitionDay());
        start.setCompetitionDay(null);
        start.setDistance(startDto.getDistance());
        start.setGroup(startDto.getGroup());
        start.setLaps(startDto.getLaps());

        return start;
    }
}
