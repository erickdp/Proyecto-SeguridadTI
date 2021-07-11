package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;

import java.util.List;

public interface CoworkerService extends BaseService<Coworker> {
    List<Coworker> buscarPorUnivesidad(Long id);

    Coworker calificarPregunta(Coworker coworker, Pregunta pregunta, Integer calificacion);

    void agreagarNuevaPregunta(Pregunta pregunta);

    Coworker setearPreguntas(Coworker coworker, List<Pregunta> preguntaList);

    Coworker buscarCowokerPorId(Long id);

    Coworker actualizarCalificacion(Long idCoworker, List<CoworkerPregunta> preguntas);

    Coworker buscarCoworkerPorNombreUsuario(String nombreUsuario);
}
