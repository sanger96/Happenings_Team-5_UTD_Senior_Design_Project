package com.services.api.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.UserAccount;
import com.services.api.service.UserAccountService;
import com.services.api.service.AppointmentService;
import com.services.api.entity.Appointment;


@RestController
@RequestMapping("useraccount")
public class UserAccountController {
    
    @Autowired
    private UserAccountService service;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/add")
    public UserAccount add(@RequestBody UserAccount userAccount) {
        return service.add(userAccount);
    }

    @GetMapping("/getById/{id}")
    public UserAccount getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<UserAccount> getAll() {
        return service.getAll();
    }

    @GetMapping("/getAllAppointments/{id}")
    public List<Appointment> getAllAppointments(@PathVariable int id) {
        List<Integer> appointmentIds = service.getAllAppointments(id);
        List<Appointment> appointments = new ArrayList<>();
        for (Integer appointmentId : appointmentIds) {
            appointments.add(appointmentService.getById(appointmentId.intValue()));
        }
        return appointments;
    }

    @PutMapping("/update")
    public UserAccount update(@RequestBody UserAccount userAccount) {
        return service.add(userAccount);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody UserAccount userAccount) {
        return service.delete(userAccount);
    }
}
