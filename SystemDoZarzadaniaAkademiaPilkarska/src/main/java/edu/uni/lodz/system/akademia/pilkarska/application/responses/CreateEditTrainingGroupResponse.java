package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateEditTrainingGroupResponse {
    private TrainingGroup trainingGroup;
}
