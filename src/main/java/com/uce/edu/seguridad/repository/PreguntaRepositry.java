package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreguntaRepositry extends JpaRepository<Pregunta, Long> {
    @Query("SELECT p FROM Pregunta p WHERE p.formulario.idFormulario = ?1")
    List<Pregunta> findPreguntasByFormulario(Long idFormulario);
}
