package com.example.challenge_forum_hub.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO(

        @NotBlank
        String email,

        @NotBlank
        String senha) {}
