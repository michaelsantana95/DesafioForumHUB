package com.example.challenge_forum_hub.controller;

import com.example.challenge_forum_hub.dto.CursoAtualizarDTO;
import com.example.challenge_forum_hub.dto.CursoNovoDTO;
import com.example.challenge_forum_hub.dto.CursoRetornoDTO;
import com.example.challenge_forum_hub.service.CursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @GetMapping()
    public ResponseEntity cursoListarTodos(@PageableDefault(size = 10, sort = {"curso"}) Pageable pageable){
        var page = cursoService.cursoListarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity cursoListar(@PathVariable Long id){
        var curso = cursoService.cursoListar(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity cursoNovo(@RequestBody @Valid CursoNovoDTO cursoNovoDTO) {
        var curso = cursoService.cursoNovo(cursoNovoDTO);
        var uri = URI.create("/cursos/" + curso.getId());
        var retorno = new CursoRetornoDTO(curso);
        return ResponseEntity.created(uri).body(retorno);
    }

    @PutMapping("/{id}")
    public ResponseEntity cursoAtualizar(@PathVariable Long id, @RequestBody @Valid CursoAtualizarDTO cursoAtualizarDTO){
        var curso = cursoService.cursoAtualizar(id, cursoAtualizarDTO);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cursoDeletar(@PathVariable Long id){
        cursoService.cursoDeletar(id);
        return ResponseEntity.noContent().build();
    }

}
