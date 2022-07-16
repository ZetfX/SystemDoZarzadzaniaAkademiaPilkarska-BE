package edu.uni.lodz.system.akademia.pilkarska.application.services;

import edu.uni.lodz.system.akademia.pilkarska.application.responses.CoachPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CoachTrainingGroupResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoachMainService {

    private final CoachService coachService;

    public CoachPlayersResponse getCoachPlayers(Long userId) {
        return coachService.getCoachPlayers(userId);
    }


    public CoachTrainingGroupResponse getCoachTrainingGroup(Long userId) {
        return coachService.getCoachTrainingGroup(userId);
    }
}
