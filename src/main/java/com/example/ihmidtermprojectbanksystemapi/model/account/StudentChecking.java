package com.example.ihmidtermprojectbanksystemapi.model.account;


import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import java.math.BigDecimal;

/*
 Student Checking Accounts are identical to Checking Accounts except that they do NOT have:
  A monthlyMaintenanceFee
  A minimumBalance
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentChecking extends Account {

    private String secretKey;

    public StudentChecking(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }

    // The secretKey should be encrypted.
    public void setSecretKey(String secretKey){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.secretKey = passwordEncoder.encode(secretKey);
    }


}
