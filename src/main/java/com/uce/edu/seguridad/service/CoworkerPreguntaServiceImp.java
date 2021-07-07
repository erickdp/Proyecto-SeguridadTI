package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.repository.CoworkerPreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoworkerPreguntaServiceImp implements CoworkerPreguntaService{

    @Autowired
    private CoworkerPreguntaRepository coworkerPreguntaRepository;

    @Override
    @Transactional
    public void guardar(CoworkerPregunta entidad) {
        this.coworkerPreguntaRepository.save(entidad);
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
    public void actualizarEntidad(CoworkerPregunta entidad) {

    }
}
