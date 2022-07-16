package edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditTrainingGroupRequest;

import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllTrainingGroupsResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditTrainingGroupResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.monthlySubscription.MonthlySubscription;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.monthlySubscription.MonthlySubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TrainingGroupService {
    private final TrainingGroupRepository trainingGroupRepository;
    private final AcademyService academyService;
    private final MonthlySubscriptionService monthlySubscriptionService;

    public TrainingGroup getTrainingGroupById(Long trainingGroupId) {
        if (trainingGroupId != null) {
            return trainingGroupRepository.findById(trainingGroupId).orElseThrow(() -> new NotFoundException("Brak grupy o takim id"));
        }
        return null;
    }

    public AllTrainingGroupsResponse getAllTrainingGroupsByAcademyId(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);
        return new AllTrainingGroupsResponse(trainingGroupRepository.getTrainingGroupsByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak grup treningowych w tej akademii")));
    }


    public CreateEditTrainingGroupResponse createTrainingGroup(CreateEditTrainingGroupRequest createEditTrainingGroupRequest) {
        String groupName = createEditTrainingGroupRequest.getGroupName();
        isGroupNameTaken(groupName);
        MonthlySubscription monthlySubscription = MonthlySubscription.builder()
                .title(createEditTrainingGroupRequest.getMonthlySubscriptionTitle())
                .value(createEditTrainingGroupRequest.getMonthlySubscriptionValue())
                .build();
        monthlySubscriptionService.saveMonthlySubscription(monthlySubscription);
        Academy academy = academyService.getAcademyById(createEditTrainingGroupRequest.getAcademyId());
        TrainingGroup trainingGroup = TrainingGroup.builder()
                .groupName(groupName)
                .monthlySubscription(monthlySubscription)
                .academy(academy)
                .playerCount(0)
                .build();

        return new CreateEditTrainingGroupResponse(trainingGroupRepository.save(trainingGroup));
    }

    public CreateEditTrainingGroupResponse editTrainingGroup(CreateEditTrainingGroupRequest createEditTrainingGroupRequest, Long trainingGroupId){
        TrainingGroup trainingGroupToUpdate = getTrainingGroupById(trainingGroupId);
        String groupName = createEditTrainingGroupRequest.getGroupName();
        if(!groupName.equals(trainingGroupToUpdate.getGroupName())) {
            isGroupNameTaken(groupName);
        }

        MonthlySubscription monthlySubscription = trainingGroupToUpdate.getMonthlySubscription();
        monthlySubscription.setTitle(createEditTrainingGroupRequest.getMonthlySubscriptionTitle());
        monthlySubscription.setValue(createEditTrainingGroupRequest.getMonthlySubscriptionValue());

        trainingGroupToUpdate.setGroupName(groupName);
        trainingGroupToUpdate.setMonthlySubscription(monthlySubscription);
        monthlySubscriptionService.saveMonthlySubscription(monthlySubscription);


        return new CreateEditTrainingGroupResponse(trainingGroupRepository.save(trainingGroupToUpdate));
    }

    private void isGroupNameTaken(String groupName){
        if(findGroupByGroupName(groupName).isPresent()){
            throw new FieldTakenException("Nazwa grupy treningowej jest zajęta");
        }
    }
    public DeleteResponse deleteTrainingGroup(Long trainingGroupId){
        trainingGroupRepository.deleteById(trainingGroupId);
        return new DeleteResponse("Pomyślnie usunięto grupę treningową");
    }

    private Optional<TrainingGroup> findGroupByGroupName(String groupName)
    {
        return trainingGroupRepository.findByGroupName(groupName);
    }

}
