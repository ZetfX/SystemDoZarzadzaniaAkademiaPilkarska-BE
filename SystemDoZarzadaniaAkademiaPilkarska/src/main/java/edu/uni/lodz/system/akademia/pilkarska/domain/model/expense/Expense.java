package edu.uni.lodz.system.akademia.pilkarska.domain.model.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal expenseValue;
    private String expenseTitle;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfExpense;
    @OneToOne
    private Academy academy;

    public Expense(BigDecimal expenseValue, String expenseTitle, Date dateOfExpense, Academy academy) {
        this.expenseValue = expenseValue;
        this.expenseTitle = expenseTitle;
        this.dateOfExpense = dateOfExpense;
        this.academy = academy;
    }
}
