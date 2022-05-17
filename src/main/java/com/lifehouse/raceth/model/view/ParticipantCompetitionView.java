package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantCompetitionView {
    private long id;
    private String name;
    private String lastname;
    private String patronymic;
    private Gender gender;
    private String chip;
    private int startNumber;
    private LocalDate birthdate;
    private String region;
    private String group;

    public static ParticipantCompetitionView convertToView(Participant participant){
        return new ParticipantCompetitionView(
                participant.getId(),
                participant.getSportsman().getName(),
                participant.getSportsman().getLastname(),
                participant.getSportsman().getPatronymic(),
                participant.getSportsman().getGender(),
                participant.getChip(),
                participant.getStartNumber(),
                participant.getSportsman().getBirthdate(),
                participant.getSportsman().getRegion(),
//                participant.getStart().getGroup().getName()
                "hello"
        );
    }

    public void setFields(ParticipantCompetitionView participant){
        this.name = participant.getName();
        this.lastname = participant.getLastname();
        this.patronymic = participant.getPatronymic();
        this.gender = participant.getGender();
        this.chip = participant.getChip();
        this.startNumber = participant.getStartNumber();
        this.birthdate = participant.getBirthdate();
        this.region = participant.getRegion();
        this.group = participant.getGroup();
    }
}
