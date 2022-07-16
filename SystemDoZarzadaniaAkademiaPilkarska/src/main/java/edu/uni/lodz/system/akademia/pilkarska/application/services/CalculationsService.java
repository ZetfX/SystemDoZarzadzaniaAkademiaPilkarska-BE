package edu.uni.lodz.system.akademia.pilkarska.application.services;

import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllExpensesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllIncomesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllTrainingGroupsResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.expense.Expense;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.expense.ExpenseService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.income.Income;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.income.IncomeService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@AllArgsConstructor
public class CalculationsService {

    private final TrainingGroupService trainingGroupService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public BigDecimal calculateMonthlySubscription(Long academyId) {
        AllTrainingGroupsResponse allTrainingGroupsResponse = trainingGroupService.getAllTrainingGroupsByAcademyId(academyId);
        BigDecimal sum = BigDecimal.ZERO;
        for (TrainingGroup trainingGroup : allTrainingGroupsResponse.getTrainingGroups()) {
            sum = sum.add(trainingGroup.getMonthlySubscription().getValue().multiply(BigDecimal.valueOf(trainingGroup.getPlayerCount())));
        }
        return sum;
    }


    public BigDecimal calculateMonthlyExpenses(Long academyId) {
        AllExpensesResponse allExpensesResponse = expenseService.getExpensesByAcademy(academyId);
        BigDecimal sum = BigDecimal.ZERO;
        LocalDate todayDate = LocalDate.now();
        for (Expense expense : allExpensesResponse.getExpenses()) {
           sum = calculateMonthlyValue(expense.getDateOfExpense(),todayDate,expense.getExpenseValue(),sum);
        }

        return sum;

    }

    public BigDecimal calculateTotalMonthlyIncome(Long academyId) {
       return calculateMonthlySubscription(academyId).add(calculateMonthlyIncomes(academyId));
    }

    private BigDecimal calculateMonthlyIncomes(Long academyId){
        AllIncomesResponse allIncomesResponse = incomeService.getIncomesByAcademy(academyId);
        LocalDate todayDate = LocalDate.now();
        BigDecimal sum = BigDecimal.ZERO;
        for(Income income : allIncomesResponse.getIncomes()){
            sum = calculateMonthlyValue(income.getDateOfIncome(),todayDate,income.getValue(),sum);
        }
        return sum;
    }

    private LocalDate toLocalDate (Date date){
       return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private boolean isFromTheSameMonth(LocalDate date, LocalDate todayDate){
        return (date.getMonth() == todayDate.getMonth()) && (date.getYear() == todayDate.getYear());
    }

    private BigDecimal calculateMonthlyValue (Date date, LocalDate todayDate, BigDecimal value, BigDecimal actualSum){
        LocalDate dateOfExpense = toLocalDate(date);
        if (isFromTheSameMonth(dateOfExpense,todayDate)) {
            actualSum = actualSum.add(value);
        }
        return actualSum;
    }

    public BigDecimal calculateMonthlyBalance(Long academyId) {
        return calculateTotalMonthlyIncome(academyId).subtract(calculateMonthlyExpenses(academyId));

    }
}
