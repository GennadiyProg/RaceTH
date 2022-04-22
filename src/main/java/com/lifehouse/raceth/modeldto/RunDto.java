package com.lifehouse.raceth.modeldto;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Start;
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
    private Competition competition; // Todo: Need fix after update DB
    private Distance distance;
    private Group group;
    private int laps;

    public static RunDto convertToDto(Start start) {
        RunDto runDto = new RunDto();
        runDto.setId(start.getId());
        runDto.setTime(start.getStartTime());
        runDto.setCompetition(null); // Todo: После апдейта БД Соревнования больше в Start нет, есть ссылка на День соревнования, откуда можешь добраться до соревнования
        runDto.setDistance(start.getDistance());
        runDto.setGroup(start.getGroup());
        runDto.setLaps(start.getLaps());

        return runDto;
    }

    public static Start convertToStart(RunDto runDto) {
        Start start = new Start();
        start.setId(runDto.getId());
        start.setStartTime(runDto.getTime());
        // start.setCompetition(runDto.getCompetition()); // Todo: Need fix after update DB
        start.setCompetitionDay(null);
        start.setDistance(runDto.getDistance());
        start.setGroup(runDto.getGroup());
        start.setLaps(runDto.getLaps());

        return start;
    }
}
