package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import org.springframework.http.HttpStatus;

public class OrganizerException  extends BaseException{

    private String message;
    private final static HttpStatus errorCode = HttpStatus.BAD_REQUEST;

    public OrganizerException(String message)
    {
        super(message, errorCode);
        this.message = message;
    }
}
