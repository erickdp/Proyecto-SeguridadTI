package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.CoworkerPreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CoworkerPreguntaServiceImp implements CoworkerPreguntaService {

    @Autowired
    private CoworkerPreguntaRepository coworkerPreguntaRepository;

    @Override
    @Transactional
    public CoworkerPregunta guardar(CoworkerPregunta entidad) {
        return this.coworkerPreguntaRepository.save(entidad);
    }

    @Override
    public CoworkerPregunta consultarPorId(Long id) {
        return this.coworkerPreguntaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoworkerPregunta> listarEntidad() {
        return this.coworkerPreguntaRepository.findAll();
    }

    @Override
    public CoworkerPregunta actualizarEntidad(CoworkerPregunta entidad) {
        return null;
    }

    // Se necesita mayor cohesion y bajo acoplamiento en estos metodos de servicio
    @Override
    public LinkedHashMap<String, String> promedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversidad) {
        var promedioPreguntas = new LinkedHashMap<String, String>();
        int i = 1;
        for (Pregunta p :
                preguntasFiltradas) {
            var avgDB = this.coworkerPreguntaRepository.getAVGByPreguntaId(p.getIdPregunta(), idUniversidad);
            if (avgDB != null) { // Cuando todas las preguntas tienen calificacion 0 devuelve null pero en eralidad seria avg 0
                promedioPreguntas.put("pregunta " + i, p.getPregunta());
                promedioPreguntas.put("promedio " + i++, String.valueOf(avgDB));
            } else {
                promedioPreguntas.put("pregunta " + i, p.getPregunta());
                promedioPreguntas.put("promedio " + i++, "0.0");
            }
        }
        return promedioPreguntas;
    }

    @Override
    public LinkedHashMap<String, Double> soloPromedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversidad) {
        var promedioPreguntas = new LinkedHashMap<String, Double>();
        int i = 1;
        for (Pregunta p :
                preguntasFiltradas) {
            var avgDB = this.coworkerPreguntaRepository.getAVGByPreguntaId(p.getIdPregunta(), idUniversidad);
            if (avgDB != null) { // Cuando todas las preguntas tienen calificacion 0 devuelve null pero en eralidad seria avg 0
                promedioPreguntas.put("promedio " + i++, avgDB);
            } else {
                promedioPreguntas.put("promedio " + i++, 0d);
            }
        }
        return promedioPreguntas;
    }

    // Se necesita mayor cohesion y bajo acoplamiento en estos metodos de servicio
    @Override
    public HashMap<String, Long> obtenerParticipantesPorPregunta(List<Pregunta> preguntasFiltradas, Long idUniversdida) {
        var participantes = new HashMap<String, Long>();
        for (Pregunta p :
                preguntasFiltradas) {
            var totalParticipantes = this.coworkerPreguntaRepository.getCoworkerPerQuestion(p.getIdPregunta(), idUniversdida);
            if (totalParticipantes != null) { // Cuando todas las preguntas tienen calificacion 0 devuelve null pero en eralidad seria avg 0
                participantes.put(p.getPregunta(), totalParticipantes);
            } else {
                participantes.put(p.getPregunta(), 0L);
            }
        }
        return participantes;
    }
}
