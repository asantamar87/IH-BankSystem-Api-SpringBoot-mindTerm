package com.example.ihmidtermprojectbanksystemapi.model.account;

import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money transactionAmount;

    private LocalDate transactionTime;

    private Long senderId;
    private Long receiverId;

    public Transaction(Money transactionAmount, LocalDate transactionTime, Long senderId, Long receiverId) {
        this.transactionAmount = transactionAmount;
        this.transactionTime = transactionTime;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Transaction(Money transactionAmount, LocalDate transactionTime, Long senderId) {
        this.transactionAmount = transactionAmount;
        this.transactionTime = transactionTime;
        this.senderId = senderId;
    }

}
