package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import com.mysql.cj.exceptions.ExceptionFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

    private String message;
    private final static HttpStatus errorCode = HttpStatus.NOT_FOUND;

    public NotFoundException(String message)
    {
        super(message, errorCode);
        this.message = message;
    }
}
