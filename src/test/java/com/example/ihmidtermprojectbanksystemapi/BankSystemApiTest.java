package com.example.ihmidtermprojectbanksystemapi;

import com.example.ihmidtermprojectbanksystemapi.dto.MoneyDTO;
import com.example.ihmidtermprojectbanksystemapi.dto.TransactionDTO;
import com.example.ihmidtermprojectbanksystemapi.model.account.*;
import com.example.ihmidtermprojectbanksystemapi.model.utils.*;
import com.example.ihmidtermprojectbanksystemapi.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BankSystemApiTest {

    @MockBean
    private BankSystemApiApplication bankSystemApiApplication;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer()) ;

    Gson gson = gsonBuilder.setPrettyPrinting().create();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    AccountHolder accountHolder;
    AccountHolder accountHolder2;
    AccountHolder accountHolder3;
    List<Transaction> transactions = new ArrayList<>();
    List<Transaction> transactions2 = new ArrayList<>();
    Address address;
    Address address2;
    Checking account;
    Checking account5;
    StudentChecking account2;
    Savings account3;
    CreditCard account4;


    @BeforeEach
    void setup(){

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        address = new Address(34, "Av Diagonal", "08023", "Barcelona", "Spain");
        address2 = new Address(59, " New Road", "Ac094", "London", "United Kingdom");
        accountHolder = new AccountHolder("Aldo SantaMaria", LocalDate.of(1988,10,21), address);
        accountHolder2 = new AccountHolder("Pedro Alcacer", LocalDate.of(1975,8,12), address2);
        accountHolder3 = new AccountHolder("Maria Piert", LocalDate.of(2000,9,12), address2);
        accountHolderRepository.saveAll(List.of(accountHolder, accountHolder2, accountHolder3));
        account = new Checking(234578784L, new BigDecimal("1000"),
                LocalDate.of(2020,4,2), accountHolder,"567478L",new Money(new BigDecimal("150")));
        checkingRepository.save(account);
    }


    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        creditCardRepository.deleteAll();
        checkingRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void index() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/index")).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Welcome to Bank System of la Caja!"));

    }
    @Test
    void contextLoads() {
    }

    @Test
    void createStudentChecking() throws Exception {
        LocalDate date = LocalDate.of(2021,9,22);
        //Implement is necessary
        account2 = new StudentChecking();
        String body = objectMapper.writeValueAsString(account2);
        MvcResult result = mockMvc.perform(
                post("/accounts/create/studentchecking")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Maria Piert"));
    }
    @Test
    void createNewChecking() throws Exception {
        LocalDate date = LocalDate.of(2021,9,2);

        account5 = new Checking(214578774L, new BigDecimal("2000"), date, accountHolder2,"569378L", new Money(new BigDecimal("300")));

        String body = gson.toJson(account5);
        MvcResult result = mockMvc.perform(
                post("/accounts/create/checking")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Aldo SantaMaria"));
    }

    @Test
    void createNewCreditCard() throws Exception {

        LocalDate date= LocalDate.of(2021,9,22);
        //Implement is necessary.
        account4 = new CreditCard();
        String body = objectMapper.writeValueAsString(account4);
        MvcResult result = mockMvc.perform(
                post("/accounts/create/creditcard")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Aldo SantaMaria"));
    }

    @Test
    void createNewSavings() throws Exception {

        LocalDate.of(2021,9,22);
        //Implement is necessary.
        account3 = new Savings();
        String body = objectMapper.writeValueAsString(account3);
        MvcResult result = mockMvc.perform(
                post("/accounts/create/savings")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Aldo SantaMaria"));
    }

    @Test
    void updateBalance() throws Exception {
        Account account5 = new Account();
        account5.setBalance(new BigDecimal("1050"));
        String body = objectMapper.writeValueAsString(account5.getBalance().getAmount());
        MvcResult result = mockMvc.perform(
                patch("/accounts/" + account.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(account5.getBalance().getAmount(), accountRepository.findById(account.getId()).get().getBalance().getAmount());

    }

    @Test
    void getAllAccounts() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/all")).andExpect(status().isOk()).andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("Pedro Alcacer"));
    }

    @Test
    void receiveMoney() throws Exception {
        TransactionDTO transaction2 = new TransactionDTO(new Money(new BigDecimal("30")), account.getId(), account.getPrimaryOwner().getName());
        String body = objectMapper.writeValueAsString(transaction2);
        MvcResult result = mockMvc.perform(
                post("/accounts/receivemoney")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(transaction2.getAccountId().toString()));
    }


    @Test
    void sendMoney_NoError() throws Exception {
        TransactionDTO transaction = new TransactionDTO(new Money(new BigDecimal("30")), account.getId(), account.getPrimaryOwner().getName());
        String body = objectMapper.writeValueAsString(transaction);
        MvcResult result = mockMvc.perform(
                post("/accounts/send")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(transaction.getAccountId().toString()));
    }

}
