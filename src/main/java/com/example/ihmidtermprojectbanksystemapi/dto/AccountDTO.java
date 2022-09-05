package com.example.ihmidtermprojectbanksystemapi.dto;

import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {

    private Long accountId;
    private Money balance;
    private AccountHolderDTO primaryOwner;
    private AccountHolderDTO secondaryOwner;
    private LocalDate creationDate;

    public AccountDTO(Long accountId, Money balance, AccountHolderDTO primaryOwner, AccountHolderDTO secondaryOwner, LocalDate creationDate) {
        this.accountId = accountId;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = creationDate;
    }


}