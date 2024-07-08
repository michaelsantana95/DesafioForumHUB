package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Resposta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RespostaRetornoDTO(

        Long id,

        String mensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        String autor) {

        public RespostaRetornoDTO(Resposta resposta) {
                this(resposta.getId(),
                        resposta.getMensagem(),
                        resposta.getDataCriacao(),
                        resposta.getAutor());
        }

}