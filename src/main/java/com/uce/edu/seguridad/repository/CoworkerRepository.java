package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    @Query("SELECT c FROM Coworker c WHERE c.usuario.nombreUsuario = ?1")
    Coworker findByNombreUsuario(String nombreUsuario);

    @Query("SELECT c FROM Coworker c WHERE c.universidad.idUniversidad = :idUniversidad")
    List<Coworker> findScoreUniversidad(@Param("idUniversidad") Long id);

    @Modifying
    @Query("DELETE FROM Coworker c WHERE c.idCoworker IN ?1")
    void deleteAllByIds(Set<Long> ids);
}
