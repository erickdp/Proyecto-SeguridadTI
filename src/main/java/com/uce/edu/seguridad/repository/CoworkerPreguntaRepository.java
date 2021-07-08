package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoworkerPreguntaRepository extends JpaRepository<CoworkerPregunta, Long> {
    @Query("SELECT AVG(cp.calificacion) FROM CoworkerPregunta cp WHERE cp.pregunta.idPregunta = ?1 GROUP BY cp.pregunta.idPregunta")
    Double getAVGByPreguntaId(Long IdPregunta);
}
