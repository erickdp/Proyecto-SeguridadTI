package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Universidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UniversidadRepository extends JpaRepository<Universidad, Long> {
    Universidad findByNombreUniversidad(String nombreUniversidad);
}
