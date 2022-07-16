package edu.uni.lodz.system.akademia.pilkarska.domain.model.income;


import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal value;
    private String title;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfIncome;
    @OneToOne
    private Academy academy;

    public Income(BigDecimal value, String title, Date dateOfIncome, Academy academy) {
        this.value = value;
        this.title = title;
        this.dateOfIncome = dateOfIncome;
        this.academy = academy;
    }
}
