package com.example.ihmidtermprojectbanksystemapi.dto;

import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDTO {
    private BigDecimal newBalance;
}