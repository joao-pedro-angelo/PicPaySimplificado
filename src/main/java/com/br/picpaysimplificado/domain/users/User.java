package com.br.picpaysimplificado.domain.users;

import com.br.picpaysimplificado.dtos.usersDtos.CreateUserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="Users")
@Table(name="tb_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(CreateUserDTO createUserDTO){
        this.firstName = createUserDTO.firstName();
        this.lastName = createUserDTO.lastName();
        this.cpf = createUserDTO.cpf();
        this.password = createUserDTO.password();
        this.balance = BigDecimal.ZERO;
        this.userType = createUserDTO.userType();
    }
}