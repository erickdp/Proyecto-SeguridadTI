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
public class CoworkerPreguntaServiceImp implements CoworkerPreguntaService{

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
    public HashMap<String, Double> promedioPorPreguntaDeFormulario(List<Pregunta> preguntasFiltradas) {
        var promedioPreguntas = new HashMap<String, Double>();
        Double promedio = 0d;
        for (Pregunta p :
                preguntasFiltradas) {
            promedio += this.coworkerPreguntaRepository.getAVGByPreguntaId(p.getIdPregunta());
            promedioPreguntas.put(p.getPregunta(), promedio);
            promedio = 0d;
        }
        return promedioPreguntas;
    }
}
