package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditPlayerRequest {
    private final String name;
    private final String surname;
    private final String email;
    private final String gender;
    private final String telephoneNumber;
    private final Date dateOfBirth;
    private final String pesel;
    private final Long academyId;
    private final Long trainingGroupId;
}
