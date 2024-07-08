package com.example.challenge_forum_hub.controller;

import com.example.challenge_forum_hub.dto.UsuarioLoginDTO;
import com.example.challenge_forum_hub.dto.UsuarioNovoDTO;
import com.example.challenge_forum_hub.dto.UsuarioRetornoDTO;
import com.example.challenge_forum_hub.infra.security.TokenDTO;
import com.example.challenge_forum_hub.infra.security.TokenService;
import com.example.challenge_forum_hub.model.Usuario;
import com.example.challenge_forum_hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity usuarioLogin(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email(), usuarioLoginDTO.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @PostMapping("/novo")
    public ResponseEntity usuarioNovo(@RequestBody @Valid UsuarioNovoDTO usuarioNovoDTO) {
        Usuario usuario = usuarioService.usuarioNovo(usuarioNovoDTO);
        var retorno = new UsuarioRetornoDTO(usuario);
        return ResponseEntity.ok(retorno);
    }

}
