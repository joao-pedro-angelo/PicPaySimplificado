package com.br.picpaysimplificado.dtos.transactionsDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDTO(
        @NotNull
        BigDecimal value,
        @NotBlank
        String cpfSender,
        @NotBlank
        String cpfReceiver
)
{}
