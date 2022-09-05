package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;


import com.example.ihmidtermprojectbanksystemapi.controlleer.interfaces.ICheckingController;
import com.example.ihmidtermprojectbanksystemapi.dto.StudentCheckingDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.StudentChecking;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.StudentCheckingRepository;
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
public class StudentCheckingCardController implements ICheckingController {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountService accountService;

    @PostMapping("/student-checking")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentChecking store(@RequestBody StudentCheckingDTO studentCheckingDT) {
        Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(studentCheckingDT.getPrimaryOwnerId());
        Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(studentCheckingDT.getSecondaryOwnerId());

        LocalDate today = LocalDate.now();
        if (accountService.checkPrimaryOwnerAge(today, primaryOwner.get().getId()) < 24) {
            StudentChecking studentChecking = new StudentChecking(
                    studentCheckingDT.getBalance(),
                    primaryOwner.get(),
                    secondaryOwner.get(),
                    studentCheckingDT.getSecretKey());
            return studentCheckingRepository.save(studentChecking);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student Checking Card account is only created for " +
                    "custmers under 24 years old, you must create a Checking Card");
        }


    }
}