package com.example.ihmidtermprojectbanksystemapi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CheckingDTO {

    private BigDecimal balance;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    private String secretKey;

}