package com.scaler.userservice.controllers;

import com.scaler.userservice.dtos.*;
import com.scaler.userservice.exceptions.IncorrectPassword;
import com.scaler.userservice.exceptions.InvalidToken;
import com.scaler.userservice.exceptions.UserAlreadyExists;
import com.scaler.userservice.exceptions.UserNotFound;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.services.UserService;
import com.scaler.userservice.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto requestDto) throws UserAlreadyExists {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());

        return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) throws UserNotFound, IncorrectPassword {
        Token token = userService.logIn(requestDto.getEmail(), requestDto.getPassword());
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);

        return new ResponseEntity<>(responseDto,HttpStatusCode.valueOf(201));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate")
    private ResponseEntity<UserDto> validate(@RequestHeader("Authorization") String token) throws InvalidToken {

        User user = userService.validate(token);
        return new ResponseEntity<>(UserDto.fromUser(user),HttpStatusCode.valueOf(200));
    }
}
