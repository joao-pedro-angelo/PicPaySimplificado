package com.br.picpaysimplificado.controllers;

import com.br.picpaysimplificado.dtos.transactionsDTOs.CreateTransactionDTO;
import com.br.picpaysimplificado.dtos.transactionsDTOs.GetTransactionDTO;
import com.br.picpaysimplificado.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    //@ApiOperation(value = "EndPoint criar transação")
    @PostMapping
    public ResponseEntity<GetTransactionDTO> createTransaction(
            @RequestBody @Valid CreateTransactionDTO createTransactionDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.createTransaction(createTransactionDTO));
    }
}
