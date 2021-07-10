package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.CoworkerPreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Override
    public HashMap<String, Double> promedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas, Long idUniversidad) {
        var promedioPreguntas = new HashMap<String, Double>();
        for (Pregunta p :
                preguntasFiltradas) {
            var avgDB = this.coworkerPreguntaRepository.getAVGByPreguntaId(p.getIdPregunta(), idUniversidad);
            if (avgDB != null) { // Cuando todas las preguntas tienen calificacion 0 devuelve null pero en eralidad seria avg 0
                promedioPreguntas.put(p.getPregunta(), avgDB);
            } else {
                promedioPreguntas.put(p.getPregunta(), 0d);
            }
        }
        return promedioPreguntas;
    }

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
