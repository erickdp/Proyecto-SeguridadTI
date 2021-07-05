package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Provincia;
import com.uce.edu.seguridad.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProvinciaServiceImp implements ProvinciaService{

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    @Transactional
    public void guardar(Provincia entidad) {
        this.provinciaRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Provincia consultarPorId(Long id) {
        return this.provinciaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Provincia> listarEntidad() {
        return this.provinciaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void actualizarEntidad(Provincia entidad) {
        this.provinciaRepository.save(entidad);
    }
}
