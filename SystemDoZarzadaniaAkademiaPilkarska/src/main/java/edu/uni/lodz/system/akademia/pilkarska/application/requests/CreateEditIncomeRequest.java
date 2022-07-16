package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateEditIncomeRequest {
    private final BigDecimal incomeValue;
    private final String incomeTitle;
    private final Date dateOfIncome;
    private final Long academyId;
}
