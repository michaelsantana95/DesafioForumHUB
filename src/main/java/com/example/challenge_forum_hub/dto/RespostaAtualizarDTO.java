package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Resposta;
import jakarta.validation.constraints.Size;

public record RespostaAtualizarDTO(

        @Size(max = 512, message = "Máximo 512 caracteres.")
        String mensagem) {

    public RespostaAtualizarDTO(Resposta resposta){
        this(resposta.getMensagem());
    }
}
