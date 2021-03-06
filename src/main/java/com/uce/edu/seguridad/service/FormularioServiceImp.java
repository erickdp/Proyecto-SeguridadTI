package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Formulario;
import com.uce.edu.seguridad.repository.FormularioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormularioServiceImp implements FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Override
    public Formulario guardar(Formulario entidad) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Formulario consultarPorId(Long id) {
        return this.formularioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formulario> listarEntidad() {
        return null;
    }

    @Override
    @Transactional
    public Formulario actualizarEntidad(Formulario entidad) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Formulario buscarPorNombreFormulario(String nombreFormulario) {
        return this.formularioRepository.findByTipoFormulario(nombreFormulario);
    }
}
