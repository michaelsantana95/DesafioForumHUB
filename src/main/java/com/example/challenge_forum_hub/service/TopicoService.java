package com.example.challenge_forum_hub.service;

import com.example.challenge_forum_hub.dto.TopicoAtualizarDTO;
import com.example.challenge_forum_hub.dto.TopicoListarDTO;
import com.example.challenge_forum_hub.dto.TopicoNovoDTO;
import com.example.challenge_forum_hub.infra.exception.Erro404Exception;
import com.example.challenge_forum_hub.infra.exception.Erro409Exception;
import com.example.challenge_forum_hub.model.Categoria;
import com.example.challenge_forum_hub.model.Curso;
import com.example.challenge_forum_hub.model.Status;
import com.example.challenge_forum_hub.model.Topico;
import com.example.challenge_forum_hub.repository.CursoRepository;
import com.example.challenge_forum_hub.repository.RespostaRepository;
import com.example.challenge_forum_hub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    RespostaRepository respostaRepository;

    public List<TopicoListarDTO> topicoListarTodos(Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable);
        if (topicos.isEmpty()) throw new Erro404Exception("Nenhum tópico cadastrado.");
        return topicos.stream()
                .map(topico -> {
                    int quantidadeRespostas = respostaRepository.countByTopicoId(topico);
                    return new TopicoListarDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus().name(), topico.getAutor(), topico.getCurso().getCurso(), quantidadeRespostas);
                })
                .collect(Collectors.toList());
    }

    public TopicoListarDTO topicoListar(Long id) {
        var topico = topicoRepository.findById(id).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        int quantidadeRespostas = respostaRepository.countByTopicoId(topico);
        return new TopicoListarDTO(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus().name(), topico.getAutor(), topico.getCurso().getCurso(), quantidadeRespostas);
    }

    @Transactional
    public Topico topicoNovo(TopicoNovoDTO topicoNovoDTO){
        if (topicoRepository.findTopicoByTitulo(topicoNovoDTO.titulo()) != null) throw new Erro409Exception("Tópico já cadastrado.");
        if (topicoRepository.findTopicoByMensagem(topicoNovoDTO.mensagem()) != null) throw new Erro409Exception("Mensagem já cadastrada.");
        var topico = new Topico();
        topico.setTitulo(topicoNovoDTO.titulo());
        topico.setMensagem(topicoNovoDTO.mensagem());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus(Status.ABERTO);
        topico.setAutor(topicoNovoDTO.autor());
        var curso = cursoRepository.findCursoByCurso(topicoNovoDTO.curso());
        if (curso == null) {
            var novoCurso = new Curso();
            novoCurso.setCurso(topicoNovoDTO.curso());
            novoCurso.setCategoria(Categoria.PROGRAMACAO);
            cursoRepository.save(novoCurso);
            topico.setCurso(novoCurso);
        } else {
            topico.setCurso(curso);
        }
        return topicoRepository.save(topico);
    }

    @Transactional
    public TopicoAtualizarDTO topicoAtualizar(Long id, TopicoAtualizarDTO topicoAtualizarDTO) {
        var topico = topicoRepository.findById(id).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        if (Status.SOLUCIONADO.equals(topico.getStatus())) {
            throw new Erro409Exception("Não é possível atualizar um tópico já solucionado.");
        }
        if (topicoAtualizarDTO.titulo() != null) topico.setTitulo(topicoAtualizarDTO.titulo());
        if (topicoAtualizarDTO.mensagem() != null) topico.setMensagem(topicoAtualizarDTO.mensagem());
        if (topicoAtualizarDTO.autor() != null) topico.setAutor(topicoAtualizarDTO.autor()); //não acho correto atualizar o autor
        if (topicoAtualizarDTO.curso() != null) {
            var curso = cursoRepository.findCursoByCurso(topicoAtualizarDTO.curso());
            if (curso == null) {
                var novoCurso = new Curso();
                novoCurso.setCurso(topicoAtualizarDTO.curso());
                novoCurso.setCategoria(Categoria.PROGRAMACAO);
                cursoRepository.save(novoCurso);
                topico.setCurso(novoCurso);
            } else {
                topico.setCurso(curso);
            }
        }
        return new TopicoAtualizarDTO(topico);
    }

    @Transactional
    public void topicoDeletar(Long id) {
        var topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
        } else throw new Erro404Exception("Tópico não encontrado.");
    }

}