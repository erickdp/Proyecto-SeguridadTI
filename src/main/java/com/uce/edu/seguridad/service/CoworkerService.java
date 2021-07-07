package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.Pregunta;

import java.util.List;

public interface CoworkerService extends BaseService<Coworker> {
    Coworker buscarPorMailInstitucional(String mailInstitucional);

    List<Coworker> buscarPorUnivesidad(Long id);

    Coworker calificarPregunta(Coworker coworker, Pregunta pregunta, Integer calificacion);
}
