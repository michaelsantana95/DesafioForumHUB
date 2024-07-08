package com.example.challenge_forum_hub.infra.exception;

public class Erro404Exception extends RuntimeException {
    public Erro404Exception(String mensagem) {
        super(mensagem);
    }
}