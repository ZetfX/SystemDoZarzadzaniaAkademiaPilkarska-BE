package edu.uni.lodz.system.akademia.pilkarska.application.services;

import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateAcademyRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditCoachRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditExpenseRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditIncomeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditPlayerRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditTrainingGroupRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AcademyResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllCoachesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllExpensesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllIncomesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllPlayersResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllTrainingGroupsResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditCoachResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditExpenseResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditIncomeResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditPlayerResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditTrainingGroupResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.CoachService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.expense.ExpenseService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.income.IncomeService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.PlayerService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AdminService {

    private final AcademyService academyService;
    private final PlayerService playerService;
    private final TrainingGroupService trainingGroupService;
    private final CoachService coachService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final CalculationsService calculationsService;

    public AcademyResponse createAcademy(CreateAcademyRequest createAcademyRequest) {
        return academyService.createAcademy(createAcademyRequest);
    }

    public AllPlayersResponse getAllAcademyPlayers(Long academyId) {
        return playerService.getAllAcademyPlayers(academyId);
    }


    public CreateEditPlayerResponse createPlayer(CreateEditPlayerRequest createEditPlayerRequest) {
        return playerService.createPlayer(createEditPlayerRequest);
    }

    public AllTrainingGroupsResponse getAllTrainingGroups(Long academyId) {
        return trainingGroupService.getAllTrainingGroupsByAcademyId(academyId);
    }

    public CreateEditPlayerResponse editPlayer(CreateEditPlayerRequest createEditPlayerRequest, Long playerId) {
        return playerService.editPlayer(createEditPlayerRequest, playerId);
    }

    public DeleteResponse deletePlayer(Long playerId) {
        return playerService.deletePlayer(playerId);
    }

    public CreateEditTrainingGroupResponse createTrainingGroup(CreateEditTrainingGroupRequest createEditTrainingGroupRequest) {
        return trainingGroupService.createTrainingGroup(createEditTrainingGroupRequest);
    }

    public CreateEditTrainingGroupResponse editTrainingGroup(CreateEditTrainingGroupRequest createEditTrainingGroupRequest, Long trainingGroupId) {
        return trainingGroupService.editTrainingGroup(createEditTrainingGroupRequest, trainingGroupId);
    }

    public DeleteResponse deleteTrainingGroup(Long trainingGroupId) {
        return trainingGroupService.deleteTrainingGroup(trainingGroupId);
    }


    public AllCoachesResponse getCoaches(Long academyId) {
        return coachService.getCoachesByAcademy(academyId);
    }

    public CreateEditCoachResponse createCoach(CreateEditCoachRequest createEditCoachRequest) {
        return coachService.createCoach(createEditCoachRequest);
    }

    public CreateEditCoachResponse editCoach(CreateEditCoachRequest createEditCoachRequest, Long coachId) {
        return coachService.editCoach(createEditCoachRequest, coachId);
    }

    public DeleteResponse deleteCoach(Long coachId) {
        return coachService.deleteCoach(coachId);
    }


    public AllIncomesResponse getIncomes(Long academyId) {
        return incomeService.getIncomesByAcademy(academyId);
    }

    public CreateEditIncomeResponse createIncome(CreateEditIncomeRequest createEditIncomeRequest) {
        return incomeService.createIncome(createEditIncomeRequest);
    }

    public CreateEditIncomeResponse editIncome(CreateEditIncomeRequest createEditIncomeRequest, Long incomeId) {
        return incomeService.editIncome(createEditIncomeRequest, incomeId);
    }


    public DeleteResponse deleteIncome(Long incomeId) {
        return incomeService.deleteIncome(incomeId);
    }


    public AllExpensesResponse getExpenses(Long academyId) {
        return expenseService.getExpensesByAcademy(academyId);
    }

    public CreateEditExpenseResponse createExpense(CreateEditExpenseRequest createEditExpenseRequest) {
        return expenseService.createExpense(createEditExpenseRequest);
    }

    public CreateEditExpenseResponse editExpense(CreateEditExpenseRequest createEditExpenseRequest, Long expenseId) {
        return expenseService.editExpense(createEditExpenseRequest, expenseId);
    }

    public DeleteResponse deleteExpense(Long expenseId) {
        return expenseService.deleteExpense(expenseId);
    }

    public BigDecimal calculateMonthlySubscriptionIncome(Long academyId){
        return calculationsService.calculateMonthlySubscription(academyId);
    }

    public BigDecimal calculateMonthlyExpenses(Long academyId) {
        return calculationsService.calculateMonthlyExpenses(academyId);
    }

    public BigDecimal calculateTotalMonthlyIncome(Long academyId) {
        return calculationsService.calculateTotalMonthlyIncome(academyId);
    }

    public BigDecimal calculateMonthlyBalance(Long academyId) {
        return calculationsService.calculateMonthlyBalance(academyId);
    }
}
