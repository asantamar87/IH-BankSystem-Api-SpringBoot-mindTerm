package com.example.ihmidtermprojectbanksystemapi.service.impl;



import com.example.ihmidtermprojectbanksystemapi.dto.AccountDTO;
import com.example.ihmidtermprojectbanksystemapi.dto.AccountHolderDTO;
import com.example.ihmidtermprojectbanksystemapi.dto.MoneyDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.*;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.CreditCardRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.SavingsRepository;
import com.example.ihmidtermprojectbanksystemapi.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.Period;
@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    SavingsRepository savingsRepository;


    public List<AccountDTO> findAccountsFromUser(Long userId){
        List<AccountDTO> accountList = new ArrayList<>();
        for(Account account : accountHolderRepository.findById(userId).get().getAccountsPrimary()) {
            accountList.add(convertAccount(account));
        }
        return accountList;
    }

    public AccountDTO updateBalance(Long accountId, MoneyDTO newBalance) {
        if(accountRepository.findById(accountId).isPresent()) {
            Account account = accountRepository.findById(accountId).get();
            if(account instanceof Savings){
                applyInterestByYear(accountId);

            }else if (account instanceof CreditCard){
                applyInterestMonthly(accountId);
            }
            account.setBalance(newBalance.getNewBalance());
            accountRepository.save(account);
            return convertAccount(account);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account doesn't exists in the database");
        }
    }

    public void decreaseBalance(Long accountId, BigDecimal amountToSubtract) {

        Account account = accountRepository.findById(accountId).get();
        if(account instanceof Savings){
            applyInterestByYear(accountId);

        }else if (account instanceof CreditCard){
            applyInterestMonthly(accountId);
        }
        account.setBalance(account.getBalance().getAmount().subtract(amountToSubtract));
        accountRepository.save(account);
    }

    public void incrementBalance(Long accountId, BigDecimal amountToIncrease) {
        Account account = accountRepository.findById(accountId).get();
        if(account instanceof Savings){
            applyInterestByYear(accountId);

        }else if (account instanceof CreditCard){
            applyInterestMonthly(accountId);
        }
        account.setBalance(account.getBalance().getAmount().add(amountToIncrease));
        accountRepository.save(account);
    }



    private AccountDTO convertAccount(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                new AccountHolderDTO(
                        account.getPrimaryOwner().getUsername(),
                        account.getPrimaryOwner().getPassword(),
                        account.getPrimaryOwner().getDateOfBirth(),
                        account.getPrimaryOwner().getPrimaryAddress(),
                        account.getPrimaryOwner().getMailingAddress()),
                new AccountHolderDTO(
                        account.getSecondaryOwner().getUsername(),
                        account.getSecondaryOwner().getPassword(),
                        account.getSecondaryOwner().getDateOfBirth(),
                        account.getSecondaryOwner().getPrimaryAddress(),
                        account.getSecondaryOwner().getMailingAddress()),
                account.getCreationDate());
    }

    public Account verifyAge (Long accountId){

        Account account= accountRepository.findById(accountId).get();

        LocalDate startDate =  LocalDate.of(account.getCreationDate().getYear(), account.getCreationDate().getMonth(),account.getCreationDate().getDayOfMonth());
        LocalDate endDate =LocalDate.now();

        Period period = Period.between(startDate, endDate);
        if(period.getYears() <18){
            StudentChecking studentChecking = new StudentChecking();
        }else {
            Checking cheking = new Checking();
        }

        return null;
    }

    //This will be needed for account creation process, method to check owner's age
    public Long checkPrimaryOwnerAge(LocalDate localDate, Long accountId){
        Account account= accountRepository.findById(accountId).get();
        LocalDate startDate =  LocalDate.of(account.getCreationDate().getYear(), account.getCreationDate().getMonth(),account.getCreationDate().getDayOfMonth());
        Period period = Period.between(startDate, localDate);

        return Long.valueOf(period.getYears());
    }



    //Metodos relaci0onados con el balance
    public void applyInterestMonthly(Long id){
        CreditCard creditCard = creditCardRepository.findById(id).get();

        BigDecimal interestApplied = creditCard.getInterestRate().divide(new BigDecimal("12"));

        LocalDate startDate =creditCard.getCreationDate();
        LocalDate endDate =LocalDate.now();
        Period period= Period.between(startDate,endDate);


        if (period.getMonths() >=1 && creditCard.getBalance().getAmount().doubleValue() > 0 ) {
           BigDecimal appliedInterest = BigDecimal.ONE.add(interestApplied).multiply(BigDecimal.valueOf(period.getMonths())).multiply(creditCard.getBalance().getAmount());
           creditCard.setBalance(appliedInterest.add(creditCard.getBalance().getAmount()));
        }
    }

    public void applyInterestByYear(Long id){
        //CreditCard creditCard = creditCardRepository.findById(id).get();
        Savings savings = savingsRepository.findById(id).get();

        BigDecimal interestApplied = savings.getInterestRate();

        LocalDate startDate =savings.getCreationDate();
        LocalDate endDate =LocalDate.now();
        Period period= Period.between(startDate,endDate);


        if (period.getYears() >=1 && savings.getBalance().getAmount().doubleValue() > 0 ) {
            BigDecimal appliedInterest = BigDecimal.ONE.add(interestApplied).multiply(BigDecimal.valueOf(period.getMonths())).multiply(savings.getBalance().getAmount());
            savings.setBalance(appliedInterest.add(savings.getBalance().getAmount()));
        }

    }

}
