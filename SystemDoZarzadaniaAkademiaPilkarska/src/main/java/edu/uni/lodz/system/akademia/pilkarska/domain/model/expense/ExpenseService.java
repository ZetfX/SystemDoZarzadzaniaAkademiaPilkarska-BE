package edu.uni.lodz.system.akademia.pilkarska.domain.model.expense;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditExpenseRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditIncomeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllExpensesResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditExpenseResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final AcademyService academyService;
    private final ExpenseRepository expenseRepository;

    public AllExpensesResponse getExpensesByAcademy(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);

        return new AllExpensesResponse(expenseRepository.getExpensesByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak wydatków w tej akademii")));
    }


    public CreateEditExpenseResponse createExpense(CreateEditExpenseRequest createEditExpenseRequest) {
        Academy academy = academyService.getAcademyById(createEditExpenseRequest.getAcademyId());
        Expense expense = new Expense(createEditExpenseRequest.getExpenseValue(), createEditExpenseRequest.getExpenseTitle(), createEditExpenseRequest.getDateOfExpense(), academy);


        return new CreateEditExpenseResponse(expenseRepository.save(expense));
    }


    public CreateEditExpenseResponse editExpense(CreateEditExpenseRequest createEditExpenseRequest, Long incomeId) {
        Expense expense = getExpenseById(incomeId);

        expense.setExpenseTitle(createEditExpenseRequest.getExpenseTitle());
        expense.setExpenseValue(createEditExpenseRequest.getExpenseValue());
        expense.setDateOfExpense(createEditExpenseRequest.getDateOfExpense());


        return new CreateEditExpenseResponse(expenseRepository.save(expense));
    }

    public Expense getExpenseById(Long expenseId) {
        if (expenseId != null) {
            return expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Brak wydatku o takim id"));
        }
        return null;
    }


    public DeleteResponse deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
        return new DeleteResponse("Pomyślnie usunięto wydatek");
    }


}
