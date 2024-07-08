package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Topico;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TopicoRetornoDTO(

        Long id,

        String titulo,

        String mensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        String autor,

        String curso) {

    public TopicoRetornoDTO(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor(),
                topico.getCurso().getCurso());
    }
}
