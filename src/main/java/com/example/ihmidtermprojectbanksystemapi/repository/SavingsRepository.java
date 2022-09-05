package com.example.ihmidtermprojectbanksystemapi.repository;

import com.example.ihmidtermprojectbanksystemapi.model.account.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
