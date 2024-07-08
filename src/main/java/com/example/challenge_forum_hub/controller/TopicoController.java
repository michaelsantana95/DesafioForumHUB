package com.example.challenge_forum_hub.controller;

import com.example.challenge_forum_hub.dto.TopicoAtualizarDTO;
import com.example.challenge_forum_hub.dto.TopicoNovoDTO;
import com.example.challenge_forum_hub.dto.TopicoRetornoDTO;
import com.example.challenge_forum_hub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    TopicoService topicoService;

    @GetMapping()
    public ResponseEntity topicoListarTodos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable pageable){
        var page = topicoService.topicoListarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity topicoListar(@PathVariable Long id){
        var topico = topicoService.topicoListar(id);
        return ResponseEntity.ok(topico);
    }

    @PostMapping
    public ResponseEntity topicoNovo(@RequestBody @Valid TopicoNovoDTO topicoNovoDTO) {
        var topico = topicoService.topicoNovo(topicoNovoDTO);
        var uri = URI.create("/topicos/" + topico.getId());
        var retorno = new TopicoRetornoDTO(topico);
        return ResponseEntity.created(uri).body(retorno);
    }

    @PutMapping("/{id}")
    public ResponseEntity topicoAtualizar(@PathVariable Long id, @RequestBody @Valid TopicoAtualizarDTO topicoAtualizarDTO){
        var topico = topicoService.topicoAtualizar(id, topicoAtualizarDTO);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity topicoDeletar(@PathVariable Long id){
        topicoService.topicoDeletar(id);
        return ResponseEntity.noContent().build();
    }

}
