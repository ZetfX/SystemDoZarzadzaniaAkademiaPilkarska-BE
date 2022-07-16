package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditCoachRequest {
    private final String name;
    private final String surname;
    private final String email;
    private final String telephoneNumber;
    private final Long trainingGroupId;
    private final Long academyId;
}
