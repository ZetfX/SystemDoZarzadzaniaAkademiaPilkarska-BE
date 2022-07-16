package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
@Getter
@Setter
public class FieldTakenException extends BaseException {

    private String message;
    private final static HttpStatus errorCode = HttpStatus.CONFLICT;


    public FieldTakenException(String message) {
        super(message, errorCode);
        this.message = message;
    }


}
