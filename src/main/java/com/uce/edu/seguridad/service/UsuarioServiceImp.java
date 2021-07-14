package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Usuario;
import com.uce.edu.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario guardar(Usuario entidad) {
        return this.usuarioRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario consultarPorId(Long id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarEntidad() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizarEntidad(Usuario entidad) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsuarioYContrasena(String usuario, String contrasena) {
        return this.usuarioRepository.findByNombreUsuarioAndPassword(usuario, contrasena);
    }

    @Override
    @Transactional
    public void eliminarEntidad() {
        List<Usuario> usuarios = this.listarEntidad();
        Set<Long> ids = new HashSet<>();
        usuarios.forEach(u -> {
            if(!(u.getRol().getIdRol().equals(1L))) {
                ids.add(u.getIdUsuario());
            }
        });
        this.usuarioRepository.deleteAllByIds(ids);
    }
}
