package com.example.ihmidtermprojectbanksystemapi.model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Entity
public class Admin extends User{
    private String name;

    public Admin (@NotNull(message = "Username required") String username, @NotNull(message = "Password required") String password) {
        super(username, password);
        this.setRole("ADMIN");
    }
}
