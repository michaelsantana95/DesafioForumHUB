package com.example.challenge_forum_hub.infra.exception;

public class Erro400Exception extends RuntimeException {
    public Erro400Exception(String mensagem) {
        super(mensagem);
    }
}