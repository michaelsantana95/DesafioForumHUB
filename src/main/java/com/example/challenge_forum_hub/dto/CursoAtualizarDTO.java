package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Curso;
import jakarta.validation.constraints.Size;

public record CursoAtualizarDTO(

        @Size(max = 255, message = "Máximo 255 caracteres.")
        String curso) {

    public CursoAtualizarDTO(Curso curso) {
        this(curso.getCurso());
    }
}
