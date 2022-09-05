package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;


import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import com.example.ihmidtermprojectbanksystemapi.model.account.CreditCard;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.CreditCardRepository;
import com.example.ihmidtermprojectbanksystemapi.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;

@RestController
public class CreditCardController {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;


    //Route to create a new credit card account (assuming it's only available for adults)
    @PostMapping("/accounts/create/creditcard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createNewCreditCard(@RequestBody @Valid CreditCard creditCard) throws ParseException {
        LocalDate today = LocalDate.now();

        Account accountCC= new CreditCard();
        Long accountId = accountCC.getId();

        if(accountService.checkPrimaryOwnerAge(today,accountId) >= 18) {
            return creditCardRepository.save(creditCard);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit Card account is only created for " +
                    "custmers over 18 years old");
        }
    }
}
