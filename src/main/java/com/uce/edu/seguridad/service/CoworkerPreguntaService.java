package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface CoworkerPreguntaService extends BaseService<CoworkerPregunta> {
    LinkedHashMap<String, String> promedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversidad);

    LinkedHashMap<String, Double> soloPromedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversidad);

    HashMap<String, Long> obtenerParticipantesPorPregunta(List<Pregunta> preguntasFiltradas, Long idUniversdida);

    HashMap<String, HashMap<String, Double>> promedioYPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversdida);

    void elimianarEntidad();
}
