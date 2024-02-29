package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.UserAccount;


public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{
    
}
