package com.lifehouse.raceth.model.view;

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
    private String chip;
    private int startNumber;
    private String lastname;
    private String name;
    private Gender gender;
    private String group;

    public static ParticipantStartView convertToView(Participant participant){
        CheckpointDAO checkpointDAO = new CheckpointDAO();
        return new ParticipantStartView(
                participant.getId(),
                checkpointDAO.getCheckpointByParticipant(participant.getId()).getCrossingTime(),
                participant.getChip(),
                participant.getStartNumber(),
                participant.getSportsman().getLastname(),
                participant.getSportsman().getName(),
                participant.getSportsman().getGender(),
                participant.getStart().getGroup().getName()
        );
    }
}
