package com.example.ihmidtermprojectbanksystemapi.repository;

import com.example.ihmidtermprojectbanksystemapi.model.account.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
}