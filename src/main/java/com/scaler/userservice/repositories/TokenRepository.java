package com.scaler.userservice.repositories;

import com.scaler.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndIsDeleatedAndExpiryAtGreaterThan(String val, Boolean isDel, Date date);

    Optional<Token> findByValueAndIsDeleated(String tokenValue, boolean b);
}
