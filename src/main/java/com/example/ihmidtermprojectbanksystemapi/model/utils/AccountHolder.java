package com.example.ihmidtermprojectbanksystemapi.model.utils;


import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{

    private String name;
    private LocalDate dateOfBirth;

    @AttributeOverrides({
            @AttributeOverride(name="houseNumber",column=@Column(name="current_house_number")),
            @AttributeOverride(name="streetName",column=@Column(name="current_street_name")),
            @AttributeOverride(name="postcode",column=@Column(name="current_postcode")),
            @AttributeOverride(name="city",column=@Column(name="current_city")),
            @AttributeOverride(name="country",column=@Column(name="current_country"))
    })

    @Embedded
    @Valid
    private Address primaryAddress;



    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "mailing_houseNumber")),
            @AttributeOverride(name = "streetName", column = @Column(name = "mailing_street_name")),
            @AttributeOverride(name = "postcode", column = @Column(name = "mailing_postcode")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country"))
    })
    private Address mailingAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsPrimary;

    @JsonIgnore
    @Nullable
    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsSecondary;



    public AccountHolder(String username, String password, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress= primaryAddress;
        this.mailingAddress = mailingAddress;
        super.setRole("ACCOUNT_HOLDER");

    }

    public AccountHolder(String name, LocalDate dateOfBirth, Address primaryAddress) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }


}
