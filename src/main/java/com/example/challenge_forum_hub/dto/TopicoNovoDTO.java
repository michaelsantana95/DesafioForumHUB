package com.example.challenge_forum_hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicoNovoDTO(

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String titulo,

        @NotBlank
        @Size(max = 512, message = "Máximo 512 caracteres.")
        String mensagem,

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String autor,

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String curso) {}
