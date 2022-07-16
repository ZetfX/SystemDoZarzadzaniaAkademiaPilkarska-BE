package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class EventDto {
    private Long id;
    private String eventTitle;
    private Date eventDate;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    @OneToOne
    private TrainingGroup trainingGroup;
    @OneToOne
    private Object object;
    @OneToOne
    private User organizer;
    @OneToOne
    private Academy academy;
}
