package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private final String type ="Bearer";
    private String refreshToken;
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private Academy academy;
}
