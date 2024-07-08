package com.example.challenge_forum_hub.infra.exception;

public class Erro401Exception extends RuntimeException {
    public Erro401Exception(String mensagem) {
        super(mensagem);
    }
}