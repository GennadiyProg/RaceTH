package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.Gender;
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
    private Gender gender;
    private String group;
}
