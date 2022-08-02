package edu.uni.lodz.system.akademia.pilkarska.domain.model.coach;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.generators.PassGenerator;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditCoachRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllCoachesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CoachPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CoachTrainingGroupResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditCoachResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.senders.EmailSender;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.enums.UserRole;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.PlayerService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroupService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoachService {
    private final AcademyService academyService;
    private final CoachRepository coachRepository;
    private final TrainingGroupService trainingGroupService;
    private final UserService userService;
    private final PlayerService playerService;

    public AllCoachesResponse getCoachesByAcademy(Long academyId)
    {
        Academy academy = academyService.getAcademyById(academyId);

        return new AllCoachesResponse(coachRepository.getCoachesByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak trenerów w tej akademii")));
    }

    public CreateEditCoachResponse createCoach(CreateEditCoachRequest createEditCoachRequest){
       TrainingGroup trainingGroup =trainingGroupService.getTrainingGroupById(createEditCoachRequest.getTrainingGroupId());
       Academy academy = academyService.getAcademyById(createEditCoachRequest.getAcademyId());
       User user =new User(createEditCoachRequest.getName(),createEditCoachRequest.getSurname(),createEditCoachRequest.getEmail(),academy,UserRole.COACH);

        User userWithPassword = userService.generatePasswordAndSaveUser(user);
        Coach coach = Coach.builder()
                .telephoneNumber(createEditCoachRequest.getTelephoneNumber())
                .trainingGroup(trainingGroup)
                .user(userWithPassword)
                .build();

        return new CreateEditCoachResponse(coachRepository.save(coach));
    }

    public CreateEditCoachResponse editCoach(CreateEditCoachRequest createEditCoachRequest,Long coachId){

            Coach coachToUpdate = getCoachById(coachId);
            coachToUpdate.setTelephoneNumber(createEditCoachRequest.getTelephoneNumber());
            coachToUpdate.getUser().setName(createEditCoachRequest.getName());
            coachToUpdate.getUser().setSurname(createEditCoachRequest.getSurname());
            coachToUpdate.setTrainingGroup(trainingGroupService.getTrainingGroupById(createEditCoachRequest.getTrainingGroupId()));
            if(userService.hasEmailChanged(coachToUpdate.getUser().getEmail(),createEditCoachRequest.getEmail())) {
                userService.validateUserData(createEditCoachRequest.getEmail());
                coachToUpdate.getUser().setEmail(createEditCoachRequest.getEmail());
                String newPassword = new PassGenerator().generatePassword();
                coachToUpdate.getUser().setPassword(newPassword);
                userService.encryptPassword(coachToUpdate.getUser());
                coachRepository.save(coachToUpdate);
                new EmailSender().sendEmail(createEditCoachRequest.getEmail(),newPassword);
                return new CreateEditCoachResponse(coachToUpdate);
            }
            return new CreateEditCoachResponse(coachRepository.save(coachToUpdate));

    }

    public DeleteResponse deleteCoach(Long coachId){
        Coach coach = getCoachById(coachId);
        coachRepository.delete(coach);
        userService.deleteUser(coach.getUser());
        return new DeleteResponse("Pomyślnie usunięto trenera");
    }

    public Coach getCoachById(Long coachId){
        return coachRepository.findById(coachId).orElseThrow(() -> new NotFoundException("Nie znaleziono trenera o takim id"));
    }

    public CoachPlayersResponse getCoachPlayers(Long userId) {
       User user = userService.getUserById(userId);
       Coach coach = getCoachByUser(user);

       return new CoachPlayersResponse( playerService.getPlayersByTrainingGroup(coach.getTrainingGroup()));
    }

    public CoachTrainingGroupResponse getCoachTrainingGroup(Long userId) {
        User user = userService.getUserById(userId);
        Coach coach = getCoachByUser(user);
        if(coach.getTrainingGroup() ==null)
        {
            throw new NotFoundException("Brak grupy treningowej przypisanej do trenera");
        }
        return new CoachTrainingGroupResponse(coach.getTrainingGroup());
    }

    public Set<Coach> getPlayerCoaches(Long userId) {
        User user = userService.getUserById(userId);
        Player player = playerService.getPlayerByUser(user);

        return getCoachesByTrainingGroup(player.getTrainingGroup());
    }

    private Coach getCoachByUser (User user){
        return coachRepository.getCoachByUser(user).orElseThrow(() -> new NotFoundException( "Nie znaleziono trenera o takim id"));
    }

    public Set<Coach> getCoachesByTrainingGroup(TrainingGroup trainingGroup)
    {
        return coachRepository.getCoachesByTrainingGroup(trainingGroup).orElseThrow(() -> new NotFoundException("Nie znaleziono zadnych trenerów w tej grupie treningowej"));
    }
}
