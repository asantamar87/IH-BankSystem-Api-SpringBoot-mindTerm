package com.example.ihmidtermprojectbanksystemapi.repository;

import com.example.ihmidtermprojectbanksystemapi.model.account.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking,Long> {
}
