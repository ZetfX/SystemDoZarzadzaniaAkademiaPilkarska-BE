package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class CoachTrainingGroupResponse {
    private TrainingGroup trainingGroup;
}
