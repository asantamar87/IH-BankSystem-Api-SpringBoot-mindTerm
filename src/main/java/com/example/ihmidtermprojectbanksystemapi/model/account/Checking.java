package com.example.ihmidtermprojectbanksystemapi.model.account;


import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checking extends Account {

    private String secretKey;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private final Money minimumBalance= new Money(new BigDecimal("250"));

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "maintenance_fee_currency"))
    })
    private final Money monthlyMaintenanceFee = new Money(new BigDecimal("12.00"));


    public Checking(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }


    public Checking(Long id, BigDecimal balance,  LocalDate creationDate, AccountHolder primaryOwner,String secretKey, Money minimumBalance) {
        super(id, balance, creationDate, primaryOwner);
        setSecretKey(secretKey);
    }

    public void setSecretKey(String secretKey){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }



    /*
        @Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
        if(balance.compareTo(minimumBalance.getAmount())<0){
            super.setBalance(balance.subtract(getPenaltyFee().getAmount()));
        }
    }


    public void setMinBalance(Money minimumBalance){
        if(minimumBalance.getAmount().doubleValue()<250.00){
            minimumBalance.setAmount(new BigDecimal("250.00"));
        }else {
        this.minimumBalance = minimumBalance;
        }
    }

    public void setMonthlyMaintenanceFee() {
        this.monthlyMaintenanceFee = new Money(new BigDecimal("12.00"));
    }


 //método en el modelo

    public Money applyFees(Date date){


        Long difference = date.getTime() - getCreationDate().getTime();
        BigDecimal months = BigDecimal.valueOf((difference / (1000l*60*60*24*30)));

        if (getBalance().getAmount().doubleValue() >=
                monthlyMaintenanceFee.getAmount().doubleValue() && months.doubleValue()>=1) {

            BigDecimal appliedFee = monthlyMaintenanceFee.getAmount().multiply(months);
            setBalance(new Money(getBalance().decreaseAmount(appliedFee)));
            if (getBalance().getAmount().doubleValue() < this.minimumBalance.getAmount().doubleValue()
                    && getBalance().getAmount().doubleValue() > getPenaltyFee().getAmount().doubleValue()) {
                setBalance(new Money(getBalance().decreaseAmount(getPenaltyFee().getAmount())));
            }
        }
        return this.getBalance();
    }



    When creating a new Checking account, if the primaryOwner is less than 24,
    a StudentChecking account should be created otherwise a regular Checking Account should be created.
    Checking accounts should have a minimumBalance of 250 and a monthlyMaintenanceFee of 12
     */


}
