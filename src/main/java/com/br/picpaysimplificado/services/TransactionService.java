package com.br.picpaysimplificado.services;

import com.br.picpaysimplificado.domain.transactions.Transaction;
import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.dtos.transactionsDTOs.CreateTransactionDTO;
import com.br.picpaysimplificado.infra.exceptions.ValidateException;
import com.br.picpaysimplificado.repositories.TransactionRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepositorie repositorie;
    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(CreateTransactionDTO transactionDTO){
        User sender = this.userService.findUserByCPF(transactionDTO.cpfSender());
        User receiver = this.userService.findUserByCPF(transactionDTO.cpfReceiver());
        this.userService.validateTransaction(sender, transactionDTO.value());

        if (this.authorizeTransaction(sender, transactionDTO.value())){
            Transaction transaction = new Transaction();
            transaction.setAmount(transactionDTO.value());
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setTimestamp(LocalDateTime.now());

            sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
            receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

            this.repositorie.save(transaction);
            this.userService.updateUser(sender);
            this.userService.updateUser(receiver);
        } else throw new ValidateException("Transação não autorizada.");
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorize = this.restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorize.getStatusCode() == HttpStatus.OK){
            String message = (String) Objects.requireNonNull(authorize.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
