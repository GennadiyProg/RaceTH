package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.CheckpointDAO;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantStartView {
    private long id;
    private LocalTime currentTime;
    private LocalTime timeOnDistance;
    private String chip;
    private int startNumber;
    private String lastname;
    private String name;
    private String group;
    private int lap;
    private int place;
    private LocalTime behindTheLeader;
    private LocalTime lapTime;

    public ParticipantStartView(long id,LocalTime currentTime,String chip,int startNumber,String lastname,String name,String group) {
        this.id = id;
        this.currentTime = currentTime;
        this.chip = chip;
        this.startNumber = startNumber;
        this.lastname = lastname;
        this.name = name;
        this.group = group;
    }

    public ParticipantStartView(
            long id,
            LocalTime currentTime,
            LocalTime timeOnDistance,
            String chip,
            int startNumber,
            String lastname,
            String name,
            String group,
            int lap,
            int place,
            LocalTime behindTheLeader) {
        this.id = id;
        this.currentTime = currentTime;
        this.timeOnDistance = timeOnDistance;
        this.chip = chip;
        this.startNumber = startNumber;
        this.lastname = lastname;
        this.name = name;
        this.group = group;
        this.lap = lap;
        this.place = place;
        this.behindTheLeader = behindTheLeader;
    }

    public static ParticipantStartView convertToView(Participant participant){
        CheckpointDAO checkpointDAO = (CheckpointDAO) Main.appContext.getBean("checkpointDAO");
        return new ParticipantStartView(
                participant.getId(),
                checkpointDAO.getCheckpointByParticipant(participant.getId()).getCrossingTime(),
                participant.getChip(),
                participant.getStartNumber(),
                participant.getSportsman().getLastname(),
                participant.getSportsman().getName(),
                participant.getStart().getGroup().getName()
        );
    }
}
