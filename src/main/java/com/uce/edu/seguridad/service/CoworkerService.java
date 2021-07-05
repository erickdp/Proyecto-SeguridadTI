package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;

public interface CoworkerService extends BaseService<Coworker> {
    Coworker buscarPorMailInstitucional(String mailInstitucional);
}
