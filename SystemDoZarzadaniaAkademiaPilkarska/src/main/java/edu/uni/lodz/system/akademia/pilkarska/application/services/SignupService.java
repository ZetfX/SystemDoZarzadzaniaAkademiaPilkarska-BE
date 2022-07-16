package edu.uni.lodz.system.akademia.pilkarska.application.services;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.SignUpRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.SignUpResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.validators.EmailValidator;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.enums.UserRole;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignupService {
    @Autowired
    private final EmailValidator emailValidator;
    @Autowired
    private final UserService userService;

    public SignUpResponse registerAcademyAdmin(SignUpRequest request) {
        if (!emailValidator.test(request.getEmail())) {
            throw new IllegalStateException("Niepoprawny email");
        }
        return userService.signUpAcademyAdmin(User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .password(request.getPassword())
                .userRole(UserRole.ADMIN)
                .build());
    }
}
