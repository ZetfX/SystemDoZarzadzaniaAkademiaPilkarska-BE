package edu.uni.lodz.system.akademia.pilkarska.domain.model.refreshToken;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;
@Getter
@Setter
@Entity(name = "refreshToken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name ="user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, unique= true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
