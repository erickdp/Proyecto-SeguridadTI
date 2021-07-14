package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Universidad;
import com.uce.edu.seguridad.repository.UniversidadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UniversidadServiceImp implements UniversidadService {

    @Autowired
    private UniversidadRepository universidadRepository;

    @Override
    public Universidad guardar(Universidad entidad) {
        return this.universidadRepository.save(entidad);
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
    public Universidad actualizarEntidad(Universidad entidad) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Universidad buscarUnieversidadPorNombre(String nombre) {
        return this.universidadRepository.findByNombreUniversidad(nombre);
    }
}
