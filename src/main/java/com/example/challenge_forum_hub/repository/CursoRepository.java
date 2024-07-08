package com.example.challenge_forum_hub.repository;

import com.example.challenge_forum_hub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findCursoByCurso(String curso);

}
