package com.example.challenge_forum_hub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TopicoListarDTO(

        Long id,

        String titulo,

        String mensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        String status,

        String autor,

        String curso,

        int quantidadeRespostas) {}