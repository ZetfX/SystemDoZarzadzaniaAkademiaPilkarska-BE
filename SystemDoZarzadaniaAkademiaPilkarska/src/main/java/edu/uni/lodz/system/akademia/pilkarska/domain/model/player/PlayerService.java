package edu.uni.lodz.system.akademia.pilkarska.domain.model.player;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.generators.PassGenerator;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditPlayerRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditPlayerResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.senders.EmailSender;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.enums.UserRole;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroupService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AcademyService academyService;
    private final TrainingGroupService trainingGroupService;
    private final UserService userService;

    public AllPlayersResponse getAllAcademyPlayers(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);
        return new AllPlayersResponse(playerRepository.getPlayersByAcademy(academy).orElseThrow(() -> new NotFoundException("Brak zawodników")));
    }

    public CreateEditPlayerResponse createPlayer(CreateEditPlayerRequest createEditPlayerRequest) {
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroupById(createEditPlayerRequest.getTrainingGroupId());
        trainingGroup.setPlayerCount(trainingGroup.getPlayerCount() + 1);
        Academy academy = academyService.getAcademyById(createEditPlayerRequest.getAcademyId());
        User user = new User(createEditPlayerRequest.getName(), createEditPlayerRequest.getSurname(), createEditPlayerRequest.getEmail(), academy, UserRole.CASUAL_USER);
        User userWithPassword = userService.generatePasswordAndSaveUser(user);
        Player player = Player.builder()
                .telephoneNumber(createEditPlayerRequest.getTelephoneNumber())
                .dateOfBirth(createEditPlayerRequest.getDateOfBirth())
                .gender(createEditPlayerRequest.getGender())
                .pesel(createEditPlayerRequest.getPesel())
                .trainingGroup(trainingGroup)
                .user(userWithPassword)
                .build();

        return new CreateEditPlayerResponse(playerRepository.save(player));
    }

    public CreateEditPlayerResponse editPlayer(CreateEditPlayerRequest createEditPlayerRequest, Long playerId) {
        Player playerToUpdate = getPlayerById(playerId);
        playerToUpdate.setGender(createEditPlayerRequest.getGender());
        playerToUpdate.setTelephoneNumber(createEditPlayerRequest.getTelephoneNumber());
        playerToUpdate.setDateOfBirth(createEditPlayerRequest.getDateOfBirth());
        playerToUpdate.setPesel(createEditPlayerRequest.getPesel());
        playerToUpdate.getUser().setName(createEditPlayerRequest.getName());
        playerToUpdate.getUser().setSurname(createEditPlayerRequest.getSurname());
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroupById(createEditPlayerRequest.getTrainingGroupId());
        TrainingGroup playerToUpdateTrainingGroup = playerToUpdate.getTrainingGroup();
        if (!playerToUpdateTrainingGroup.getId().equals(trainingGroup.getId())) {
            playerToUpdateTrainingGroup.setPlayerCount(playerToUpdateTrainingGroup.getPlayerCount() - 1);
            trainingGroup.setPlayerCount(trainingGroup.getPlayerCount() + 1);
        }
        playerToUpdate.setTrainingGroup(trainingGroup);
        if (userService.hasEmailChanged(playerToUpdate.getUser().getEmail(), createEditPlayerRequest.getEmail())) {
            userService.validateUserData(createEditPlayerRequest.getEmail());
            playerToUpdate.getUser().setEmail(createEditPlayerRequest.getEmail());
            String newPassword = new PassGenerator().generatePassword();
            playerToUpdate.getUser().setPassword(newPassword);
            playerRepository.save(playerToUpdate);
            new EmailSender().sendEmail(createEditPlayerRequest.getEmail(), newPassword);
            return new CreateEditPlayerResponse(playerToUpdate);
        }
        return new CreateEditPlayerResponse(playerRepository.save(playerToUpdate));

    }

    public DeleteResponse deletePlayer(Long playerId) {
        Player player = playerRepository.getById(playerId);
        player.getTrainingGroup().setPlayerCount(player.getTrainingGroup().getPlayerCount() - 1);
        playerRepository.delete(player);
        userService.deleteUser(player.getUser());
        return new DeleteResponse("Pomyślnie usunięto zawodnika");
    }


    private Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new NotFoundException("Nie znaleziono zawodnika o takim id"));
    }

    public Set<Player> getPlayersByTrainingGroup (TrainingGroup trainingGroup){
        return playerRepository.getPlayersByTrainingGroup(trainingGroup).orElseThrow(() -> new NotFoundException("Nie znaleziono zadnych zawodników w tej grupie treningowej"));
    }

}
