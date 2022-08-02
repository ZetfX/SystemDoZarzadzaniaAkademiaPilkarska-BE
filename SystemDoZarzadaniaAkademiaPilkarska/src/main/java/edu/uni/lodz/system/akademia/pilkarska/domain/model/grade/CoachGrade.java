package edu.uni.lodz.system.akademia.pilkarska.domain.model.grade;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.coach.Coach;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CoachGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = true,name="grade_text",nullable = false,columnDefinition = "varchar(10000)")
    private String gradeText;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfGrade;
    @OneToOne
    private Coach coach;



    public CoachGrade(String gradeText, Date dateOfGrade, Coach coach) {
        this.gradeText = gradeText;
        this.dateOfGrade = dateOfGrade;
        this.coach = coach;
    }
}
