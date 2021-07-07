package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Coworker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoworkerRepository extends JpaRepository<Coworker, Long> {
    Coworker findByMailInstitucional(String mailInstitucional);

    @Query("SELECT c FROM Coworker c WHERE c.universidad.idUniversidad = :idUniversidad")
    List<Coworker> findScoreUniversidad(@Param("idUniversidad") Long id);
}
