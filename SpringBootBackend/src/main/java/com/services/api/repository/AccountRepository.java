package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
    

