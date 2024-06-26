package com.br.picpaysimplificado.controllers;

import com.br.picpaysimplificado.dtos.usersDTOs.CreateUserDTO;
import com.br.picpaysimplificado.dtos.usersDTOs.GetUserDTO;
import com.br.picpaysimplificado.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<GetUserDTO> createUser(
            @RequestBody @Valid CreateUserDTO createUserDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.createUser(createUserDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetUserDTO>> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findAllUsers());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable String cpf){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetUserDTO(this.service.findUserByCPF(cpf)));
    }
}
