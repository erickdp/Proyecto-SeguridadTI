package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Formulario;

public interface FormularioService extends BaseService<Formulario> {
    Formulario buscarPorNombreFormulario(String nombreFormulario);
}
