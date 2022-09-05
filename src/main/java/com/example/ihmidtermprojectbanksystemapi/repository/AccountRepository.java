package com.example.ihmidtermprojectbanksystemapi.repository;


import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //Optional<Account> findById(Long id);
    Optional<Account> findByPrimaryOwnerId (Long id);
    Optional<Account> findByPrimaryOwnerName (String name);
}
