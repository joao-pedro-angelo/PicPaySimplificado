package com.br.picpaysimplificado.testControllers;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.domain.users.UserType;
import com.br.picpaysimplificado.dtos.transactionsDTOs.CreateTransactionDTO;
import com.br.picpaysimplificado.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CreateTransactionDTO> createTransactionDTOJacksonTester;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User sender = new User();
        sender.setFirstName("João");
        sender.setLastName("Angelo");
        sender.setEmail("angelo@gmail.com");
        sender.setCPF("112097904-49");
        sender.setPassword("123");
        sender.setUserType(UserType.COMMON);
        sender.setBalance(BigDecimal.TEN);
        this.userRepository.save(sender);

        User receiver = new User();
        receiver.setFirstName("Victor");
        receiver.setLastName("Angelo");
        receiver.setEmail("vangelo@gmail.com");
        receiver.setCPF("112057904-50");
        receiver.setPassword("125");
        receiver.setUserType(UserType.MERCHANT);
        receiver.setBalance(BigDecimal.ZERO);
        this.userRepository.save(receiver);
    }

    @AfterEach
    void tearDown(){
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("Transação falha - dados inválidos")
    void createTransactionFailed() throws Exception{
        var response = this.mockMvc.perform(post("/transactions"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Transação OK - Common to Merchant")
    void createTransactionCase01Sucess() throws Exception{
        CreateTransactionDTO transactionDTO = new CreateTransactionDTO(
                BigDecimal.TWO,
                "112097904-49",
                "112057904-50");

        var response = this.mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.createTransactionDTOJacksonTester
                        .write(transactionDTO)
                        .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Transação Failed - Merchant to Common")
    void createTransactionCase02Failed() throws Exception{
        CreateTransactionDTO transactionDTO = new CreateTransactionDTO(
                BigDecimal.TWO,
                "112057904-50",
                "112097904-49");

        var response = this.mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.createTransactionDTOJacksonTester
                                .write(transactionDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Transação Failed - Saldo insuficiente")
    void createTransactionCase03Failed() throws Exception{
        CreateTransactionDTO transactionDTO = new CreateTransactionDTO(
                BigDecimal.valueOf(2000),
                "112097904-49",
                "112057904-50");

        var response = this.mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.createTransactionDTOJacksonTester
                                .write(transactionDTO)
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
