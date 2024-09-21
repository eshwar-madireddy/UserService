package com.scaler.userservice.ControllerAdvice;

import com.scaler.userservice.dtos.ErrorDto;
import com.scaler.userservice.exceptions.IncorrectPassword;
import com.scaler.userservice.exceptions.InvalidToken;
import com.scaler.userservice.exceptions.UserAlreadyExists;
import com.scaler.userservice.exceptions.UserNotFound;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserAlreadyExists exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus("Failure");
        ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(409));
        return responseEntity;
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFound exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus("Failure");
        ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(404));
        return responseEntity;
    }

    @ExceptionHandler(IncorrectPassword.class)
    public ResponseEntity<ErrorDto> handleIncorrectPasswordException(IncorrectPassword exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus("Failure");
        ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(400));
        return responseEntity;
    }

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<ErrorDto> handleTokenInvalidException(InvalidToken exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        errorDto.setStatus("Failure");
        ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(400));
        return responseEntity;
    }
}
