package edu.uni.lodz.system.akademia.pilkarska.domain.model.coach;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
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

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telephoneNumber;
    @OneToOne
    private User user;
    @OneToOne
    private TrainingGroup trainingGroup;

}
