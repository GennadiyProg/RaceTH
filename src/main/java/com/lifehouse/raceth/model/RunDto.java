package com.lifehouse.raceth.model;

import com.lifehouse.raceth.model.competition.Competition;
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
public class RunDto {
    private CheckBox check = new CheckBox();
    private long id;
    private LocalTime time;
    private Competition competition;
    private Distance distance;
    private Group group;
    private int laps;

    public static RunDto convertToDto(Run run) {
        RunDto runDto = new RunDto();
        runDto.setId(run.getId());
        runDto.setTime(run.getTime());
        runDto.setCompetition(run.getCompetition());
        runDto.setDistance(run.getDistance());
        runDto.setGroup(run.getGroup());
        runDto.setLaps(run.getLaps());

        return runDto;
    }

    public static Run convertToRun(RunDto runDto) {
        Run run = new Run();
        run.setId(runDto.getId());
        run.setTime(runDto.getTime());
        run.setCompetition(runDto.getCompetition());
        run.setDistance(runDto.getDistance());
        run.setGroup(runDto.getGroup());
        run.setLaps(runDto.getLaps());

        return run;
    }
}
