package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditTrainingGroupRequest {
    private final String groupName;
    private final String monthlySubscriptionTitle;
    private final BigDecimal monthlySubscriptionValue;
    private final Long academyId;
}
