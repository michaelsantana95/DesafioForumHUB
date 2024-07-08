package com.example.challenge_forum_hub.infra.exception;

public class Erro409Exception extends RuntimeException {
    public Erro409Exception(String mensagem) {
        super(mensagem);
    }
}