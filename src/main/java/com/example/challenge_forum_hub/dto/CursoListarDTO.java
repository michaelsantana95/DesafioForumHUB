package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Categoria;

public record CursoListarDTO(

        Long id,

        String curso,

        Categoria categoria,

        int quantidadeTopicos) {}
