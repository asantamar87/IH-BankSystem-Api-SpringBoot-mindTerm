package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;

import com.example.ihmidtermprojectbanksystemapi.controlleer.interfaces.ICheckingController;
import com.example.ihmidtermprojectbanksystemapi.dto.CheckingDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Checking;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.CheckingRepository;
import com.example.ihmidtermprojectbanksystemapi.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.Optional;


@RestController
public class CheckingController implements ICheckingController {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

   @Autowired
    AccountService accountService;

    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Checking store(@RequestBody CheckingDTO checkingDTO) {
        Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId());
        Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId());

        LocalDate today= LocalDate.now();
        if(accountService.checkPrimaryOwnerAge(today,primaryOwner.get().getId()) > 24) {
            Checking checking = new Checking(
                    checkingDTO.getBalance(),
                    primaryOwner.get(),
                    secondaryOwner.get(),
                    checkingDTO.getSecretKey());
            return checkingRepository.save(checking);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checking Card account is only created for " +
                    "custmers over 24 years old, you must create a StudendChecking Card");
        }

        }

    }



