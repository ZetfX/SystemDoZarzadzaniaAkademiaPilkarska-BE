package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditEventRequest {

    private final String eventTitle;
    private final Date eventDate;
    private final Long startTime;
    private final Long endTime;
    private final Long trainingGroupId;
    private final Long objectId;
    private final Long organizerId;
    private final Long academyId;
}
