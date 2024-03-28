package com.br.picpaysimplificado.dtos.transactionsDTOs;

import com.br.picpaysimplificado.domain.transactions.Transaction;
import com.br.picpaysimplificado.domain.users.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetTransactionDTO(
        Long id,
        BigDecimal amount,
        User sender,
        User receiver,
        LocalDateTime timestamp
) {
    public GetTransactionDTO(Transaction transaction){
        this(transaction.getId(),
                transaction.getAmount(),
                transaction.getSender(),
                transaction.getReceiver(),
                transaction.getTimestamp());
    }
}
