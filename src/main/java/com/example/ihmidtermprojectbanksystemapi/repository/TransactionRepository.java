package com.example.ihmidtermprojectbanksystemapi.repository;

import com.example.ihmidtermprojectbanksystemapi.model.account.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Long> {
}
