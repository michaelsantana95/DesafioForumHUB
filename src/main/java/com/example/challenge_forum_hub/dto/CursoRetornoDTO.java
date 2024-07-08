package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Categoria;
import com.example.challenge_forum_hub.model.Curso;

public record CursoRetornoDTO(

        Long id,

        String curso,

        Categoria categoria) {

    public CursoRetornoDTO(Curso curso){
        this(curso.getId(),
                curso.getCurso(),
                curso.getCategoria());
    }
}
