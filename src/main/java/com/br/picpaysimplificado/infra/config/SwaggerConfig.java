package com.br.picpaysimplificado.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API RESTFUL PicPaySimplificado")
                        .description("Solução Desafio BackEnd PicPay")
                        .contact(new Contact()
                                .name("João Pedro")
                                .email("carneiroangelojoaopedro@gmail.com"))
                );
    }

}