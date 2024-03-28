package com.br.picpaysimplificado.services;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.domain.users.UserType;
import com.br.picpaysimplificado.infra.exceptions.ValidateException;
import com.br.picpaysimplificado.repositories.UserRepositorie;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private UserRepositorie repositorie;

    public void validateTransaction(User sender, BigDecimal amount){
        if (sender.getUserType() == UserType.MERCHANT){
            throw new ValidateException("Lojista não pode enviar transação!");
        } if (sender.getBalance().compareTo(amount) < 0){
            throw new ValidateException("Saldo insuficiente.");
        }
    }
}
