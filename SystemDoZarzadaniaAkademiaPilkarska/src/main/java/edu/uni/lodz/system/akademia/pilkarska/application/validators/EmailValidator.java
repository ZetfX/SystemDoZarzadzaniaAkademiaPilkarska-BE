package edu.uni.lodz.system.akademia.pilkarska.application.validators;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //TODO: regex
        return true;
    }
}
