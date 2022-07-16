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
public class CreateEditExpenseRequest {
    private final BigDecimal expenseValue;
    private final String expenseTitle;
    private final Date dateOfExpense;
    private final Long academyId;
}
