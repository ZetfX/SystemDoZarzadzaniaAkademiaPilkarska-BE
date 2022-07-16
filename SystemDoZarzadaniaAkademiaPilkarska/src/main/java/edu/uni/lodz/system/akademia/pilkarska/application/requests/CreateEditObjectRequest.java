package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditObjectRequest {
    private final String placeName;
    private final String street;
    private final String city;
    private final String zipCode;
    private final Long academyId;
}
