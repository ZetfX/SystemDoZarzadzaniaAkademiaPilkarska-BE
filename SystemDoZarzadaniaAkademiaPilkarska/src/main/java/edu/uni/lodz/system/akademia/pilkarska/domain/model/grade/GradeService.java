package edu.uni.lodz.system.akademia.pilkarska.domain.model.grade;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddCoachGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddPlayerGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.NotGradedCoachesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.NotGradedPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.PlayerGradesResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.Coach;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.CoachService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.PlayerService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class GradeService {

    private final PlayerService playerService;
    private final CoachService coachService;
    private final PlayerGradeRepository playerGradeRepository;
    private final CoachGradeRepository coachGradeRepository;
    private final UserService userService;

    public PlayerGrade addPlayerGrade(AddPlayerGradeRequest addPlayerGradeRequest) {

        Player player = playerService.getPlayerById(addPlayerGradeRequest.getPlayerId());

        return playerGradeRepository.save(new PlayerGrade(addPlayerGradeRequest.getGradeText(), addPlayerGradeRequest.getDateOfGrade(), player));

    }

    public NotGradedPlayersResponse getNotGradedPlayers(Long userId) {
        Set<Player> players = coachService.getCoachPlayers(userId).getPlayers();
        Set<PlayerGrade> gradesFromCurrentMonth = playerGradesFromCurrentMonth(getAllPlayerGrades());


        for (PlayerGrade playerGrade : gradesFromCurrentMonth) {
                players.removeIf(player ->  playerGrade.getPlayer().getId().equals(player.getId()));
        }

        return new NotGradedPlayersResponse(players);
    }


    private Set<PlayerGrade> getAllPlayerGrades() {
        return new HashSet<>((playerGradeRepository.findAll()));
    }

    private Set<PlayerGrade> playerGradesFromCurrentMonth(Set<PlayerGrade> allPlayerGrades) {
        Set<PlayerGrade> gradesFromCurrentMonth = new HashSet<PlayerGrade>();
        LocalDate now = LocalDate.now();

        for (PlayerGrade playerGrade : allPlayerGrades) {
            if (isDateMonthAndYearEqualsToNow(parseDateToLocalDate(playerGrade.getDateOfGrade()),now))
            {
                gradesFromCurrentMonth.add(playerGrade);
            }
        }

        return gradesFromCurrentMonth;
    }


    public Set<CoachGrade> getAllCoachesGradesByAcademyId(Long academyId)
        {
        HashSet<CoachGrade> allCoachGrades = new HashSet<>((coachGradeRepository.findAll()));
        HashSet <CoachGrade> coachGradesByAcademy = new HashSet<>();

        for(CoachGrade grade: allCoachGrades )
        {
            if (Objects.requireNonNull(grade.getCoach().getUser().getAcademy()).getId().equals(academyId))
            {
                coachGradesByAcademy.add(grade);
            }

        }

        return coachGradesByAcademy;
    }

    private Set<CoachGrade> getAllCoachGrades(){
       return new HashSet<>((coachGradeRepository.findAll()));
    }

    private Set<CoachGrade> coachGradesFromCurrentMonth(Set<CoachGrade> allCoachGrades) {
        Set<CoachGrade> gradesFromCurrentMonth = new HashSet<CoachGrade>();
        LocalDate now = LocalDate.now();

        for (CoachGrade coachGrade : allCoachGrades) {
            if (isDateMonthAndYearEqualsToNow(parseDateToLocalDate(coachGrade.getDateOfGrade()),now))
            {
                gradesFromCurrentMonth.add(coachGrade);
            }
        }

        return gradesFromCurrentMonth;
    }

    private LocalDate parseDateToLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private boolean isDateMonthAndYearEqualsToNow(LocalDate date, LocalDate now) {

        return date.getMonth() == now.getMonth() && date.getYear() == now.getYear();
    }

    public NotGradedCoachesResponse getNotGradedCoaches(Long userId) {
        Set<Coach> coaches = coachService.getPlayerCoaches(userId);
        Set<CoachGrade> gradesFromCurrentMonth = coachGradesFromCurrentMonth(getAllCoachGrades());


        for (CoachGrade coachGrade : gradesFromCurrentMonth) {
            coaches.removeIf(coach ->  coachGrade.getCoach().getId().equals(coach.getId()));
        }

        return new NotGradedCoachesResponse(coaches);
    }

    public CoachGrade addCoachGrade(AddCoachGradeRequest addCoachGradeRequest) {

        Coach coach = coachService.getCoachById(addCoachGradeRequest.getCoachId());

        return coachGradeRepository.save(new CoachGrade(addCoachGradeRequest.getGradeText(), addCoachGradeRequest.getDateOfGrade(), coach));

    }

    public PlayerGradesResponse getAllPlayerGradesForPlayer(Long userId){
        User user = userService.getUserById(userId);
        Player player = playerService.getPlayerByUser(user);

        return new PlayerGradesResponse(playerGradeRepository.getPlayerGradesByPlayer(player).orElseThrow(() -> new NotFoundException("Nie znaleziono ocen dla tego zawodnika")));
    }



}
