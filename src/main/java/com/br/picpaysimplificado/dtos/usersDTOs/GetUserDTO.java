package com.br.picpaysimplificado.dtos.usersDTOs;

import com.br.picpaysimplificado.domain.users.User;
import com.br.picpaysimplificado.domain.users.UserType;

import java.math.BigDecimal;

public record GetUserDTO(
        String firstName,
        String lastName,
        String cpf,
        String email,
        BigDecimal balance,
        UserType userType
) {
    public GetUserDTO(User user){
        this(user.getFirstName(),
                user.getLastName(),
                user.getCpf(),
                user.getEmail(),
                user.getBalance(),
                user.getUserType()
        );
    }
}
