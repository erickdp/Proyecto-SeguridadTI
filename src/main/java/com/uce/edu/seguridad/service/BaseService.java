package com.uce.edu.seguridad.service;

import java.util.List;

public interface BaseService<T> {
    T guardar(T entidad);

    T consultarPorId(Long id);

    List<T> listarEntidad();

    T actualizarEntidad(T entidad);
}
