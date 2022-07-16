package edu.uni.lodz.system.akademia.pilkarska.domain.model.income;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditIncomeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditTrainingGroupRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllIncomesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditIncomeResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditTrainingGroupResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.monthlySubscription.MonthlySubscription;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncomeService {

    private final AcademyService academyService;
    private final IncomeRepository incomeRepository;

    public AllIncomesResponse getIncomesByAcademy(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);

        return new AllIncomesResponse(incomeRepository.getIncomesByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak przychodów w tej akademii")));
    }


    public CreateEditIncomeResponse createIncome(CreateEditIncomeRequest createEditIncomeRequest) {
        Academy academy = academyService.getAcademyById(createEditIncomeRequest.getAcademyId());
        Income income = new Income(createEditIncomeRequest.getIncomeValue(), createEditIncomeRequest.getIncomeTitle(), createEditIncomeRequest.getDateOfIncome(), academy);


        return new CreateEditIncomeResponse(incomeRepository.save(income));
    }


    public CreateEditIncomeResponse editIncome(CreateEditIncomeRequest createEditIncomeRequest, Long incomeId){
        Income incomeToUpdate = getIncomeById(incomeId);

        incomeToUpdate.setTitle(createEditIncomeRequest.getIncomeTitle());
        incomeToUpdate.setValue(createEditIncomeRequest.getIncomeValue());
        incomeToUpdate.setDateOfIncome(createEditIncomeRequest.getDateOfIncome());


        return new CreateEditIncomeResponse(incomeRepository.save(incomeToUpdate));
    }
    public Income getIncomeById(Long incomeId) {
        if (incomeId != null) {
            return incomeRepository.findById(incomeId).orElseThrow(() -> new NotFoundException("Brak przychodu o takim id"));
        }
        return null;
    }


    public DeleteResponse deleteIncome(Long incomeId){
        incomeRepository.deleteById(incomeId);
        return new DeleteResponse("Pomyślnie usunięto przychód");
    }


}
