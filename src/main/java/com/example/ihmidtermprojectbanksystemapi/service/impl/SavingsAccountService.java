package com.example.ihmidtermprojectbanksystemapi.service.impl;


import com.example.ihmidtermprojectbanksystemapi.dto.SavingsDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Savings;
import com.example.ihmidtermprojectbanksystemapi.repository.SavingsRepository;
import org.springframework.stereotype.Service;

import static com.example.ihmidtermprojectbanksystemapi.model.utils.PasswordUtil.encryptedPassword;

@Service
public class SavingsAccountService {

    private final SavingsRepository savingsRepository;

    public SavingsAccountService(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }

    public Savings create(SavingsDTO savingsDTO) {
        Savings savings = new Savings(
                savingsDTO.getBalance(),
                savingsDTO.getPrimaryOwnerId(),
                savingsDTO.getSecondaryOwnerId(),
                encryptedPassword(savingsDTO.getSecretKey()),
                savingsDTO.getMinimumBalance(),
                savingsDTO.getInterestRate());

        return savingsRepository.save(savings);
    }
}
