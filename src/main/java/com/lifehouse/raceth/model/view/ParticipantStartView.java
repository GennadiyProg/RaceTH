package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.CheckpointDAO;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.parser.Part;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public ParticipantStartView(long id, LocalTime currentTime) {
        this.id = id;
        this.startNumber = -1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        this.currentTime = LocalTime.parse(LocalTime.now().format(formatter));
    }

    public ParticipantStartView(long id, LocalTime currentTime, String chip, int startNumber, String lastname, String name, String group) {
        this.id = id;
        this.currentTime = currentTime;
        this.chip = chip;
        this.startNumber = startNumber;
        this.lastname = lastname;
        this.name = name;
        this.group = group;
    }

    public void attachParticipant(Participant participant) {
        if (participant == null) {
            this.name = null;
            this.lastname = null;
            this.chip = null;
            this.startNumber = -1;
            return;
        }

        this.name = participant.getSportsman().getName();
        this.lastname = participant.getSportsman().getLastname();
        this.chip = participant.getChip();
        this.startNumber = participant.getStartNumber();
    }

    public static ParticipantStartView convertToView(Participant participant) {
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
