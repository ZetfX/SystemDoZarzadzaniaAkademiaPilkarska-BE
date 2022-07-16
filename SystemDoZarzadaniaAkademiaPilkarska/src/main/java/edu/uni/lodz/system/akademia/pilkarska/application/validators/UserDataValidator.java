package edu.uni.lodz.system.akademia.pilkarska.application.validators;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@AllArgsConstructor
public class UserDataValidator {

    private final UserService userService;


}
