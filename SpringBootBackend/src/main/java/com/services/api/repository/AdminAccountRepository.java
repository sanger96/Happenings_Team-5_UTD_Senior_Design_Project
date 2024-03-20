package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.AdminAccount;


public interface AdminAccountRepository extends JpaRepository<AdminAccount, Integer>{
    
}
