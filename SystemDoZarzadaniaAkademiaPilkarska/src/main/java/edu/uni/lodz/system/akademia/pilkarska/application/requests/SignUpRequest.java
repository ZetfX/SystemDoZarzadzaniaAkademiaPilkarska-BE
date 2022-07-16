package edu.uni.lodz.system.akademia.pilkarska.application.requests;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest {
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    @Nullable
    private final UserRole userRole;
}
