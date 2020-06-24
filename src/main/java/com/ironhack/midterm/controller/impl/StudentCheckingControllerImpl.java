package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.StudentCheckingController;
import com.ironhack.midterm.model.Money;
import com.ironhack.midterm.model.StudentChecking;
import com.ironhack.midterm.model.User;
import com.ironhack.midterm.service.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentCheckingControllerImpl implements StudentCheckingController {
    @Autowired
    private StudentCheckingService studentCheckingService;

    @GetMapping("/accounts/student-checkings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentChecking findById(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return studentCheckingService.findById(user, id);
    }

    @GetMapping("/accounts/student-checkings/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public Money findBalance(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return studentCheckingService.findBalance(user, id);
    }

    @PostMapping("/accounts/student-checkings/{id}/credit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void credit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        studentCheckingService.credit(user, id, amount);
    }

    @PostMapping("/accounts/student-checkings/{id}/debit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debit(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Money amount) {
        studentCheckingService.credit(user, id, amount);
    }
}
