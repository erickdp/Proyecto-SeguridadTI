package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;

import java.util.HashMap;
import java.util.List;

public interface CoworkerPreguntaService extends BaseService<CoworkerPregunta> {
    HashMap<String, Double> promedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas);
}
