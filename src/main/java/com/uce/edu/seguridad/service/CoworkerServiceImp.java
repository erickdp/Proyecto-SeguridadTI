package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.CoworkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CoworkerServiceImp implements CoworkerService {

    @Autowired
    private CoworkerRepository coworkerRepository;

    @Override
    @Transactional
    public void guardar(Coworker entidad) {
        this.coworkerRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Coworker consultarPorId(Long id) {
        return this.coworkerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Coworker> listarEntidad() {
        return this.coworkerRepository.findAll();
    }

    @Override
    public void actualizarEntidad(Coworker entidad) {

    }

    @Override
    public Coworker buscarPorMailInstitucional(String mailInstitucional) {
        return this.coworkerRepository.findByMailInstitucional(mailInstitucional);
    }

    @Override
    public List<Coworker> buscarPorUnivesidad(Long id) {
        return this.coworkerRepository.findScoreUniversidad(id);
    }

    @Override
    public Coworker calificarPregunta(Coworker coworker, Pregunta pregunta, Integer calificacion) {
        var coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setPregunta(pregunta);
        coworkerPregunta.setCalificacion(calificacion);
        coworker.getPreguntas().add(coworkerPregunta);
        this.coworkerRepository.save(coworker);
        return coworker;
    }
}
