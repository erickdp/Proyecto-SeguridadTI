package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuarioAndPassword(String nombreUsuario, String password);

    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.idUsuario IN ?1")
    void deleteAllByIds(Set<Long> ids);

    Usuario findByNombreUsuario(String nombreUsuario);
}
