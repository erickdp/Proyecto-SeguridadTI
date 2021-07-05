package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Universidad;
import com.uce.edu.seguridad.repository.UniversidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class UniversidadServiceImp implements UniversidadService {

    @Autowired
    private UniversidadRepository universidadRepository;

    @Override
    public void guardar(Universidad entidad) {
        this.universidadRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Universidad consultarPorId(Long id) {
        return this.universidadRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Universidad> listarEntidad() {
        return this.universidadRepository.findAll();
    }

    @Override
    public void actualizarEntidad(Universidad entidad) {

    }
}
