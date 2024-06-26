package com.br.picpaysimplificado.services;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.domain.users.UserType;
import com.br.picpaysimplificado.dtos.usersDTOs.CreateUserDTO;
import com.br.picpaysimplificado.dtos.usersDTOs.GetUserDTO;
import com.br.picpaysimplificado.infra.exceptions.NotFoundException;
import com.br.picpaysimplificado.infra.exceptions.ValidateException;
import com.br.picpaysimplificado.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount){
        if (sender.getUserType() == UserType.MERCHANT){
            throw new ValidateException("Lojista não pode enviar transação!");
        } if (sender.getBalance().compareTo(amount) < 0){
            throw new ValidateException("Saldo insuficiente.");
        }
    }

    public User findUserByCPF(String CPF){
        return this.repository.findUserByCPF(CPF).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void updateUser(User user){
        this.repository.save(user);
    }

    @Transactional
    public GetUserDTO createUser(CreateUserDTO userDTO){
        User user = new User(userDTO);
        this.repository.save(user);
        return new GetUserDTO(user);
    }

    public List<GetUserDTO> findAllUsers(){
        return this.repository.findAll()
                .stream()
                .map(GetUserDTO::new)
                .collect(Collectors.toList());
    }
}
