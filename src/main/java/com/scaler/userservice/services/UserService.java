package com.scaler.userservice.services;

import com.scaler.userservice.exceptions.IncorrectPassword;
import com.scaler.userservice.exceptions.InvalidToken;
import com.scaler.userservice.exceptions.UserAlreadyExists;
import com.scaler.userservice.exceptions.UserNotFound;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;

public interface UserService {
    public User signUp(String name, String email, String pass) throws UserAlreadyExists;
    public Token logIn(String email, String pass) throws UserNotFound, IncorrectPassword;
    public User validate(String token) throws InvalidToken;
    public void logout(String token);
}
