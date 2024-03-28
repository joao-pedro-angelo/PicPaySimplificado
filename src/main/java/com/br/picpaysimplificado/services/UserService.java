package com.br.picpaysimplificado.services;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.domain.users.UserType;
import com.br.picpaysimplificado.dtos.usersDTOs.CreateUserDTO;
import com.br.picpaysimplificado.dtos.usersDTOs.GetUserDTO;
import com.br.picpaysimplificado.infra.exceptions.NotFoundException;
import com.br.picpaysimplificado.infra.exceptions.ValidateException;
import com.br.picpaysimplificado.repositories.UserRepositorie;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public User findUserByCPF(String cpf){
        return this.repositorie.findUserByCPF(cpf).orElseThrow(NotFoundException::new);
    }

    public void updateUser(User user){
        this.repositorie.save(user);
    }

    public GetUserDTO createUser(CreateUserDTO userDTO){
        User user = new User(userDTO);
        this.repositorie.save(user);
        return new GetUserDTO(user);
    }

    public List<GetUserDTO> findAllUsers(){
        return this.repositorie.findAll()
                .stream()
                .map(GetUserDTO::new)
                .collect(Collectors.toList());
    }
}
