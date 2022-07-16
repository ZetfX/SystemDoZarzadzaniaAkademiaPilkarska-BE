package edu.uni.lodz.system.akademia.pilkarska.Exceptions.handlers;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.BaseException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.EventTimeException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.OrganizerException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.TokenRefreshException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            FieldTakenException.class,
            NotFoundException.class,
            TokenRefreshException.class,
            EventTimeException.class,
            OrganizerException.class
    }
    )
    protected ResponseEntity<Object> handleException(BaseException exception) {

        return ResponseEntity
                .status(exception.getErrorCode())
                .body(new JSONObject()
                .put("message", exception.getMessage())
                .put("code", exception.getErrorCode().value()).toMap());
    }
}
