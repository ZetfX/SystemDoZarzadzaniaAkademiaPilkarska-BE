package edu.uni.lodz.system.akademia.pilkarska.application.controllers;

import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateAcademyRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditCoachRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditExpenseRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditIncomeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditPlayerRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditTrainingGroupRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/createAcademy")
    public ResponseEntity<?> createAcademy(@Valid @RequestBody CreateAcademyRequest createAcademyRequest) {
        return ResponseEntity.ok(adminService.createAcademy(createAcademyRequest));
    }

    @GetMapping("/allPlayers/{academyId}")
    public ResponseEntity<?> getAllAcademyPlayers(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.getAllAcademyPlayers(academyId));
    }

    @PostMapping("/createPlayer")
    public ResponseEntity<?> createPlayer(@Valid @RequestBody CreateEditPlayerRequest createEditPlayerRequest) {
        return ResponseEntity.ok(adminService.createPlayer(createEditPlayerRequest));
    }


    @PutMapping("/editPlayer/{playerId}")
    public ResponseEntity<?> editPlayer(@Valid @RequestBody CreateEditPlayerRequest createEditPlayerRequest, @PathVariable("playerId") Long playerId) {
        return ResponseEntity.ok(adminService.editPlayer(createEditPlayerRequest, playerId));
    }

    @DeleteMapping("/deletePlayer/{playerId}")
    public ResponseEntity<?> deletePlayer(@Valid @PathVariable("playerId") Long playerId) {
        return ResponseEntity.ok(adminService.deletePlayer(playerId));
    }

    @GetMapping("/allTrainingGroups/{academyId}")
    public ResponseEntity<?> getTrainingGroups(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.getAllTrainingGroups(academyId));
    }

    @PostMapping("/createTrainingGroup")
    public ResponseEntity<?> createTrainingGroup(@Valid @RequestBody CreateEditTrainingGroupRequest createEditTrainingGroupRequest) {
        return ResponseEntity.ok(adminService.createTrainingGroup(createEditTrainingGroupRequest));
    }

    @PutMapping("/editTrainingGroup/{trainingGroupId}")
    public ResponseEntity<?> editTrainingGroup(@PathVariable("trainingGroupId") Long trainingGroupId, @Valid @RequestBody CreateEditTrainingGroupRequest createEditTrainingGroupRequest) {
        return ResponseEntity.ok(adminService.editTrainingGroup(createEditTrainingGroupRequest, trainingGroupId));
    }

    @DeleteMapping("/deleteTrainingGroup/{trainingGroupId}")
    public ResponseEntity<?> deleteTrainingGroup(@PathVariable("trainingGroupId") Long trainingGroupId) {
        return ResponseEntity.ok(adminService.deleteTrainingGroup(trainingGroupId));
    }

    @GetMapping("/allCoaches/{academyId}")
    public ResponseEntity<?> getAllCoaches(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.getCoaches(academyId));
    }

    @PostMapping("/createCoach")
    public ResponseEntity<?> createCoach(@Valid @RequestBody CreateEditCoachRequest createEditCoachRequest) {
        return ResponseEntity.ok(adminService.createCoach(createEditCoachRequest));
    }

    @PutMapping("/editCoach/{coachId}")
    public ResponseEntity<?> editCoach(@Valid @RequestBody CreateEditCoachRequest createEditCoachRequest, @PathVariable("coachId") Long coachId) {
        return ResponseEntity.ok(adminService.editCoach(createEditCoachRequest, coachId));
    }

    @DeleteMapping("/deleteCoach/{coachId}")
    public ResponseEntity<?> deleteCoach(@Valid @PathVariable("coachId") Long coachId) {
        return ResponseEntity.ok(adminService.deleteCoach(coachId));
    }

    @GetMapping("/getAllIncomes/{academyId}")
    public ResponseEntity<?> getAllIncomes(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.getIncomes(academyId));
    }

    @PostMapping("/createIncome")
    public ResponseEntity<?> createIncome(@Valid @RequestBody CreateEditIncomeRequest createEditIncomeRequest) {
        return ResponseEntity.ok(adminService.createIncome(createEditIncomeRequest));
    }

    @PutMapping("/editIncome/{incomeId}")
    public ResponseEntity<?> editIncome(@Valid @RequestBody CreateEditIncomeRequest createEditIncomeRequest, @PathVariable("incomeId") Long incomeId) {
        return ResponseEntity.ok(adminService.editIncome(createEditIncomeRequest, incomeId));
    }


    @DeleteMapping("/deleteIncome/{incomeId}")
    public ResponseEntity<?> deleteIncome(@Valid @PathVariable("incomeId") Long incomeId) {
        return ResponseEntity.ok(adminService.deleteIncome(incomeId));
    }

    @GetMapping("/getAllExpenses/{academyId}")
    public ResponseEntity<?> getAllExpenses(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.getExpenses(academyId));
    }

    @PostMapping("/createExpense")
    public ResponseEntity<?> createExpense(@Valid @RequestBody CreateEditExpenseRequest createEditExpenseRequest) {
        return ResponseEntity.ok(adminService.createExpense(createEditExpenseRequest));
    }

    @PutMapping("/editExpense/{expenseId}")
    public ResponseEntity<?> editExpense(@Valid @RequestBody CreateEditExpenseRequest createEditExpenseRequest, @PathVariable("expenseId") Long expenseId) {
        return ResponseEntity.ok(adminService.editExpense(createEditExpenseRequest, expenseId));
    }

    @DeleteMapping("/deleteExpense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@Valid @PathVariable("expenseId") Long expenseId) {
        return ResponseEntity.ok(adminService.deleteExpense(expenseId));
    }

    @GetMapping("/monthlySubscriptionIncome/{academyId}")
    public ResponseEntity<?> monthlySubscriptionIncome(@PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.calculateMonthlySubscriptionIncome(academyId));
    }

    @GetMapping("/monthlyExpenses/{academyId}")
    public ResponseEntity<?> monthlyExpenses(@PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.calculateMonthlyExpenses(academyId));
    }

    @GetMapping("/totalMonthlyIncomes/{academyId}")
    public ResponseEntity<?> monthlyIncomes(@PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.calculateTotalMonthlyIncome(academyId));
    }

    @GetMapping("/monthlyBalance/{academyId}")
    public ResponseEntity<?> monthlyBalance(@PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminService.calculateMonthlyBalance(academyId));
    }
}
