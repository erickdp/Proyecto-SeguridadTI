package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Usuario;
import com.uce.edu.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImp implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void guardar(Usuario entidad) {

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario consultarPorId(Long id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listarEntidad() {
        return null;
    }

    @Override
    public void actualizarEntidad(Usuario entidad) {

    }

    @Override
    public Usuario buscarPorUsuarioYContrasena(String usuario, String contrasena) {
        return this.usuarioRepository.findByNombreUsuarioAndPassword(usuario, contrasena);
    }
}
