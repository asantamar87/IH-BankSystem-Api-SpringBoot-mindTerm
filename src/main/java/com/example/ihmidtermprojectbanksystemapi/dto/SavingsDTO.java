package com.example.ihmidtermprojectbanksystemapi.dto;

import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class SavingsDTO {


    private BigDecimal balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String secretKey;
    private Money minimumBalance;
    private BigDecimal interestRate;

    public SavingsDTO(BigDecimal balance, Long primaryOwnerId, Long secondaryOwnerId, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.secretKey = secretKey;
        this.minimumBalance = new Money(new BigDecimal("1000"));
        this.interestRate = new BigDecimal("0.0025");
    }
}
