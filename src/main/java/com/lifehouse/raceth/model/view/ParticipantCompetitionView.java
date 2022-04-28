package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantCompetitionView {
    private long id;
    private String name;
    private Gender gender;
    private String chip;
    private int tag;
    private Date birthdate;
    private String region;

    public static ParticipantCompetitionView convertToView(Participant participant){
        return new ParticipantCompetitionView(
                participant.getId(),
                participant.getSportsman().getName(),
                participant.getSportsman().getGender(),
                participant.getChip(),
                participant.getTag(),
                participant.getSportsman().getBirthdate(),
                participant.getSportsman().getRegion()
        );
    }
}
