package edu.uni.lodz.system.akademia.pilkarska.application.services;


import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddCoachGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddPlayerGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllCoachesGradesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.NotGradedCoachesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.NotGradedPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.PlayerGradesResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.grade.CoachGrade;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.grade.GradeService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.grade.PlayerGrade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradesMainService {

    private final GradeService gradeService;


    public PlayerGrade addPlayerGrade(AddPlayerGradeRequest addPlayerGradeRequest) {
        return gradeService.addPlayerGrade(addPlayerGradeRequest);
    }

    public NotGradedPlayersResponse getNotGradedPlayers(Long userId) {
        return gradeService.getNotGradedPlayers(userId);
    }

    public NotGradedCoachesResponse getNotGradedCoaches(Long userId) {
        return gradeService.getNotGradedCoaches(userId);
    }

    public CoachGrade addCoachGrade(AddCoachGradeRequest addCoachGradeRequest) {
        return gradeService.addCoachGrade(addCoachGradeRequest);
    }

    public PlayerGradesResponse getPlayerGrades(Long userId) {
        return gradeService.getAllPlayerGradesForPlayer(userId);
    }


    public AllCoachesGradesResponse getAllCoachesGrades(Long academyId) {
        return new AllCoachesGradesResponse(gradeService.getAllCoachesGradesByAcademyId(academyId));
    }
}
