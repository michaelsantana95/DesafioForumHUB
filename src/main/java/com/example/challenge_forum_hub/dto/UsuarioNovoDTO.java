package com.example.challenge_forum_hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioNovoDTO(

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String nome,

        @NotBlank
        @Email
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String email,

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String senha) {}
