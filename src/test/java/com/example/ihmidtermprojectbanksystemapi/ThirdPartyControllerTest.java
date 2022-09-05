package com.example.ihmidtermprojectbanksystemapi;


import com.example.ihmidtermprojectbanksystemapi.dto.TransactionDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.Account;
import com.example.ihmidtermprojectbanksystemapi.model.account.Savings;
import com.example.ihmidtermprojectbanksystemapi.model.utils.AccountHolder;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Address;
import com.example.ihmidtermprojectbanksystemapi.model.utils.Money;
import com.example.ihmidtermprojectbanksystemapi.model.utils.ThirdParty;
import com.example.ihmidtermprojectbanksystemapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {
    @MockBean
    private BankSystemApiApplication bankSystemApiApplication;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private ThirdParty thirdParty;
    private ThirdParty thirdParty2;
    private Account savings1;
    AccountHolder accountHolder;
    Address address;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address = new Address(1, "Aldo SantaMaria", "09027", "Barcelona", "Spain");
        accountHolder = new AccountHolder("Maria Rodriguez", LocalDate.of(1986,5,15), address);
        accountHolderRepository.save(accountHolder);
        thirdParty = new ThirdParty("th567483jnskt679", "Jose Paredes");

        thirdPartyRepository.save(thirdParty);

        //Implementar
        savings1 =new Savings();

        accountRepository.save(savings1);

        LocalDate.of(2020,4,20);


    }

    @AfterEach
    void tearDown(){
        thirdPartyRepository.deleteAll();
        savingsRepository.deleteAll();
        transactionRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addParty_Positive() throws Exception{
        thirdParty2 = new ThirdParty("th567483jnmlkt680", "Pizza Hut");
        String body = objectMapper.writeValueAsString(thirdParty2);
        MvcResult result = mockMvc.perform(
                post("/thirdparty")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Pizza Hut"));

    }

    @Test
    void sendMoney_NoError() throws Exception {
        //Implements is necessary.
        TransactionDTO transaction = new TransactionDTO();
        String body = objectMapper.writeValueAsString(transaction);
        MvcResult result = mockMvc.perform(
                post("/thirdparty/sendmoney")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(transaction.getAccountId().toString()));
    }

    @Test
    void receiveMoney_NoError() throws Exception {
        //Implements is necessary.
        TransactionDTO transaction2 = new TransactionDTO();
        String body = objectMapper.writeValueAsString(transaction2);
        MvcResult result = mockMvc.perform(
                post("/thirdparty/receivemoney")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(transaction2.getAccountId().toString()));
    }
}