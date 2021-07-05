package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuarioAndPassword(String nombreUsuario, String password);
}
