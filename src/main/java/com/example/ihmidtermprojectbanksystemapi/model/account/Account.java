package com.example.ihmidtermprojectbanksystemapi.model.account;


import com.example.ihmidtermprojectbanksystemapi.enums.AccountStatus;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "balance")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "account_balance", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "account_balance_currency", nullable = false))
    }
    )
    private Money balance;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @JsonIgnoreProperties
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    // On creation accounts default to active
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.valueOf("ACTIVE");

    // So all time dependant operations work on the same time zone
    private LocalDate creationDate = LocalDate.now(ZoneId.of("Europe/Madrid"));


    //POner Override
    @Embedded
    private final Money penaltyFee = new Money(new BigDecimal("40.00"));

    public Account(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
    }

    public Account(Long id, BigDecimal balance, LocalDate creationDate, AccountHolder primaryOwner) {
        setId(id);
        setBalance(balance);
        setCreationDate(creationDate);
        setPrimaryOwner(primaryOwner);

    }

    public void setBalance(BigDecimal balance) {
        this.balance = new Money(balance);

    }





 /*  public void sendMoney (Money amount) throws Exception {
        Money newBalance = new Money(balance.decreaseAmount(amount));
        if(newBalance.getAmount().doubleValue()>=0){
            this.setBalance(newBalance);
        }else {
            throw new Exception("Insufficient funds");
        }
    }

    public void receiveMoney(Money amount){
        Money newBalance = new Money(balance.increaseAmount(amount));
        this.setBalance(newBalance);
    }*/

}
