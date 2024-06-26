package com.services.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.api.entity.UserAccount;
import com.services.api.entity.Appointment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{
    
    @Query(value = "SELECT Appointment.appointmentid\n" + //
                "FROM Appointment\n" + //
                "JOIN UserAccount ON Appointment.user_accountid = UserAccount.useraccountid\n" + //
                "WHERE UserAccount.useraccountid = ?1" + //
                "", nativeQuery = true)
    List<Integer> getAllAppointments(int id);

    @Query(value = "SELECT accountid FROM account a WHERE a.email = ?1 AND a.password = ?2", nativeQuery = true)
    Integer checkLogin(String email, String password);
}
