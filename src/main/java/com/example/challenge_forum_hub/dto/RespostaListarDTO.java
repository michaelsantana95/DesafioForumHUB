package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Resposta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RespostaListarDTO(

        Long id,

        String mensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        String autor,

        Boolean solucao) {

    public RespostaListarDTO(Resposta resposta){
        this(resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor(),
                resposta.getSolucao());
    }
}
