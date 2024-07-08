package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Topico;
import jakarta.validation.constraints.Size;

public record TopicoAtualizarDTO(

        @Size(max = 255, message = "Máximo 255 caracteres.")
        String titulo,

        @Size(max = 512, message = "Máximo 512 caracteres.")
        String mensagem,

        @Size(max = 255, message = "Máximo 255 caracteres.")
        String autor,

        @Size(max = 255, message = "Máximo 255 caracteres.")
        String curso) {

    public TopicoAtualizarDTO(Topico topico) {
        this(topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor(),
                topico.getCurso().getCurso());
    }
}
