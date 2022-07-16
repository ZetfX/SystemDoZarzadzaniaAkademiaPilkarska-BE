package edu.uni.lodz.system.akademia.pilkarska.domain.model.event;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
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
import javax.persistence.OneToOne;
import java.sql.Time;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventTitle;
    private Date eventDate;
    private Time startTime;
    private Time endTime;
    @OneToOne
    private TrainingGroup trainingGroup;
    @OneToOne
    private Object object;
    @OneToOne
    private User organizer;
    @OneToOne
    private Academy academy;


    public Event(String eventTitle, Date eventDate, Time startTime, Time endTime, TrainingGroup trainingGroup, Object object, User organizer, Academy academy) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainingGroup = trainingGroup;
        this.object = object;
        this.organizer = organizer;
        this.academy = academy;
    }
}
