package com.br.picpaysimplificado.repositories;

import com.br.picpaysimplificado.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByCPF(String CPF);
}
