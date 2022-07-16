package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.Coach;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateEditCoachResponse {
    private Coach coach;
}
