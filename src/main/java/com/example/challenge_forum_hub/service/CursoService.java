package com.example.challenge_forum_hub.service;

import com.example.challenge_forum_hub.dto.CursoAtualizarDTO;
import com.example.challenge_forum_hub.dto.CursoListarDTO;
import com.example.challenge_forum_hub.dto.CursoNovoDTO;
import com.example.challenge_forum_hub.infra.exception.Erro404Exception;
import com.example.challenge_forum_hub.infra.exception.Erro409Exception;
import com.example.challenge_forum_hub.model.Categoria;
import com.example.challenge_forum_hub.model.Curso;
import com.example.challenge_forum_hub.repository.CursoRepository;
import com.example.challenge_forum_hub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    TopicoRepository topicoRepository;

    public List<CursoListarDTO> cursoListarTodos(Pageable pageable) {
        var cursos = cursoRepository.findAll(pageable);
        if (cursos.isEmpty()) throw new Erro404Exception("Nenhum curso cadastrado.");
        return cursos.stream()
                .map(curso -> {
                    int quantidadeTopicos = topicoRepository.countByCursoId(curso.getId());
                    return new CursoListarDTO(curso.getId(), curso.getCurso(), curso.getCategoria(), quantidadeTopicos);
                })
                .collect(Collectors.toList());
    }

    public CursoListarDTO cursoListar(Long id) {
        var curso = cursoRepository.findById(id).orElseThrow(() -> new Erro404Exception("Curso não encontrado."));
        int quantidadeTopicos = topicoRepository.countByCursoId(curso.getId());
        return new CursoListarDTO(curso.getId(), curso.getCurso(), curso.getCategoria(), quantidadeTopicos);
    }

    @Transactional
    public Curso cursoNovo(CursoNovoDTO cursoNovoDTO){
        if (cursoRepository.findCursoByCurso(cursoNovoDTO.curso()) != null) throw new Erro409Exception("Curso já cadastrado.");
        var curso = new Curso();
        curso.setCurso(cursoNovoDTO.curso());
        curso.setCategoria(Categoria.PROGRAMACAO);
        return cursoRepository.save(curso);
    }

    @Transactional
    public CursoAtualizarDTO cursoAtualizar(Long id, CursoAtualizarDTO cursoAtualizarDTO) {
        var curso = cursoRepository.findById(id).orElseThrow(() -> new Erro404Exception("Curso não encontrado."));
        if (topicoRepository.existsTopicoByCursoId(id)) throw new Erro409Exception("Não é possível modificar cursos que possuem tópicos cadastrados.");
        if (cursoAtualizarDTO.curso() != null) curso.setCurso(cursoAtualizarDTO.curso());
        return new CursoAtualizarDTO(curso);
    }

    @Transactional
    public void cursoDeletar(Long id) {
        if (topicoRepository.existsTopicoByCursoId(id)) throw new Erro409Exception("Não é possível deletar cursos que possuem tópicos cadastrados.");
        var curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            cursoRepository.deleteById(id);
        } else throw new Erro404Exception("Curso não encontrado.");
    }

}