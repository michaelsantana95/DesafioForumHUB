package com.example.challenge_forum_hub.infra.exception;

public class Erro500Exception extends RuntimeException {
    public Erro500Exception(String mensagem) {
        super(mensagem);
    }
}