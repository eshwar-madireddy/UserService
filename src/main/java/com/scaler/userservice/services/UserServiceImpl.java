package com.scaler.userservice.services;

import com.scaler.userservice.exceptions.IncorrectPassword;
import com.scaler.userservice.exceptions.InvalidToken;
import com.scaler.userservice.exceptions.UserAlreadyExists;
import com.scaler.userservice.exceptions.UserNotFound;
import com.scaler.userservice.repositories.TokenRepository;
import com.scaler.userservice.repositories.UserRepository;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenService tokenService;
    private TokenRepository tokenRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenService tokenService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String pass) throws UserAlreadyExists {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            throw new UserAlreadyExists("User already exists!");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(pass));

        return userRepository.save(user);
    }

    @Override
    public Token logIn(String email, String pass) throws UserNotFound, IncorrectPassword {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFound("User doesn't exist");
        }

        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(pass, user.getHashedPassword())) {
            throw new IncorrectPassword("Invalid username/password");
        }

        Token token = tokenService.createToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public User validate(String tokenValue) throws InvalidToken {
        Optional<Token> Optionaltoken = tokenRepository
                .findByValueAndIsDeleatedAndExpiryAtGreaterThan(tokenValue,
                        false,
                        new Date());
        if(Optionaltoken.isEmpty()) {
            throw new InvalidToken("Token not valid!");
        }
        Token token = Optionaltoken.get();
        return token.getUser();
    }

    @Override
    public void logout(String tokenValue) {
        Optional<Token> optionalToken = tokenRepository
                .findByValueAndIsDeleated(tokenValue, false);

        Token token = optionalToken.get();

        token.setIsDeleated(true);
        tokenRepository.save(token);
    }

}
