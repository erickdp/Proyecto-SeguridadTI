package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.repository.CoworkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }

    @Override
    public void actualizarEntidad(Coworker entidad) {

    }
}
