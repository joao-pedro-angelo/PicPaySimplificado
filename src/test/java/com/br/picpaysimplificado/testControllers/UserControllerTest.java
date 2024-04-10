package com.br.picpaysimplificado.testControllers;

import com.br.picpaysimplificado.domain.users.UserType;
import com.br.picpaysimplificado.dtos.usersDTOs.CreateUserDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CreateUserDTO> createUserDTOJacksonTester;

    @Test
    @DisplayName("Usuário com informação inválida - Bad Request")
    void createUserCase01Failed() throws Exception{
        var response = this.mockMvc.perform(post("/users"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Usuário com informações válidas - HTTP Created 201")
    void createUserCase02Created() throws Exception{
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "João",
                "Angelo",
                "112055802-42",
                "opa@gmail.com",
                "121210",
                UserType.COMMON
        );

        var response = this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.createUserDTOJacksonTester
                        .write(createUserDTO)
                        .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}
