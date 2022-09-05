package com.example.ihmidtermprojectbanksystemapi.controlleer.impl;

import com.example.ihmidtermprojectbanksystemapi.dto.AccountHolderDTO;
import com.example.ihmidtermprojectbanksystemapi.dto.TransactionDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountRepository;
import com.example.ihmidtermprojectbanksystemapi.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.ihmidtermprojectbanksystemapi.model.utils.PasswordUtil.encryptedPassword;

@RestController
@RequestMapping("/client")
public class AccountHolderController {


    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder store(@RequestBody AccountHolderDTO accountHolderDTO){
        AccountHolder accountHolder = new AccountHolder(
                accountHolderDTO.getUsername(),
                encryptedPassword(accountHolderDTO.getPassword()),
                accountHolderDTO.getDateOfBirth(),
                accountHolderDTO.getAddress(),
                accountHolderDTO.getMailingAddress());

        return accountHolderRepository.save(accountHolder);
    }



    @PatchMapping("/transfer/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Account> transfer(@PathVariable("id") Long accountId, @RequestBody TransactionDTO transactionDTO){

        List<Account> accounts = new ArrayList<>();
        //Send Account
        Optional<Account> senderAccount = accountRepository.findById(accountId);
        //Receiver Account
        Optional<Account> receiverAccount = accountRepository.findById(transactionDTO.getAccountId());

        //isPresent() for two accounts
        if(senderAccount.isPresent() && receiverAccount.isPresent()) {
            //CompareTo verify amount of Balance > 0
            if(senderAccount.get().getBalance().getAmount().compareTo(transactionDTO.getTransactionAmount().getAmount()) > 0) {
                //Decrease and increment respective accounts.
                accountService.decreaseBalance(senderAccount.get().getId(), transactionDTO.getTransactionAmount().getAmount());
                accountService.incrementBalance(receiverAccount.get().getId(), transactionDTO.getTransactionAmount().getAmount());

                accounts.add((accountRepository.findById(accountId).get()));
                accounts.add((accountRepository.findById(transactionDTO.getAccountId()).get()));

            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have enough balance for this transaction");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please provide correct account id's");
        return accounts;
    }


}
