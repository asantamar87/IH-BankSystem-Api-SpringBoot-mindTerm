package com.example.ihmidtermprojectbanksystemapi;

import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Admin;
import com.example.ihmidtermprojectbanksystemapi.model.utils.PasswordUtil;
import com.example.ihmidtermprojectbanksystemapi.repository.AccountHolderRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.AdminRepository;
import com.example.ihmidtermprojectbanksystemapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class BankSystemApiApplication implements CommandLineRunner {

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    RoleRepository repository;
    @Autowired
    AdminRepository adminRepository;



    public static void main(String[] args) {
        SpringApplication.run(BankSystemApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        adminRepository.save(new Admin("aldo",PasswordUtil.encryptedPassword("12345@")));
        accountHolderRepository.save(new AccountHolder("asantamar", PasswordUtil.encryptedPassword("1234"), LocalDate.of(2022,8, 2), null, null));
    }
}
