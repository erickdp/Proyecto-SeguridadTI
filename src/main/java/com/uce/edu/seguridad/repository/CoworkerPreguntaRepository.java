package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoworkerPreguntaRepository extends JpaRepository<CoworkerPregunta, Long> {

    @Query(value = "SELECT promedio_preguntas(:preguntaI, :universidadI);", nativeQuery = true)
    Double getAVGByPreguntaId(
            @Param("preguntaI") Long IdPregunta,
            @Param("universidadI") Long idUniversidad);

    @Query(value = "SELECT numero_participantes(:preguntaI, :universidadI);", nativeQuery = true)
    Long getCoworkerPerQuestion(@Param("preguntaI") Long IdPregunta,
                                @Param("universidadI") Long idUniversidad);
}
