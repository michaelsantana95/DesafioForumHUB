package com.example.challenge_forum_hub.service;

import com.example.challenge_forum_hub.dto.RespostaAtualizarDTO;
import com.example.challenge_forum_hub.dto.RespostaListarDTO;
import com.example.challenge_forum_hub.dto.RespostaNovoDTO;
import com.example.challenge_forum_hub.infra.exception.Erro404Exception;
import com.example.challenge_forum_hub.infra.exception.Erro409Exception;
import com.example.challenge_forum_hub.model.Resposta;
import com.example.challenge_forum_hub.model.Status;
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
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    TopicoRepository topicoRepository;

    public List<RespostaListarDTO> respostaListarTodos(Pageable pageable, Long topicoId) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        var respostas = respostaRepository.findByTopicoId(pageable, topico);
        if (respostas.isEmpty() ) throw new Erro404Exception("Nenhuma resposta cadastrada para esse tópico.");
        return respostas.stream()
                .map(RespostaListarDTO::new)
                .collect(Collectors.toList());
    }

    public RespostaListarDTO respostaListar(Long topicoId, Long id) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        var resposta = respostaRepository.findByTopicoIdAndId(topico, id).orElseThrow(() -> new Erro404Exception("Resposta não encontrada para esse tópico."));
        return new RespostaListarDTO(resposta);
    }

    @Transactional
    public Resposta respostaNovo(Long topicoId, RespostaNovoDTO respostaNovoDTO){
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        if (Status.SOLUCIONADO.equals(topico.getStatus())) {
            throw new Erro409Exception("Tópico já solucionado.");
        }
        if (respostaRepository.findRespostaByTopicoIdAndMensagem(topico, respostaNovoDTO.mensagem()) != null) throw new Erro409Exception("Resposta já cadastrada para esse tópico.");
        var resposta = new Resposta();
        resposta.setTopicoId(topico);
        resposta.setMensagem(respostaNovoDTO.mensagem());
        resposta.setDataCriacao(LocalDateTime.now());
        resposta.setAutor(respostaNovoDTO.autor());
        resposta.setSolucao(false);
        return respostaRepository.save(resposta);
    }

    @Transactional
    public RespostaAtualizarDTO respostaAtualizar(Long topicoId, Long id, RespostaAtualizarDTO respostaAtualizarDTO) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        if (Status.SOLUCIONADO.equals(topico.getStatus())) {
            throw new Erro409Exception("Não é possível atualizar respostas de um tópico já solucionado.");
        }
        var resposta = respostaRepository.findByTopicoIdAndId(topico, id).orElseThrow(() -> new Erro404Exception("Resposta não encontrada para esse tópico."));
        if (respostaAtualizarDTO.mensagem() != null) resposta.setMensagem(respostaAtualizarDTO.mensagem());
        return new RespostaAtualizarDTO(resposta);
    }

    @Transactional
    public void respostaDeletar(Long topicoId, Long id) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        if (Status.SOLUCIONADO.equals(topico.getStatus())) {
            throw new Erro409Exception("Não é possível deletar respostas de um tópico já solucionado.");
        }
        var resposta = respostaRepository.findByTopicoIdAndId(topico, id).orElseThrow(() -> new Erro404Exception("Resposta não encontrada para esse tópico."));
        respostaRepository.deleteByTopicoIdAndId(topico, id);
    }

    @Transactional
    public void respostaSolucao(Long topicoId, Long id) {
        var topico = topicoRepository.findById(topicoId).orElseThrow(() -> new Erro404Exception("Tópico não encontrado."));
        if (respostaRepository.existsByTopicoIdAndSolucao(topico, true)) {
            throw new Erro409Exception("Já existe uma resposta marcada como solução para este tópico.");
        }
        var resposta = respostaRepository.findByTopicoIdAndId(topico, id).orElseThrow(() -> new Erro404Exception("Resposta não encontrada para esse tópico."));
        resposta.setSolucao(true);
        topico.setStatus(Status.SOLUCIONADO);
    }

}