package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Formulario;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.PreguntaRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreguntaServiceImp implements PreguntaService {

    @Autowired
    private PreguntaRepositry preguntaRepositry;

    @Override
    @Transactional
    public Pregunta guardar(Pregunta entidad) {
        return this.preguntaRepositry.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Pregunta consultarPorId(Long id) {
        return this.preguntaRepositry.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> listarEntidad() {
        return this.preguntaRepositry.findAll();
    }

    @Override
    public Pregunta actualizarEntidad(Pregunta entidad) {
        return null;
    }

    @Override
    public Pregunta agregarPreguntaAFormulario(String pregunta, Formulario formulario) {
        var preguntaAgregar = new Pregunta();
        preguntaAgregar.setPregunta(pregunta);
        preguntaAgregar.setFormulario(formulario);
        this.preguntaRepositry.save(preguntaAgregar);
        return preguntaAgregar;
    }

    @Override
    public List<Pregunta> obtenerPreguntasPorFormulario(Long idFormulario) {
        return this.preguntaRepositry.findPreguntasByFormulario(idFormulario);
    }
}
