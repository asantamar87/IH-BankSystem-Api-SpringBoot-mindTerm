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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Savings extends Account {

    private String secretKey; // This should be encrypted

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    @DecimalMin(value = "100", message = "Minimum balance to open a savings account is 100")
    private Money minimumBalance;

    @DecimalMax(value = "0.5", message = "Max interest rate is 0.5")
    private BigDecimal interestRate;

    public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public Savings(BigDecimal balance, Long primaryOwnerId, Long secondaryOwnerId, String secretKey, Money minimumBalance, BigDecimal interestRate) {
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance == null ? new Money(new BigDecimal("1000")) : minimumBalance;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate == null ? new BigDecimal("0.0025") : interestRate;
    }

    public void setSecretKey(String secretKey){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }

    @Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
        if(balance.compareTo(minimumBalance.getAmount())<0){
            super.setBalance(balance.subtract(getPenaltyFee().getAmount()));
        }
    }


     /*
    public Money applyInterest(Date date){

        //Difference between Current time and creation time of Saving Account
        Long difference = date.getTime() - getCreationDate().getTime();
        //Total of years
        Long years = ( difference / (1000l*60*60*24*365 ));

        if (years>=1) {
            BigDecimal appliedInterest = BigDecimal.ONE.add(interestRate).pow(years.intValue()).multiply(getBalance().getAmount());
            setBalance(new Money(appliedInterest));

            if (getBalance().getAmount().doubleValue() < minimumBalance.getAmount().doubleValue()) {
                getBalance().decreaseAmount(getPenaltyFee().getAmount());
            }
        }
        return this.getBalance();
    }




    Savings are identical to Checking accounts except that they
    Do NOT have a monthlyMaintenanceFee.
    Do have an interestRate.

    - Savings accounts have a default interest rate of 0.0025. DONE
    - Savings accounts may be instantiated with an interest rate other than the default,
    with a maximum interest rate of 0.5. DONE
    - Savings accounts should have a default minimumBalance of 1000. DONE
    - Savings accounts may be instantiated with a minimum balance of less than 1000
    but no lower than 100

    - Interest on savings accounts is added to the account annually at the rate
    of specified interestRate per year.

    That means that if I have 1000000 in a savings account with a 0.01 interest rate,
    1% of 1 Million is added to my account after 1 year.

    When a savings account balance is accessed, you must determine if it has been 1 year
    or more since either the account was created or since interest was added to the account,
    and add the appropriate interest to the balance if necessary. DONE

     */

}
