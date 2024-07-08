package com.example.challenge_forum_hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CursoNovoDTO(

        @NotBlank
        @Size(max = 255, message = "Máximo 255 caracteres.")
        String curso) {}
