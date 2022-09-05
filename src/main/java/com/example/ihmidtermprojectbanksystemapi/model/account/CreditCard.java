package com.example.ihmidtermprojectbanksystemapi.model.account;

import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit")),
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    private Money creditLimit = new Money(new BigDecimal("100.00"));

    @DecimalMin(value = "0.1", message = "Minimum interest rate is 0.1")
    private BigDecimal interestRate;
    //private BigDecimal interestRate = new BigDecimal("0.2");

    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    //CreditCard accounts have a default creditLimit of 100.
    public void setCreditLimit(Money creditLimit) {
        //Obtain value of creditLimit.
        BigDecimal convertLimit = new BigDecimal(String.valueOf((creditLimit)));
        this.creditLimit = isaBoolean(convertLimit)
                ? creditLimit : new Money(new BigDecimal("100"));
    }

    // CreditCards may be instantiated with a creditLimit higher than 100 but not higher than 100000.
    private boolean isaBoolean(BigDecimal convertLimit) {
        return convertLimit.compareTo(new BigDecimal("100000")) ==-1
                && convertLimit.compareTo(new BigDecimal("100")) ==1;
    }

    //CreditCards have a default interestRate of 0.2
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = isaBoolean1(interestRate)
                ? interestRate : new BigDecimal("0.02");
    }

    //CreditCards may be instantiated with an interestRate less than 0.2 but not lower than 0.1
    private boolean isaBoolean1(BigDecimal interestRate) {
        return interestRate.compareTo(new BigDecimal("0.5")) == -1
                && interestRate.compareTo(new BigDecimal("0.1")) == 1;
    }

/*
    // Interest on credit cards is added to the balance monthly.
    public Money applyInterest(Date date){

        BigDecimal interestApplied = interestRate.divide(new BigDecimal("12"));
        Long difference = date.getTime() - getCreationDate().getTime();
        BigDecimal months = BigDecimal.valueOf((difference / (1000l*60*60*24*30)));

        if (months.doubleValue()>=1 && getBalance().getAmount().doubleValue() > 0 ) {
            BigDecimal appliedInterest = BigDecimal.ONE.add(interestApplied).pow(months.intValue()).multiply(getBalance().getAmount());
            setBalance(new Money(appliedInterest));
        }
        return this.getBalance();
    }

    public void sendMoneyCreditCard(Money amount) throws Exception {

        Money newBalance = new Money(getBalance().increaseAmount(amount));
        if(newBalance.getAmount().doubleValue() <= creditLimit.getAmount().doubleValue()) {
            this.setBalance(newBalance);
        } else {
            throw new Exception("Exceeds credit limit");
        }
    }*/


        /*
    CreditCard accounts have a default creditLimit of 100. DONE
    CreditCards may be instantiated with a creditLimit higher than 100 but not higher than 100000. DONE
    CreditCards have a default interestRate of 0.2. DONE
    CreditCards may be instantiated with an interestRate less than 0.2 but not lower than 0.1

    Interest on credit cards is added to the balance monthly.
    If you have a 12% interest rate (0.12) then 1% interest will be added to the account monthly.
    When the balance of a credit card is accessed, check to determine if it has been 1 month or more since the account was created
    or since interested was added, and if so, add the appropriate interest to the balance.
     */


}
