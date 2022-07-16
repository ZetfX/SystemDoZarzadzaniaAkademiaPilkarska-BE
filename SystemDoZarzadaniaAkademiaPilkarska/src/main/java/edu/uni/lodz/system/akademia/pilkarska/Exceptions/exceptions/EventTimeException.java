package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import org.springframework.http.HttpStatus;

public class EventTimeException extends BaseException{
    private String message;
    private final static HttpStatus errorCode = HttpStatus.BAD_REQUEST;

    public EventTimeException(String message)
    {
        super(message, errorCode);
        this.message = message;
    }
}
