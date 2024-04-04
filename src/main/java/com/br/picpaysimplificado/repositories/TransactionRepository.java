package com.br.picpaysimplificado.repositories;

import com.br.picpaysimplificado.domain.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionById(Long id);
}
