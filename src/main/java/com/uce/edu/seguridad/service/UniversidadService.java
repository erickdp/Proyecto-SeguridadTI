package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Universidad;

public interface UniversidadService extends BaseService<Universidad> {
    Universidad buscarUnieversidadPorNombre(String nombre);
}
