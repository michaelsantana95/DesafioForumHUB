package com.example.challenge_forum_hub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpingDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Challenge Forum Hub - Jhonathan Pfeiffer Urbainski")
                        .description("API Rest do Challenge Forum Hub para o programa ONE (Oracle Next Education) em parceria com Alura.")
                        .contact(new Contact()
                                .name("Jhonathan Pfeiffer Urbainski")
                                .url("https://github.com/jhonathan-p")));
    }

}