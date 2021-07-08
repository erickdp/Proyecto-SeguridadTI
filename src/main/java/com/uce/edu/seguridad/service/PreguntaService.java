package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Formulario;
import com.uce.edu.seguridad.models.Pregunta;

import java.util.List;

public interface PreguntaService extends BaseService<Pregunta> {
    Pregunta agregarPreguntaAFormulario(String pregunta, Formulario formulario);

    List<Pregunta> obtenerPreguntasPorFormulario(Long idFormulario);
}
