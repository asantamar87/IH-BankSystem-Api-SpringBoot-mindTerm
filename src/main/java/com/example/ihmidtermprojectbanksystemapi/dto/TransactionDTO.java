package com.example.ihmidtermprojectbanksystemapi.dto;


import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    @JsonProperty(required = true)
    private Money transactionAmount;

    @JsonProperty(required = true)
    private Long accountId;

    private Long accountSecretKey;
    private String primaryOwner;
    private String secondaryOwner;
    private String hashedKey;


    public TransactionDTO(String hashedKey, Money transactionAmount, Long accountId, Long accountSecretKey) {
        this.hashedKey = hashedKey;
        this.transactionAmount = transactionAmount;
        this.accountId = accountId;
        this.accountSecretKey = accountSecretKey;
    }

    public TransactionDTO(Money transactionAmount, Long accountId, String primaryOwner) {
        this.transactionAmount = transactionAmount;
        this.accountId = accountId;
        this.primaryOwner = primaryOwner;
    }
}
