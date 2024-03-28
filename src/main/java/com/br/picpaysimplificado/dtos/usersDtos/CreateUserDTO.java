package com.br.picpaysimplificado.dtos.usersDtos;

import com.br.picpaysimplificado.domain.users.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String cpf,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotNull
        UserType userType
) {
}
