package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.PreguntaRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreguntaServiceImp implements PreguntaService{

    @Autowired
    private PreguntaRepositry preguntaRepositry;

    @Override
    @Transactional
    public void guardar(Pregunta entidad) {
        this.preguntaRepositry.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Pregunta consultarPorId(Long id) {
        return this.preguntaRepositry.findById(id).orElse(null);
    }

    @Override
    public List<Pregunta> listarEntidad() {
        return null;
    }

    @Override
    public void actualizarEntidad(Pregunta entidad) {

    }
}
