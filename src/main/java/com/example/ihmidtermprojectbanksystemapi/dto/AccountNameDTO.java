package com.example.ihmidtermprojectbanksystemapi.dto;

import javax.validation.constraints.NotEmpty;

public class AccountNameDTO {

    @NotEmpty(message = "Account name can't be empty")
    private String name;

    public AccountNameDTO(String name) {
        this.name = name.replace("-", " ");
    }

    public AccountNameDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}