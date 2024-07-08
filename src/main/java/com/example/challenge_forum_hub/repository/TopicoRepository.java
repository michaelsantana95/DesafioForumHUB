package com.example.challenge_forum_hub.repository;

import com.example.challenge_forum_hub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Topico findTopicoByTitulo(String titulo);

    Topico findTopicoByMensagem(String mensagem);

    boolean existsTopicoByCursoId(Long id);

    int countByCursoId(Long cursoId);

}
