package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import org.springframework.http.HttpStatus;

public class TokenRefreshException extends BaseException{
    private String message;
    private final static HttpStatus errorCode = HttpStatus.FORBIDDEN;


    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message),errorCode);
        this.message = message;
    }
}
