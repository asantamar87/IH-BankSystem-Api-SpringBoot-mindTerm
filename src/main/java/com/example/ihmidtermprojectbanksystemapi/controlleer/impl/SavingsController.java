package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;

import com.example.ihmidtermprojectbanksystemapi.dto.SavingsDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Savings;
import com.example.ihmidtermprojectbanksystemapi.service.impl.AccountService;
import com.example.ihmidtermprojectbanksystemapi.service.impl.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class SavingsController {

    @Autowired
    SavingsAccountService savingsAccountService;

    @PostMapping("/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings store(@RequestBody SavingsDTO savingsDTO) {
        return savingsAccountService.create(savingsDTO);
    }

}