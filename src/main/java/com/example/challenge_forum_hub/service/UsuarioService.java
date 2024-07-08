package com.example.challenge_forum_hub.service;

import com.example.challenge_forum_hub.dto.UsuarioNovoDTO;
import com.example.challenge_forum_hub.infra.exception.Erro409Exception;
import com.example.challenge_forum_hub.model.Perfil;
import com.example.challenge_forum_hub.model.Usuario;
import com.example.challenge_forum_hub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario usuarioNovo(UsuarioNovoDTO usuarioNovoDTO) {
        if (usuarioRepository.findByEmail(usuarioNovoDTO.email()) != null) {
            throw new Erro409Exception("Usuário já cadastrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioNovoDTO.nome());
        usuario.setEmail(usuarioNovoDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioNovoDTO.senha()));
        usuario.setPerfil(Perfil.USER);
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

}