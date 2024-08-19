package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    // check for existing username
    Optional<Account> findByUsername(String username);
    // validate credentials
    Optional<Account> findByUsernameAndPassword(String username, String password);

    boolean existsById(Integer accountId);
    
}
