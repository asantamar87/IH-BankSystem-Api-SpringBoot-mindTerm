package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;


import com.example.ihmidtermprojectbanksystemapi.controlleer.interfaces.IAccountController;
import com.example.ihmidtermprojectbanksystemapi.dto.AccountDTO;
import com.example.ihmidtermprojectbanksystemapi.dto.MoneyDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountRepository;
import com.example.ihmidtermprojectbanksystemapi.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController implements IAccountController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountService accountService;
    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public String index(){ //the actual function from the interface
        return "Welcome to Bank System of la Caja!";
    }


   @GetMapping("/my-account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountById(@AuthenticationPrincipal UserDetails userDetails) {
       return accountHolderRepository.findByUsername(userDetails.getUsername()).get();
    }

    @PatchMapping("/admin/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO updateAccount(@PathVariable("id") Long accountId, @RequestBody MoneyDTO newBalance) {
        return accountService.updateBalance(accountId, newBalance);
    }

    //route to return the list of accounts
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

}
