package com.example.ihmidtermprojectbanksystemapi.dto;


import com.example.ihmidtermprojectbanksystemapi.model.utils.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class AccountHolderDTO {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Address address;
    private Address mailingAddress;



}