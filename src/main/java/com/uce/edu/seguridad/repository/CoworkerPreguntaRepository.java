package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoworkerPreguntaRepository extends JpaRepository<CoworkerPregunta, Long> {

    @Query(value = "call promedio_preguntas(:pregunta, :universidad);", nativeQuery = true)
    Double getAVGByPreguntaId(
            @Param("pregunta") Long IdPregunta,
            @Param("universidad") Long idUniversidad);

    @Query(value = "call numero_participantes(:pregunta, :universidad);", nativeQuery = true)
    Long getCoworkerPerQuestion(@Param("pregunta") Long IdPregunta,
                                @Param("universidad") Long idUniversidad);
}
