package edu.uni.lodz.system.akademia.pilkarska.domain.model.expense;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.expense.Expense;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface ExpenseRepository extends JpaRepository<Expense,Long> {
    @Query(value = "SELECT e from Expense e where e.academy = :academy")
    Optional<Set<Expense>> getExpensesByAcademy(@Param("academy") Academy academy);

}
