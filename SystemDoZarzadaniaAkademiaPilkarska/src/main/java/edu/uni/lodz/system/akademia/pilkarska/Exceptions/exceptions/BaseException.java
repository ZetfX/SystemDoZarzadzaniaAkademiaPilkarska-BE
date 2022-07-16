package edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
@Setter
public class BaseException extends RuntimeException{
    private String message;
    private HttpStatus errorCode;


}
