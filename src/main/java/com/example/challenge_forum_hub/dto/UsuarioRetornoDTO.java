package com.example.challenge_forum_hub.dto;

import com.example.challenge_forum_hub.model.Usuario;

public record UsuarioRetornoDTO(

        String nome,

        String login) {

    public UsuarioRetornoDTO(Usuario usuario){
        this(usuario.getNome(),
                usuario.getUsername());
    }
}
