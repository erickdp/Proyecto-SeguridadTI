package com.uce.edu.seguridad.service;

import java.util.List;

public interface BaseService<T> {
    void guardar(T entidad);

    T consultarPorId(Long id);

    List<T> listarEntidad();

    void actualizarEntidad(T entidad);
}
