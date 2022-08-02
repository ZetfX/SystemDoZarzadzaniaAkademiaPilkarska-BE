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
public class AddCoachGradeRequest {
    private final String gradeText;
    private final Date dateOfGrade;
    private final Long coachId;
}
