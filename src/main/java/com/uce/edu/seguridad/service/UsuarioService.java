package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Usuario;

public interface UsuarioService extends BaseService<Usuario> {
    public Usuario buscarPorUsuarioYContrasena(String usuario, String contrasena);

    void eliminarEntidad();
}
